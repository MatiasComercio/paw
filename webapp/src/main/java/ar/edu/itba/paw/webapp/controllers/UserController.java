package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.webapp.auth.UserSessionDetails;
import ar.edu.itba.paw.webapp.forms.PasswordForm;
import ar.edu.itba.paw.webapp.forms.ResetPasswordForm;
import ar.edu.itba.paw.webapp.forms.UserForm;
import ar.edu.itba.paw.webapp.forms.validators.PasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@Controller
public class UserController {

	private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	private static final String UNAUTHORIZED = "redirect:/errors/403";

	@Autowired
	private MessageSource messageSource;

	@Autowired
	PasswordValidator passwordValidator;

	@Autowired
	private UserService userService;


	@Autowired
	private HttpServletRequest request;

	@ModelAttribute("user")
	public UserSessionDetails user(final Authentication authentication) {
		return (UserSessionDetails) authentication.getPrincipal();
	}

	@RequestMapping(value = "/user/changePassword")
	public ModelAndView changePassword(
			@ModelAttribute("changePasswordForm") final PasswordForm passwordForm,
			final RedirectAttributes redirectAttributes) {
		LOGGER.info("User {} is about to change password", passwordForm.getDni());
		final ModelAndView mav = new ModelAndView("changePassword");
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			LOGGER.warn("Someone tried to change a password and auth was null [GET]");
			return new ModelAndView("redirect:/errors/401");
		}

		UserDetails userDetails = (UserDetails) auth.getPrincipal();
		/* userDetails.getUsername() == user's dni; used to load on the PasswordForm */
		HTTPErrorsController.setAlertMessages(mav, redirectAttributes);
		mav.addObject("dni", userDetails.getUsername());
		mav.addObject("section2", "changePassword");

		return mav;
	}

	@RequestMapping(value = "/user/resetPassword", method = RequestMethod.POST)
	public ModelAndView resetPassword(
		@Valid @ModelAttribute("resetPasswordForm") final ResetPasswordForm passwordForm,
		final BindingResult errors,
		final RedirectAttributes redirectAttributes,
		@ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("RESET_PASSWORD")) {
			LOGGER.warn("User {} tried to delete user doesn't have authority RESET_PASSWORD [POST]", loggedUser);
			return new ModelAndView(UNAUTHORIZED);
		}

			if (errors.hasErrors()){
				redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.resetPasswordForm", errors);
				redirectAttributes.addFlashAttribute("resetPasswordForm", passwordForm);

				final String referrer = request.getHeader("referer");
				return new ModelAndView("redirect:" + referrer);
			}

			final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth == null) {
				LOGGER.warn("Someone tried to change a password and auth was null [POST]");
				return new ModelAndView("redirect:/errors/401");
			}
			UserSessionDetails user = (UserSessionDetails) auth.getPrincipal();

		/* Check that the DNI is still valid and was not modified */
			if (!String.valueOf(passwordForm.getDni()).equals(user.getUsername()) && !user.hasAuthority("ADMIN")) {
				LOGGER.warn("Someone tried to reset a password from another user.");
				LOGGER.warn("Logged user's dni: {}", user.getUsername());
				LOGGER.warn("Victim's dni: {}", passwordForm.getDni());

				return new ModelAndView("redirect:/errors/401");
			}

			final Result result = userService.resetPassword(passwordForm.getDni());

			if (!result.equals(Result.OK)) {
				redirectAttributes.addFlashAttribute("alert", "danger");
				redirectAttributes.addFlashAttribute("message", result.getMessage());
			} else {
				redirectAttributes.addFlashAttribute("alert", "success");
				redirectAttributes.addFlashAttribute("message", messageSource.getMessage("change_pwd_success",
						null,
						Locale.getDefault()));
			}

		final String referrer = request.getHeader("referer");
		return new ModelAndView("redirect:" + referrer);
	}

	@RequestMapping(value = "/user/changePassword", method = RequestMethod.POST)
	public ModelAndView changePassword(
			@Valid @ModelAttribute("changePasswordForm") final PasswordForm passwordForm,
			final BindingResult errors,
			final RedirectAttributes redirectAttributes) {
		LOGGER.info("User {} is about to change password", passwordForm.getDni());
		passwordValidator.validate(passwordForm, errors);

		if (errors.hasErrors()){
			LOGGER.warn("User {} could not change password due to {} [POST]", passwordForm.getDni(), errors.getAllErrors());

			return changePassword(passwordForm, redirectAttributes);
		}

		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null) {
			LOGGER.warn("Someone tried to change a password and auth was null [POST]");
			return new ModelAndView("redirect:/errors/401");
		}
		UserSessionDetails user = (UserSessionDetails) auth.getPrincipal();

		/* Check that the DNI is still valid and was not modified */
		if (!String.valueOf(passwordForm.getDni()).equals(user.getUsername())) {
			LOGGER.warn("Someone tried to change a password from another user.");
			LOGGER.warn("Logged user's dni: {}", user.getUsername());
			LOGGER.warn("Victim's dni: {}", passwordForm.getDni());

			return new ModelAndView("redirect:/errors/401");
		}

		final Result result = userService.changePassword(
				passwordForm.getDni(),
				passwordForm.getCurrentPassword(),
				passwordForm.getNewPassword(),
				passwordForm.getRepeatNewPassword());

		if (!result.equals(Result.OK)) {
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", result.getMessage());
			LOGGER.warn("User {} could not change Password, Result = {}", passwordForm.getDni(), result);
		} else {
			redirectAttributes.addFlashAttribute("alert", "success");
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("change_pwd_success",
					null,
					Locale.getDefault()));
			LOGGER.info("User {} changed Password successfully", passwordForm.getDni());

		}


		/* +++xchange */
		return new ModelAndView("redirect:/user/changePassword");
	}

	@RequestMapping(value = "/user/delete_user", method = RequestMethod.POST)
	public ModelAndView deleteUser(@Valid @ModelAttribute("userForm") UserForm userForm,
	                               final BindingResult errors, final RedirectAttributes redirectAttributes,
	                               @ModelAttribute("user") UserSessionDetails loggedUser) {
		LOGGER.info("Deleting User {}", userForm.getDni());
		if (!loggedUser.hasAuthority("DELETE_USER")
				&& !loggedUser.hasAuthority("ADMIN")) {
			LOGGER.warn("User {} tried to delete user NOT ADMIN and doesn't have authority DELETE_USER [POST]", loggedUser);
			return new ModelAndView(UNAUTHORIZED);
		}
		if (errors.hasErrors()){
			//return deleteUser(userForm, null); //TODO: see where it returns
		}
		final Result result = userService.delete(userForm.getDni());

		if(!result.equals(Result.OK)){
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", result.getMessage());
			//return deleteUser(userForm, redirectAttributes); //TODO: See where it returns
			LOGGER.warn("User {} could not be deleted, Result = {}", userForm.getDni(), result);
		} else {
			LOGGER.info("User {} was deleted successfully", userForm.getDni());
		}
		redirectAttributes.addFlashAttribute("alert", "success");
		redirectAttributes.addFlashAttribute("message", messageSource.getMessage("addStudent_success",
				null,
				Locale.getDefault()));
		return new ModelAndView("redirect:/students");
	}
}

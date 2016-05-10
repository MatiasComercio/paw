package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.AdminService;
import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.AdminFilter;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.webapp.auth.UserSessionDetails;
import ar.edu.itba.paw.webapp.forms.AdminFilterForm;
import ar.edu.itba.paw.webapp.forms.AdminForm;
import ar.edu.itba.paw.webapp.forms.ResetPasswordForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

/**
 * Controller of ${rootServerPath}/admins/** and ${rootServerPath}/admin/**
 */

@Controller
public class AdminController {
	private final static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	private static final String ADMINS_SECTION = "admins";
	private static final String UNAUTHORIZED = "redirect:/errors/403";
	private static final String NOT_FOUND = "404";

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private AdminService adminService;

	@Autowired
	private HttpServletRequest request;

	@ModelAttribute("section")
	public String sectionManager(){
		return ADMINS_SECTION;
	}

	@ModelAttribute("user")
	public UserSessionDetails user(final Authentication authentication) {
		return (UserSessionDetails) authentication.getPrincipal();
	}

	@ModelAttribute("resetPasswordForm")
	public ResetPasswordForm resetPasswordForm() {
		return new ResetPasswordForm();
	}

	@RequestMapping(value = "/admins", method = RequestMethod.GET)
	public ModelAndView admins(@Valid @ModelAttribute("adminFilterForm") final AdminFilterForm adminFilterForm,
	                                final BindingResult errors,
	                                final Model model,
	                           @ModelAttribute("user") UserSessionDetails loggedUser) {
		if (!loggedUser.hasAuthority("VIEW_ADMINS")) {
			LOGGER.warn("User {} tried to list the admins and doesn't have VIEW_ADMINS authority [GET]", loggedUser);
			return new ModelAndView(UNAUTHORIZED);
		}

		final ModelAndView mav = new ModelAndView("admins");

		if (errors.hasErrors()) {
			/* Cancel current search */
			LOGGER.warn("There were errors when User {} tried to list the admins {}", loggedUser, errors.getAllErrors());
			adminFilterForm.empty();

			mav.addObject("alert", "danger");
			mav.addObject("message", messageSource.getMessage("search_fail",
					null,
					Locale.getDefault()));
		}

		final AdminFilter adminFilter = new AdminFilter.AdminFilterBuilder()
				.dni(adminFilterForm.getDni())
				.firstName(adminFilterForm.getFirstName())
				.lastName(adminFilterForm.getLastName())
				.build();

		// +++ximprove with Spring Security
		final List<Admin> admins = adminService.getByFilter(adminFilter);
		if (admins == null) {
			return new ModelAndView(NOT_FOUND);
		}

		mav.addObject("admins", admins);
		mav.addObject("adminFilterFormAction", "/admins");

		/*+++ximprove */
		loadEnableDisableInscription(mav);
		return mav;
	}

	private void loadEnableDisableInscription(final ModelAndView mav) {
		if(adminService.isInscriptionEnabled()){
			mav.addObject("confirm_action_url", "/admins/disable_inscriptions");
			mav.addObject("confirm_action_message", messageSource.getMessage("confirm_disable_inscriptions",
					null,
					Locale.getDefault()));
			mav.addObject("isInscriptionEnabled", true);
		}
		else{
			mav.addObject("confirm_action_url", "/admins/enable_inscriptions");
			mav.addObject("confirm_action_message", messageSource.getMessage("confirm_enable_inscriptions",
					null,
					Locale.getDefault()));

			mav.addObject("isInscriptionEnabled", false);
		}
	}

	/* +++xcheck: if errors are displayed; not redirecting attributes on post */
	@RequestMapping(value = "/admins/add_admin", method = RequestMethod.GET)
	public ModelAndView adminsAddAdminG(
			@ModelAttribute("adminForm") final AdminForm adminForm,
			final RedirectAttributes redirectAttributes,
			@ModelAttribute("user") UserSessionDetails loggedUser) {
		if (!loggedUser.hasAuthority("ADD_ADMIN")) {
			LOGGER.warn("The user {} is trying to add an admin and doesn't have authority ADD_ADMIN [GET]", loggedUser.getDni());
			return new ModelAndView(UNAUTHORIZED);
		}

		ModelAndView mav = new ModelAndView("addUser");
		HTTPErrorsController.setAlertMessages(mav, redirectAttributes);
		mav.addObject("section2", "addAdmin");

		/*+++ximprove */
		loadEnableDisableInscription(mav);
		return mav;
	}

	@RequestMapping(value = "/admins/add_admin", method = RequestMethod.POST)
	public ModelAndView adminsAddAdminP(
			@Valid @ModelAttribute("adminForm") AdminForm adminForm,
			final BindingResult errors,
			final RedirectAttributes redirectAttributes,
			@ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("ADD_ADMIN")) {
			LOGGER.warn("The user {} is trying to add an admin and doesn't have authority ADD_ADMIN [POST]", loggedUser.getDni());
			return new ModelAndView(UNAUTHORIZED);
		}
		if (errors.hasErrors()){
			LOGGER.warn("The user {} is trying to add an admin and there was an error = {}", loggedUser, errors.getAllErrors());
			return adminsAddAdminG(adminForm, null, loggedUser);
		}
		Admin admin = adminForm.build();
		Result result = adminService.create(admin);
		if(!result.equals(Result.OK)){
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", result.getMessage());
			LOGGER.warn("User {} could not add admin, Result = {}", adminForm.getDni(), result);
			return adminsAddAdminG(adminForm, redirectAttributes, loggedUser);
		}
		redirectAttributes.addFlashAttribute("alert", "success");
		redirectAttributes.addFlashAttribute("message", messageSource.getMessage("addAdmin_success",
				null,
				Locale.getDefault()));
		return new ModelAndView("redirect:/admins/");
	}

	@RequestMapping(value= "/admins/disable_inscriptions", method=RequestMethod.POST)
	public ModelAndView disableInscriptions(RedirectAttributes redirectAttributes,
	                                        @ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("DISABLE_INSCRIPTION")) {
			LOGGER.warn("The user {} is trying to disable inscription and and doesn't have authority DISABLE_INSCRIPTION [POST]", loggedUser.getDni());
			return new ModelAndView(UNAUTHORIZED);
		}
		Result result = adminService.disableInscriptions();
		if(!result.equals(Result.OK)){
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", result.getMessage());
			LOGGER.warn("User {} could not disable inscriptions, Result = {}", loggedUser.getDni(), result);
		}
		else{
			LOGGER.info("User {} disabled inscriptions successfully", loggedUser.getDni());
			redirectAttributes.addFlashAttribute("alert", "success");
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("disable_inscriptions_success",
					null,
					Locale.getDefault()));
		}

		final String referrer = request.getHeader("referer");
		return new ModelAndView("redirect:" + referrer);
	}

	@RequestMapping(value= "/admins/enable_inscriptions", method=RequestMethod.POST)
	public ModelAndView enableInscriptions(RedirectAttributes redirectAttributes,
	                                       @ModelAttribute("user") UserSessionDetails loggedUser) {
		if (!loggedUser.hasAuthority("ENABLE_INSCRIPTION")) {
			LOGGER.warn("The user {} is trying to enable inscription and and doesn't have authority ENABLE_INSCRIPTION [POST]", loggedUser.getDni());
			return new ModelAndView(UNAUTHORIZED);
		}
		Result result = adminService.enableInscriptions();
		if(!result.equals(Result.OK)){
			LOGGER.warn("User {} could not enable inscriptions, Result = {}", loggedUser.getDni(), result);
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", result.getMessage());
		}
		else{
			LOGGER.info("User {} disabled inscriptions successfully", loggedUser.getDni());
			redirectAttributes.addFlashAttribute("alert", "success");
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("enable_inscriptions_success",
					null,
					Locale.getDefault()));
		}

		final String referrer = request.getHeader("referer");
		return new ModelAndView("redirect:" + referrer);
	}


	@RequestMapping("/admins/{dni}/info")
	public ModelAndView viewAdmin (
			@PathVariable("dni") final int dni,
			@ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("VIEW_ADMIN")) {
			LOGGER.warn("The user {} is trying to view an admin's info and and doesn't have authority VIEW_ADMIN [GET]", loggedUser.getDni());
			return new ModelAndView(UNAUTHORIZED);
		}

		final Admin admin = adminService.getByDni(dni);

		if (admin == null) {
			LOGGER.warn("User {} tried to access admin {} that does not exist", loggedUser.getUsername());
			return new ModelAndView(NOT_FOUND);
		}

		final ModelAndView mav = new ModelAndView("admin");
		mav.addObject("admin", admin);
		mav.addObject("section", ADMINS_SECTION);
		mav.addObject("section2", "info");

		return mav;
	}

	@RequestMapping("/admins/{dni}/edit")
	public ModelAndView editAdmin(
			@PathVariable("dni") final int dni,
			@ModelAttribute("adminForm") AdminForm adminForm,
			final RedirectAttributes redirectAttributes,
			@ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("EDIT_ADMIN")) {
			LOGGER.warn("The user {} is trying to edit an admin and and doesn't have authority EDIT_ADMIN [GET]", loggedUser.getDni());
			return new ModelAndView(UNAUTHORIZED);
		}

		final Admin admin = adminService.getByDni(dni);

		if (admin == null) {
			LOGGER.warn("User {} tried to access admin {} that does not exist", loggedUser.getUsername());
			return new ModelAndView(NOT_FOUND);
		}

		adminForm.loadFromAdmin(admin);

		final ModelAndView mav = new ModelAndView("addUser");
		HTTPErrorsController.setAlertMessages(mav, redirectAttributes);
		mav.addObject("admin", admin);
		mav.addObject("section", ADMINS_SECTION);
		mav.addObject("section2", "edit");

		return mav;
	}

	@RequestMapping(value = "/admins/{dni}/edit", method = RequestMethod.POST)
	public ModelAndView editStudent(@PathVariable final int dni,
	                                @Valid @ModelAttribute("adminForm") AdminForm adminForm,
	                                final BindingResult errors,
	                                RedirectAttributes redirectAttributes,
	                                @ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("EDIT_ADMIN")) {
			LOGGER.warn("The user {} is trying to edit an admin and and doesn't have authority EDIT_ADMIN [POST]", loggedUser);
			return new ModelAndView(UNAUTHORIZED);
		}

		if (errors.hasErrors()){
			LOGGER.warn("There were errors when User {} tried to edit an admin {}", loggedUser, errors.getAllErrors());
			return editAdmin(dni, adminForm, redirectAttributes, loggedUser);
		}

		final Admin admin = adminForm.build();
		final Result result = adminService.update(dni, admin);

		if(!result.equals(Result.OK)){
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", result.getMessage());
			LOGGER.warn("User {} could not edit admin, Result = {}", adminForm.getDni(), result);
			return editAdmin(dni, adminForm, redirectAttributes, loggedUser);
		} else {
			LOGGER.info("User {} could edited admins, Result = {}", adminForm.getDni());
		}

		redirectAttributes.addFlashAttribute("alert", "success");
		redirectAttributes.addFlashAttribute("message", messageSource.getMessage("editAdmin_success",
				null,
				Locale.getDefault()));

		final String referrer = request.getHeader("referer");
		return new ModelAndView("redirect:" + referrer);
	}

}

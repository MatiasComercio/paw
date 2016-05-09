package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.AdminService;
import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.shared.AdminFilter;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.webapp.auth.UserDetailsServiceImpl;
import ar.edu.itba.paw.webapp.auth.UserSessionDetails;
import ar.edu.itba.paw.webapp.forms.AdminFilterForm;
import ar.edu.itba.paw.webapp.forms.AdminForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Controller of ${rootServerPath}/admins/** and ${rootServerPath}/admin/**
 */

@Controller
public class AdminController {
	private final static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);
	private static final String ADMINS_SECTION = "admins";
	private static final String UNAUTHORIZED = "redirect:/errors/403";

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

	@RequestMapping(value = "/admins", method = RequestMethod.GET)
	public ModelAndView admins(@Valid @ModelAttribute("adminFilterForm") final AdminFilterForm adminFilterForm,
	                                final BindingResult errors,
	                                final Model model,
	                           @ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("VIEW_ADMINS")) {
			return new ModelAndView(UNAUTHORIZED);
		}

		final ModelAndView mav = new ModelAndView("admins");

		if (errors.hasErrors()) {
			/* Cancel current search */
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
			return new ModelAndView("forward:/errors/404.html");
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
			return new ModelAndView(UNAUTHORIZED);
		}
		if (errors.hasErrors()){
			return adminsAddAdminG(adminForm, null, loggedUser);
		}
		Admin admin = adminForm.build();
		Result result = adminService.create(admin);
		if(!result.equals(Result.OK)){
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", result.getMessage());
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
			return new ModelAndView(UNAUTHORIZED);
		}
		Result result = adminService.disableInscriptions();
		if(!result.equals(Result.OK)){
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", result.getMessage());
		}
		else{
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
			return new ModelAndView(UNAUTHORIZED);
		}
		Result result = adminService.enableInscriptions();
		if(!result.equals(Result.OK)){
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", result.getMessage());
		}
		else{
			redirectAttributes.addFlashAttribute("alert", "success");
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("enable_inscriptions_success",
					null,
					Locale.getDefault()));
		}

		final String referrer = request.getHeader("referer");
		return new ModelAndView("redirect:" + referrer);
	}

}

package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.webapp.auth.StudentDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Security;
import java.util.Map;

@Controller
public class IndexController {

	private final static Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping("/")
	public ModelAndView index() {
		/* +++xchange: implement the index later */
		LOGGER.info("Temporarily redirecting to /students...");
		return new ModelAndView("redirect:/students");
	}

	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
}

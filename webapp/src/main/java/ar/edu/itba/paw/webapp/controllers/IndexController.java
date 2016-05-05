package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.webapp.auth.StudentDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Security;

@Controller
public class IndexController {

	private final static Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

	@RequestMapping("/")
	public ModelAndView index() {
		/* +++xchange: implement the index later */
		/* tmp solution */
		final StudentDetails studentDetails = (StudentDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		LOGGER.debug("Logged User: {}", studentDetails);
		LOGGER.info("Temporarily redirecting to /students...");
		return new ModelAndView("redirect:/students");
	}

	@RequestMapping("/login") /* +++xcheck: if loginForm is necessary or not */
	public ModelAndView login() {
		return new ModelAndView("login");
	}
}

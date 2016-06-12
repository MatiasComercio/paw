package ar.edu.itba.paw.webapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

	@RequestMapping("/logout")
	public ModelAndView logout() {
		return new ModelAndView("login?logout");
	}

	// for maintenance
//	@RequestMapping("/inMaintenance")
//	public ModelAndView inMaintenance() {
//		return new ModelAndView("inMaintenance");
//	}
}

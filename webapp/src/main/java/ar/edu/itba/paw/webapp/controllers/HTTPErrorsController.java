package ar.edu.itba.paw.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HTTPErrorsController {
	@RequestMapping(value="/errors/403")
	public ModelAndView handle403() {
		return new ModelAndView("403");
	}

	@RequestMapping(value="/errors/404")
	public ModelAndView handle404() {
		return new ModelAndView("404");
	}
}

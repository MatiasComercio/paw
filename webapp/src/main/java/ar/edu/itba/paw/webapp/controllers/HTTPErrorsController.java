package ar.edu.itba.paw.webapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HTTPErrorsController {
	@RequestMapping(value="/errors/404.html")
	public ModelAndView handle404() {
		return new ModelAndView("404");
	}
}

package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

	@Autowired
	private StudentService studentService;

/*	@RequestMapping("/")
	public ModelAndView index() {
		final ModelAndView mav = new ModelAndView("index");
		mav.addObject("user", us.register("juan", "12345"));
		return mav;
	}*/

	@RequestMapping("/students/{docket}")
	public ModelAndView getUser(@PathVariable final int docket) {
		final ModelAndView mav = new ModelAndView("student");
		mav.addObject("student", studentService.getByDocket(docket));
		return mav;
	}

}

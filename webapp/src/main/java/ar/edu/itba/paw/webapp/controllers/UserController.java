package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ar.edu.itba.paw.interfaces.UserService;
import javax.servlet.http.HttpSession;
import java.awt.event.MouseAdapter;

@Controller
public class UserController {

	@Autowired
	private UserService us;

//	@Autowired
//	private CourseService cs;

	@RequestMapping("/")
	public ModelAndView index() {
		final ModelAndView mav = new ModelAndView("index");
		mav.addObject("user", us.register("juan", "12345"));
		return mav;
	}

	@RequestMapping("/users/{username}")
	public ModelAndView getUser(@PathVariable final String username) {
		final ModelAndView mav = new ModelAndView("user");
		mav.addObject("user", us.getByUsername(username));
		return mav;
	}

//	@RequestMapping("/courses/{id}")
//	public ModelAndView getCourse(@PathVariable final Integer id) {
//		final ModelAndView mav = new ModelAndView("course");
//		mav.addObject("course", cs.getById(id));
//		return mav;
//	}

}

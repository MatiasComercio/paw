package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CourseController {

    @Autowired
    private CourseService cs;

    @RequestMapping("/courses/{id}")
    public ModelAndView getCourse(@PathVariable final Integer id) {
        final ModelAndView mav = new ModelAndView("course");
        mav.addObject("course", cs.getById(id));
        return mav;
    }
}

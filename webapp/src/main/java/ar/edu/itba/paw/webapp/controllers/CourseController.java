package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.CourseService;

import ar.edu.itba.paw.shared.CourseFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @RequestMapping("/courses/{id}")
    public ModelAndView getCourse(@PathVariable final Integer id) {
        final ModelAndView mav = new ModelAndView("course");
        mav.addObject("course", courseService.getById(id));
        return mav;
    }

    @RequestMapping("/courses/")
    public ModelAndView getAllCourses() {
        final ModelAndView mav = new ModelAndView("courses");
        mav.addObject("courses", courseService.getAllCourses());
        return mav;
    }

    @RequestMapping(value = "/courses")
    public ModelAndView getCoursesByFilter(@RequestParam(defaultValue = "") String keyword,
                                           @RequestParam(defaultValue = "") Integer id) {
        final ModelAndView mav = new ModelAndView("courses");
        final CourseFilter courseFilter = new CourseFilter.CourseFilterBuilder().keyword(keyword).id(id).build();
        mav.addObject("courses", courseService.getByFilter(courseFilter));
        return mav;
    }

    @RequestMapping("/courses/{id}/students")
    public ModelAndView getCourseStudents(@PathVariable final Integer id){
        final ModelAndView mav = new ModelAndView("courseStudents");
        mav.addObject("courseStudents", courseService.getCourseStudents(id));
        return mav;
    }
}

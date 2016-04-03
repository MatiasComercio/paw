package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.CourseService;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.shared.CourseFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @RequestMapping("/courses/{id}/info")
    public ModelAndView getCourse(@PathVariable final Integer id) {
        final ModelAndView mav = new ModelAndView("course");
        mav.addObject("course", courseService.getById(id));
        mav.addObject("section", "courses");
        return mav;
    }

    @RequestMapping(value = "/courses")
    public ModelAndView getCoursesByFilter(@RequestParam(defaultValue = "") String keyword,
                                           @RequestParam(defaultValue = "") Integer id) {
        final ModelAndView mav = new ModelAndView("searchCourses");
        final CourseFilter courseFilter = new CourseFilter.CourseFilterBuilder().keyword(keyword).id(id).build();
        mav.addObject("courses", courseService.getByFilter(courseFilter));
        mav.addObject("section", "search_courses");
        return mav;
    }

    @RequestMapping("/courses/{id}/students")
    public ModelAndView getCourseStudents(@PathVariable final Integer id){
        final ModelAndView mav = new ModelAndView("courseStudents");
        mav.addObject("courseStudents", courseService.getCourseStudents(id));
        return mav;
    }

    @RequestMapping(value = "/courses/add_course", method = RequestMethod.GET)
    public ModelAndView addCourse(){
        final Course course = new Course.Builder(0).build();
        final ModelAndView mav = new ModelAndView("addCourse", "command", course);
        return mav;
    }

    @RequestMapping(value = "/courses/add_course", method = RequestMethod.POST)
    public String addCourse(@ModelAttribute("addCourse") Course.Builder courseBuilder) {

        final Course course = courseBuilder.build();
        System.out.println(course.toString());
        System.out.println(course.getId());
        courseService.create(course.getId(), course.getName(), course.getCredits());
        return "redirect:";
    }
}

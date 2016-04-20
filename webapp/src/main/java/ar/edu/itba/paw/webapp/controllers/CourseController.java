package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.CourseService;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.webapp.forms.CourseForm;
import ar.edu.itba.paw.shared.CourseFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class CourseController {
    private static final String COURSES_SECTION = "courses";

    @Autowired
    private CourseService courseService;

    @RequestMapping(value = "/courses")
    public ModelAndView getCoursesByFilter(@RequestParam(required = false) String keyword,
                                           @RequestParam(required = false) Integer id) {
        final ModelAndView mav = new ModelAndView("coursesSearch");
        final CourseFilter courseFilter = new CourseFilter.CourseFilterBuilder().keyword(keyword).id(id).build();
        mav.addObject("courses", courseService.getByFilter(courseFilter));
        mav.addObject("section", COURSES_SECTION);
        return mav;
    }

    @RequestMapping("/courses/{id}/info")
    public ModelAndView getCourse(@PathVariable final Integer id) {
        final ModelAndView mav = new ModelAndView("course");
        mav.addObject("course", courseService.getById(id));
        mav.addObject("section", COURSES_SECTION);
        return mav;
    }

    @RequestMapping("/courses/{id}/students")
    public ModelAndView getCourseStudents(@PathVariable final Integer id){
        final ModelAndView mav = new ModelAndView("courseStudents");
        mav.addObject("courseStudents", courseService.getCourseStudents(id));
        mav.addObject("section", COURSES_SECTION);
        return mav;
    }

    @RequestMapping(value = "/courses/add_course", method = RequestMethod.GET)
    public ModelAndView addCourse(@ModelAttribute("courseForm") final CourseForm courseForm){

        //final ModelAndView mav = new ModelAndView("addCourse", "command", new CourseForm());
        ModelAndView mav = new ModelAndView("addCourse");
        mav.addObject("section", COURSES_SECTION);
        return mav;
    }

    @RequestMapping(value = "/courses/add_course", method = RequestMethod.POST)
    public ModelAndView addCourse(@Valid @ModelAttribute("courseForm") CourseForm courseForm,
                            final BindingResult errors){
        if(!errors.hasErrors()){
            final Course course = courseForm.build();
            courseService.create(course);
            //return "redirect:/app/courses";
            return new ModelAndView("redirect:/app/courses");
        }
        else{
            return addCourse(courseForm);
        }
    }

    @RequestMapping(value = "/courses/{id}/delete", method = RequestMethod.POST)
    public ModelAndView deleteCourse(@PathVariable final Integer id) {
        final Result result = courseService.deleteCourse(id);
//        ModelAndView mav = new ModelAndView("redirect:/app/courses");
        ModelAndView mav = new ModelAndView("coursesSearch");
        mav.addObject("message", result.getMessage());

        return mav;
    }
}

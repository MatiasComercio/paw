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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class CourseController {
    private static final String COURSES_SECTION = "courses";

    /* +++xtodo TODO: why not final? */
    private static String TASK_FORM_ADD = "add";
    private static String TASK_FORM_EDIT = "edit";

    @Autowired
    private CourseService courseService;

    @ModelAttribute("section")
    public String sectionManager(){
        return COURSES_SECTION;
    }

    @RequestMapping(value = "/courses")
    public ModelAndView getCoursesByFilter(@RequestParam(required = false) String keyword,
                                           @RequestParam(required = false) Integer id) {
        final ModelAndView mav = new ModelAndView("coursesSearch");
        final CourseFilter courseFilter = new CourseFilter.CourseFilterBuilder().keyword(keyword).id(id).build();
        mav.addObject("courses", courseService.getByFilter(courseFilter));
        return mav;
    }

    @RequestMapping("/courses/{id}/info")
    public ModelAndView getCourse(@PathVariable final Integer id) {
        final ModelAndView mav = new ModelAndView("course");
        mav.addObject("course", courseService.getById(id));
        return mav;
    }

    @RequestMapping("/courses/{courseId}/edit")
    public ModelAndView editCourse(@PathVariable final Integer courseId, @ModelAttribute("courseForm") final CourseForm courseForm,
                                   RedirectAttributes redirectAttributes) {
        final ModelAndView mav = new ModelAndView("addCourse");

        if (redirectAttributes != null) {
            Map<String, ?> raMap = redirectAttributes.getFlashAttributes();
            if (raMap.get("alert") != null) {
                mav.addObject("alert", raMap.get("alert"));
                mav.addObject("message", raMap.get("message"));
            }
        }

        Course course = courseService.getById(courseId);

        if (course == null){
            redirectAttributes.addFlashAttribute("alert", "danger");
            redirectAttributes.addFlashAttribute("message", "La materia que se intena editar no existe.");
            return new ModelAndView("redirect:/courses");
        }

        courseForm.loadFromCourse(course);

        mav.addObject("courseId", courseId);
        mav.addObject("courseName", course.getName());
        mav.addObject("task", TASK_FORM_EDIT);

        return mav;
    }

    @RequestMapping(value = "/courses/{courseId}/edit", method = RequestMethod.POST)
    public ModelAndView editCourse(@PathVariable final Integer courseId,
                                  @Valid @ModelAttribute("courseForm") CourseForm courseForm,
                                  final BindingResult errors,
                                  RedirectAttributes redirectAttributes){
        if (errors.hasErrors()){
            return editCourse(courseId, courseForm, redirectAttributes);
        }

        Course course = courseForm.build();
        Result result = courseService.update(courseId, course);

        if(!result.equals(Result.OK)){
            redirectAttributes.addFlashAttribute("alert", "danger");
            redirectAttributes.addFlashAttribute("message", result.getMessage());
            return editCourse(courseId, courseForm, redirectAttributes);
        }

        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("message", "La materia se ha guardado correctamente.");

        return new ModelAndView("redirect:/courses");
    }


    @RequestMapping("/courses/{id}/students")
    public ModelAndView getCourseStudents(@PathVariable final Integer id){
        final ModelAndView mav = new ModelAndView("courseStudents");
        mav.addObject("courseStudents", courseService.getCourseStudents(id));

        return mav;
    }

    @RequestMapping(value = "/courses/add_course", method = RequestMethod.GET)
    public ModelAndView addCourse(@ModelAttribute("courseForm") final CourseForm courseForm,
                                  RedirectAttributes redirectAttributes){
        ModelAndView mav = new ModelAndView("addCourse");
        if(redirectAttributes != null) {
            Map<String, ?> raMap = redirectAttributes.getFlashAttributes();
            if (raMap.get("alert") != null) {
                mav.addObject("alert", raMap.get("alert"));
                mav.addObject("message", raMap.get("message"));
            }
        }
        mav.addObject("task", TASK_FORM_ADD);

        return mav;
    }

    @RequestMapping(value = "/courses/add_course", method = RequestMethod.POST)
    public ModelAndView addCourse(@Valid @ModelAttribute("courseForm") CourseForm courseForm,
                            final BindingResult errors, RedirectAttributes redirectAttributes){
        if(errors.hasErrors()){
            return addCourse(courseForm, null);
        }
        else{
            final Course course = courseForm.build();
            Result result = courseService.create(course);
            if(!result.equals(Result.OK)){
                redirectAttributes.addFlashAttribute("alert", "danger");
                redirectAttributes.addFlashAttribute("message", result.getMessage());
                return addCourse(courseForm, redirectAttributes);
            }
            redirectAttributes.addFlashAttribute("alert", "success");
            redirectAttributes.addFlashAttribute("message", "El curso se ha guardado correctamente.");
            return new ModelAndView("redirect:/courses");
        }
    }

    @RequestMapping(value = "/courses/{id}/delete", method = RequestMethod.POST)
    public ModelAndView deleteCourse(@PathVariable final Integer id, RedirectAttributes redirectAttributes) {
        final Result result = courseService.deleteCourse(id);
//        ModelAndView mav = new ModelAndView("redirect:/courses");
//        ModelAndView mav = new ModelAndView("coursesSearch");
        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("message", "El curso se ha eliminado exitosamente.");

        return new ModelAndView("redirect:/courses");
    }
}

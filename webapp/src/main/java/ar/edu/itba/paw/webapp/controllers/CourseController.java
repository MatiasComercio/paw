package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.CourseService;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.shared.StudentFilter;
import ar.edu.itba.paw.webapp.forms.CourseFilterForm;
import ar.edu.itba.paw.webapp.forms.CourseForm;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.webapp.forms.StudentFilterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class CourseController {
    private static final String COURSES_SECTION = "courses";

    /* +++xtodo TODO: why not final? */
    private static String TASK_FORM_ADD = "add";
    private static String TASK_FORM_EDIT = "edit";

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CourseService courseService;

    @ModelAttribute("section")
    public String sectionManager() {
        return COURSES_SECTION;
    }

/*    @RequestMapping(value = "/courses")
    public ModelAndView getCoursesByFilter(@RequestParam(required = false) String keyword,
                                           @RequestParam(required = false) Integer id) {
        final ModelAndView mav = new ModelAndView("coursesSearch");
        final CourseFilter courseFilter = new CourseFilter.CourseFilterBuilder().keyword(keyword).id(id).build();
        mav.addObject("courses", courseService.getByFilter(courseFilter));
        return mav;
    }*/

    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public ModelAndView getCourses(final Model model) {

        if (!model.containsAttribute("courseFilterForm")) {
            model.addAttribute("courseFilterForm", new CourseFilterForm());
        }
        if (!model.containsAttribute("deleteCourseForm")) {
            model.addAttribute("deleteCourseForm", new CourseFilterForm());  /*+++xcheck: if it is necessary to create a new Form*/
        }

        final CourseFilterForm courseFilterForm = (CourseFilterForm) model.asMap().get("courseFilterForm");
        final CourseFilter courseFilter = new CourseFilter.CourseFilterBuilder().
                id(courseFilterForm.getId()).keyword(courseFilterForm.getName()).build();

        // +++ximprove with Spring Security
        final List<Course> courses = courseService.getByFilter(courseFilter);
        if (courses == null) {
            return new ModelAndView("forward:/errors/404.html");
        }

        final ModelAndView mav = new ModelAndView("courses");
        mav.addObject("courses", courses);
        mav.addObject("courseFilterFormAction", "/courses/courseFilterForm");
        return mav;
    }

    @RequestMapping(value = "/courses/courseFilterForm", method = RequestMethod.GET)
    public ModelAndView studentFilterForm(@Valid @ModelAttribute("courseFilterForm") final CourseFilterForm courseFilterForm,
                                          final BindingResult errors,
                                          final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.courseFilterForm", errors);
        redirectAttributes.addFlashAttribute("courseFilterForm", courseFilterForm);
        return new ModelAndView("redirect:/courses");
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

        if (course == null) {
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
                                   RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return editCourse(courseId, courseForm, redirectAttributes);
        }

        Course course = courseForm.build();
        Result result = courseService.update(courseId, course);

        if (!result.equals(Result.OK)) {
            redirectAttributes.addFlashAttribute("alert", "danger");
            redirectAttributes.addFlashAttribute("message", result.getMessage());
            return editCourse(courseId, courseForm, redirectAttributes);
        }

        redirectAttributes.addFlashAttribute("alert", "success");
        redirectAttributes.addFlashAttribute("message", messageSource.getMessage("editCourse_success",
                new Object[]{},
                Locale.getDefault()));

        return new ModelAndView("redirect:/courses");
    }


/*    @RequestMapping("/courses/{id}/students")
    public ModelAndView getCourseStudents(@PathVariable final Integer id){
        final ModelAndView mav = new ModelAndView("courseStudents");
        mav.addObject("courseStudents", courseService.getCourseStudents(id));

        return mav;
    }*/

    @RequestMapping(value = "/courses/{id}/students", method = RequestMethod.GET)
    public ModelAndView getCourseStudents(@PathVariable("id") final Integer id, final Model model) {

        if (!model.containsAttribute("studentFilterForm")) {
            model.addAttribute("studentFilterForm", new StudentFilterForm());
        }

        final StudentFilterForm studentFilterForm = (StudentFilterForm) model.asMap().get("studentFilterForm");
        final StudentFilter studentFilter = new StudentFilter.StudentFilterBuilder()
                .docket(studentFilterForm.getDocket())
                .firstName(studentFilterForm.getFirstName())
                .lastName(studentFilterForm.getLastName())
                .build();

        // +++ximprove with Spring Security
        final Course course = courseService.getById(id);
        if (course == null) {
            return new ModelAndView("forward:/errors/404.html");
        }
        final List<Student> students = courseService.getCourseStudents(id, studentFilter);

        final ModelAndView mav = new ModelAndView("courseStudents");
        mav.addObject("course", course);
        mav.addObject("students", students);
        mav.addObject("studentFilterFormAction", "/courses/" + id + "/students/studentFilterForm");
        return mav;
    }

    @RequestMapping(value = "/courses/{id}/students/studentFilterForm", method = RequestMethod.GET)
    public ModelAndView courseStudentsStudentFilterForm(
            @PathVariable("id") final int id,
            @Valid @ModelAttribute("studentFilterForm") final StudentFilterForm studentFilterForm,
            final BindingResult errors,
            final RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.studentFilterForm", errors);
        redirectAttributes.addFlashAttribute("studentFilterForm", studentFilterForm);
        return new ModelAndView("redirect:/courses/" + id + "/students");
    }

    @RequestMapping(value = "/courses/add_course", method = RequestMethod.GET)
    public ModelAndView addCourse(@ModelAttribute("courseForm") final CourseForm courseForm,
                                  RedirectAttributes redirectAttributes) {
        ModelAndView mav = new ModelAndView("addCourse");
        if (redirectAttributes != null) {
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
                                  final BindingResult errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            return addCourse(courseForm, null);
        } else {
            final Course course = courseForm.build();
            Result result = courseService.create(course);
            if (!result.equals(Result.OK)) {
                redirectAttributes.addFlashAttribute("alert", "danger");
                redirectAttributes.addFlashAttribute("message", result.getMessage());
                return addCourse(courseForm, redirectAttributes);
            }
            redirectAttributes.addFlashAttribute("alert", "success");
            redirectAttributes.addFlashAttribute("message", messageSource.getMessage("addCourse_success",
                    new Object[]{},
                    Locale.getDefault()));
            return new ModelAndView("redirect:/courses");
        }
    }

    @RequestMapping(value = "/courses/{id}/delete", method = RequestMethod.POST)
    public ModelAndView deleteCourse(@PathVariable final Integer id, RedirectAttributes redirectAttributes) {
        final Result result = courseService.deleteCourse(id);
//        ModelAndView mav = new ModelAndView("redirect:/courses");
//        ModelAndView mav = new ModelAndView("coursesSearch");
        if (result.equals(Result.OK)) {
            redirectAttributes.addFlashAttribute("alert", "success");
            redirectAttributes.addFlashAttribute("message", messageSource.getMessage("deleteCourse_success",
                    new Object[]{},
                    Locale.getDefault()));
        } else {
            redirectAttributes.addFlashAttribute("alert", "danger");
            redirectAttributes.addFlashAttribute("message", result.getMessage());
        }

        return new ModelAndView("redirect:/courses");
    }

    //TODO: DELETE
    @RequestMapping(value = "/correlatives/{course_id}", method = RequestMethod.GET)
    public ModelAndView getCorrelatives(@PathVariable final Integer course_id) {

        /*List<Integer> correlatives = courseService.getCorrelatives(course_id);
        System.out.println("getCorrelatives en CourseController");
        for (Integer correlative : correlatives) {
            System.out.println("102 es correlativa de: " + correlative);
        }*/

        System.out.println(courseService.addCorrelative(101, 105));
        System.out.println(courseService.addCorrelative(105, 101));

        return new ModelAndView("redirect:/courses");
    }
}
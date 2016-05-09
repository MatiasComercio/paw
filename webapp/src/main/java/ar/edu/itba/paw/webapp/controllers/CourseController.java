package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.CourseService;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.models.users.User;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.shared.StudentFilter;
import ar.edu.itba.paw.webapp.auth.UserSessionDetails;
import ar.edu.itba.paw.webapp.forms.CorrelativeForm;
import ar.edu.itba.paw.webapp.forms.CourseFilterForm;
import ar.edu.itba.paw.webapp.forms.CourseForm;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.webapp.forms.StudentFilterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
public class CourseController {
	private static final String COURSES_SECTION = "courses";

	/* +++xtodo TODO: why not final? */
	private static final String TASK_FORM_ADD = "add";
	private static final String TASK_FORM_EDIT = "edit";
	private static final String UNAUTHORIZED = "redirect:/errors/401";

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private CourseService courseService;

	@ModelAttribute("section")
	public String sectionManager(){
		return COURSES_SECTION;
	}

	@ModelAttribute("user")
	public UserSessionDetails user(final Authentication authentication) {
		return (UserSessionDetails) authentication.getPrincipal();
	}

	@ModelAttribute("deleteCourseForm")
	public CourseFilterForm courseFilterForm() {
		return new CourseFilterForm();
	}

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
		mav.addObject("subsection_get_courses", true);
		mav.addObject("courseFilterFormAction", "/courses/courseFilterForm");
		return mav;
	}

	@RequestMapping(value = "/courses/courseFilterForm", method = RequestMethod.GET)
	public ModelAndView studentFilterForm(             @Valid @ModelAttribute("courseFilterForm") final CourseFilterForm courseFilterForm,
	                                                   final BindingResult errors,
	                                                   final RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.courseFilterForm", errors);
		redirectAttributes.addFlashAttribute("courseFilterForm", courseFilterForm);
		return new ModelAndView("redirect:/courses");
	}

	@RequestMapping("/courses/{id}/info")
	public ModelAndView getCourse(@PathVariable final Integer id, Model model,
	                              @ModelAttribute("deleteCourseForm") final CourseFilterForm courseFilterForm) {
		final ModelAndView mav = new ModelAndView("course");

		if (!model.containsAttribute("correlativeForm")) {
			model.addAttribute("correlativeForm", new CorrelativeForm());
		}

		mav.addObject("course", courseService.getById(id));
		mav.addObject("section2", "info"); /* +++xcheck: if it's ok, do the same for all the URLs */
		mav.addObject("correlativeFormAction", "/courses/" + id + "/delete_correlative");
		mav.addObject("subsection_delete_correlative", true);
		mav.addObject("correlatives", courseService.getCorrelativesByFilter(id, null));
		return mav;
	}

	@RequestMapping("/courses/{courseId}/edit")
	public ModelAndView editCourse(
			@PathVariable final Integer courseId,
			@ModelAttribute("courseForm") final CourseForm courseForm,
			@ModelAttribute("deleteCourseForm") final CourseFilterForm courseFilterForm,
			final RedirectAttributes redirectAttributes,
			@ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("EDIT_COURSE")) {
			return new ModelAndView(UNAUTHORIZED);
		}

		final ModelAndView mav = new ModelAndView("addCourse");

		HTTPErrorsController.setAlertMessages(mav, redirectAttributes);
		Course course = courseService.getById(courseId);

		if (course == null){
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", "La materia que se intena editar no existe.");
			return new ModelAndView("redirect:/courses");
		}

		courseForm.loadFromCourse(course);

		mav.addObject("section2", "edit");
		mav.addObject("course", course);
/*		mav.addObject("courseId", courseId);
		mav.addObject("courseName", course.getName());*/
		mav.addObject("task", TASK_FORM_EDIT);

		return mav;
	}

	@RequestMapping(value = "/courses/{courseId}/edit", method = RequestMethod.POST)
	public ModelAndView editCourse(@PathVariable final Integer courseId,
	                               @Valid @ModelAttribute("courseForm") CourseForm courseForm,
	                               final BindingResult errors,
	                               RedirectAttributes redirectAttributes,
	                               @ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("EDIT_COURSE")) {
			return new ModelAndView(UNAUTHORIZED);
		}

		if (errors.hasErrors()){
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.courseForm", errors);
			redirectAttributes.addFlashAttribute("courseForm", courseForm);
			return new ModelAndView("redirect:/courses/" + courseId + "/edit");
		}

		Course course = courseForm.build();
		Result result = courseService.update(courseId, course);

		if(!result.equals(Result.OK)){
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", result.getMessage());
			return new ModelAndView("redirect:/courses/" + courseId + "/edit");
		}

		redirectAttributes.addFlashAttribute("alert", "success");
		redirectAttributes.addFlashAttribute("message", messageSource.getMessage("editCourse_success",
				null,
				Locale.getDefault()));

		final String referrer = request.getHeader("referer");
		return new ModelAndView("redirect:" + referrer);
	}


/*    @RequestMapping("/courses/{id}/students")
	public ModelAndView getCourseStudents(@PathVariable final Integer id){
		final ModelAndView mav = new ModelAndView("courseStudents");
		mav.addObject("courseStudents", courseService.getCourseStudents(id));

		return mav;
	}*/

	@RequestMapping(value = "/courses/{id}/students", method = RequestMethod.GET)
	public ModelAndView getCourseStudents(@PathVariable("id") final Integer id,
	                                      final Model model, final RedirectAttributes redirectAttributes) {

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
		HTTPErrorsController.setAlertMessages(mav, redirectAttributes);
		mav.addObject("section2", "students");
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
	                              RedirectAttributes redirectAttributes,
	                              @ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("ADD_COURSE")) {
			return new ModelAndView(UNAUTHORIZED);
		}
		ModelAndView mav = new ModelAndView("addCourse");
		HTTPErrorsController.setAlertMessages(mav, redirectAttributes);
//		mav.addObject("task", TASK_FORM_ADD);
		mav.addObject("section2", "addCourse");

		return mav;
	}

	@RequestMapping(value = "/courses/add_course", method = RequestMethod.POST)
	public ModelAndView addCourse(@Valid @ModelAttribute("courseForm") CourseForm courseForm,
	                              final BindingResult errors, RedirectAttributes redirectAttributes,
	                              UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("ADD_COURSE")) {
			return new ModelAndView(UNAUTHORIZED);
		}

		if(errors.hasErrors()){
			return addCourse(courseForm, null, loggedUser);
		}
		else{
			final Course course = courseForm.build();
			Result result = courseService.create(course);
			if(!result.equals(Result.OK)){
				redirectAttributes.addFlashAttribute("alert", "danger");
				redirectAttributes.addFlashAttribute("message", result.getMessage());
				return addCourse(courseForm, redirectAttributes, loggedUser);
			}
			redirectAttributes.addFlashAttribute("alert", "success");
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("addCourse_success",
					null,
					Locale.getDefault()));
			return new ModelAndView("redirect:/courses");
		}
	}

	@RequestMapping(value = "/courses/{id}/delete", method = RequestMethod.POST)
	public ModelAndView deleteCourse(@PathVariable final Integer id,
	                                 RedirectAttributes redirectAttributes,
	                                 @ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("DELETE_COURSE")) {
			return new ModelAndView(UNAUTHORIZED);
		}

		final Result result = courseService.deleteCourse(id);
//        ModelAndView mav = new ModelAndView("redirect:/courses");
//        ModelAndView mav = new ModelAndView("coursesSearch");
		final String urlRedirect;
		if(result.equals(Result.OK)) {
			redirectAttributes.addFlashAttribute("alert", "success");
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("deleteCourse_success",
					null,
					Locale.getDefault()));
			urlRedirect = "/courses";
		} else {
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", result.getMessage());
			urlRedirect = request.getHeader("referer");
		}

		return new ModelAndView("redirect:" + urlRedirect);
	}

	@RequestMapping(value = "/courses/{course_id}/add_correlative", method = RequestMethod.GET)
	public ModelAndView addCorrelative(@PathVariable final Integer course_id, Model model,
	                                   @ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("ADD_CORRELATIVE")) {
			return new ModelAndView(UNAUTHORIZED);
		}

		if (!model.containsAttribute("courseFilterForm")) {
			model.addAttribute("courseFilterForm", new CourseFilterForm());
		}
		if (!model.containsAttribute("correlativeForm")) {
			model.addAttribute("correlativeForm", new CorrelativeForm());
		}

		final CourseFilterForm courseFilterForm = (CourseFilterForm) model.asMap().get("courseFilterForm");
		final CourseFilter courseFilter = new CourseFilter.CourseFilterBuilder().
				id(courseFilterForm.getId()).keyword(courseFilterForm.getName()).build();

		//Check the course exists (in case the url is modified)
		final Course course = courseService.getById(course_id);
		if (course == null) {
			return new ModelAndView("forward:/errors/404");
		}

		final ModelAndView mav = new ModelAndView("courses");
		mav.addObject("course", course);
		mav.addObject("section2", "addCorrelative");
		mav.addObject("courseFilterFormAction", "/courses/" + course_id + "/add_correlative/courseFilterForm");
		mav.addObject("correlativeFormAction", "/courses/" + course_id + "/add_correlative");
		mav.addObject("subsection_add_correlative", true);
		mav.addObject("courses", courseService.getAvailableAddCorrelatives(course_id, courseFilter));
		return mav;
	}

	@RequestMapping(value = "/courses/{course_id}/add_correlative/courseFilterForm", method = RequestMethod.GET)
	public ModelAndView studentInscriptionCourseFilter(@PathVariable final int course_id,
	                                                   @Valid @ModelAttribute("courseFilterForm") final CourseFilterForm courseFilterForm,
	                                                   final BindingResult errors,
	                                                   final RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.courseFilterForm", errors);
		redirectAttributes.addFlashAttribute("courseFilterForm", courseFilterForm);
		return new ModelAndView("redirect:/courses/" + course_id + "/add_correlative");
	}

	@RequestMapping(value = "/courses/{course_id}/add_correlative", method = RequestMethod.POST)
	public ModelAndView addCorrelative(@PathVariable final Integer course_id,
	                                   @Valid @ModelAttribute("CorrelativeForm") CorrelativeForm correlativeForm,
	                                   final BindingResult errors, final RedirectAttributes redirectAttributes,
	                                   @ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("ADD_CORRELATIVE")) {
			return new ModelAndView(UNAUTHORIZED);
		}

		if (errors.hasErrors()){
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.correlativeForm", errors);
			redirectAttributes.addFlashAttribute("correlativeForm", correlativeForm);
			return new ModelAndView("redirect:/courses/" + course_id + "/add_correlative");
		}

		Result result = courseService.addCorrelative(correlativeForm.getCourseId(), correlativeForm.getCorrelativeId());
		if (result == null) {
			result = Result.ERROR_UNKNOWN;
		}
		if (!result.equals(Result.OK)) {
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", result.getMessage());

		} else {
			redirectAttributes.addFlashAttribute("alert", "success");
			redirectAttributes.addFlashAttribute("message",
					messageSource.getMessage("correlative_add_success",
							new Object[] {correlativeForm.getCourseName(), correlativeForm.getCorrelativeName()},
							Locale.getDefault()));
		}

		return new ModelAndView("redirect:/courses/" + course_id + "/add_correlative");
	}

	@RequestMapping(value = "/courses/{course_id}/delete_correlative", method = RequestMethod.POST)
	public ModelAndView deleteCorrelative(@PathVariable final Integer course_id,
	                                      @Valid @ModelAttribute("CorrelativeForm") CorrelativeForm correlativeForm,
	                                      final BindingResult errors, final RedirectAttributes redirectAttributes,
	                                      @ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("DELETE_CORRELATIVE")) {
			return new ModelAndView(UNAUTHORIZED);
		}

		if (errors.hasErrors()){
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.correlativeForm", errors);
			redirectAttributes.addFlashAttribute("correlativeForm", correlativeForm);
			return new ModelAndView("redirect:/courses/" + course_id + "/info");
		}

		Result result = courseService.deleteCorrelative(correlativeForm.getCourseId(), correlativeForm.getCorrelativeId());
		if (result == null) {
			result = Result.ERROR_UNKNOWN;
		}
		if (!result.equals(Result.OK)) {
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", result.getMessage());

		} else {
			redirectAttributes.addFlashAttribute("alert", "success");
			redirectAttributes.addFlashAttribute("message",
					messageSource.getMessage("correlative_delete_success",
							new Object[] {correlativeForm.getCourseName(), correlativeForm.getCorrelativeName()},
							Locale.getDefault()));
		}

		return new ModelAndView("redirect:/courses/" + course_id + "/info");

	}

}
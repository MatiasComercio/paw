package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.StudentFilter;
import ar.edu.itba.paw.webapp.auth.UserSessionDetails;
import ar.edu.itba.paw.webapp.forms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@Controller
public class CourseController {
	private final static Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

	private static final String COURSES_SECTION = "courses";

	private static final String TASK_FORM_ADD = "add";
	private static final String TASK_FORM_EDIT = "edit";
	private static final String UNAUTHORIZED = "redirect:/errors/403";
	private static final String NOT_FOUND = "404";

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
	public ModelAndView getCourses(final Model model,
	                               @ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("VIEW_COURSES")) {
			LOGGER.warn("User {} tried to view all courses and doesn't have VIEW_COURSES authority [GET]", loggedUser.getDni());
			return new ModelAndView(UNAUTHORIZED);
		}

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
			LOGGER.warn("There were no courses to return"); /* It is logged because this situation is strange */
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
	                              @ModelAttribute("deleteCourseForm") final CourseFilterForm courseFilterForm,
	                              @ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("VIEW_COURSE")) {
			LOGGER.warn("User {} tried to view the course {} and doesn't have VIEW_COURSE authority [GET]",
					loggedUser.getDni(), id);
			return new ModelAndView(UNAUTHORIZED);
		}
		final ModelAndView mav = new ModelAndView("course");

		if (!model.containsAttribute("correlativeForm")) {
			model.addAttribute("correlativeForm", new CorrelativeForm());
		}

		final Course course = courseService.getById(id);

		if (course == null) {
			LOGGER.warn("User {} tried to edit course {} that does not exist", loggedUser.getDni(), id);
			return new ModelAndView(NOT_FOUND);
		}

		mav.addObject("course", course);
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
//			@ModelAttribute("deleteCourseForm") final CourseFilterForm courseFilterForm,
			final RedirectAttributes redirectAttributes,
			@ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("EDIT_COURSE")) {
			LOGGER.warn("User {} tried to edit a course and doesn't have EDIT_COURSE authority [GET]", loggedUser.getDni());
			return new ModelAndView(UNAUTHORIZED);
		}

		final ModelAndView mav = new ModelAndView("addCourse");

		HTTPErrorsController.setAlertMessages(mav, redirectAttributes);
		Course course = courseService.getById(courseId);

		if (course == null){
			LOGGER.warn("User {} tried to edit course {} that does not exist", loggedUser.getDni(), courseId);
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("editCourse_fail", null, Locale.getDefault()));
			return new ModelAndView("redirect:/courses");
		}

		if (!redirectAttributes.getFlashAttributes().containsKey("justCalled")) {
			courseForm.loadFromCourse(course);
		}

		mav.addObject("section2", "edit");
		mav.addObject("course", course);
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
			LOGGER.warn("User {} tried to edit a course and doesn't have EDIT_COURSE authority [POST]", loggedUser.getDni());
			return new ModelAndView(UNAUTHORIZED);
		}

		if (errors.hasErrors()){
			LOGGER.warn("User {} could not edit course {} due to {} [POST]", loggedUser.getDni(), courseId, errors.getAllErrors());
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.courseForm", errors);
			redirectAttributes.addFlashAttribute("courseForm", courseForm);
			/* set alert */
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("formWithErrors", null, Locale.getDefault()));
			/* --------- */
			redirectAttributes.addFlashAttribute("justCalled", true);
			return editCourse(courseId, courseForm, redirectAttributes, loggedUser);
		}

		Course course = courseForm.build();
		boolean done = courseService.update(courseId, course);

		/* If no course with 'courseId' exists, !result.equals(Result.OK) will be true */
		if(!done){
			LOGGER.warn("User {} could not edit course, Result = {}", loggedUser.getDni(), done);
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("CORRELATIVE_SEMESTER_INCOMPATIBILITY", null, Locale.getDefault()));
			redirectAttributes.addFlashAttribute("justCalled", true);
			return editCourse(courseId, courseForm, redirectAttributes, loggedUser);
		} else {
			LOGGER.info("User {} edited course {} successfully", loggedUser.getDni(), courseId);
		}

		redirectAttributes.addFlashAttribute("alert", "success");
		redirectAttributes.addFlashAttribute("message", messageSource.getMessage("editCourse_success",
				null,
				Locale.getDefault()));

		final String referrer = request.getHeader("referer");
		return new ModelAndView("redirect:" + referrer);
	}


	@RequestMapping(value = "/courses/{id}/students", method = RequestMethod.GET)
	public ModelAndView getCourseStudents(@PathVariable("id") final Integer id,
	                                      final Model model, final RedirectAttributes redirectAttributes,
	                                      @ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("VIEW_STUDENTS")) {
			LOGGER.warn("User {} tried to view all students that are enrolled at course {} " +
					"and doesn't have VIEW_STUDENTS authority [GET]", loggedUser.getDni(), id);
			return new ModelAndView(UNAUTHORIZED);
		}

		if (!model.containsAttribute("gradeForm")) {
			model.addAttribute("gradeForm", new GradeForm());
		}
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
			LOGGER.warn("User {} tried to view all enrolled students from course {} that does not exist", loggedUser.getUsername(), id);
			return new ModelAndView(NOT_FOUND);
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
			LOGGER.warn("User {} tried to add a course and doesn't have ADD_COURSE authority [GET]", loggedUser.getDni());
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
	                              @ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("ADD_COURSE")) {
			LOGGER.warn("User {} tried to add a course and doesn't have ADD_COURSE authority [POST]", loggedUser.getDni());
			return new ModelAndView(UNAUTHORIZED);
		}

		if(errors.hasErrors()){
			LOGGER.warn("User {} could not add course {} due to {} [POST]", loggedUser.getDni(), courseForm.getId(), errors.getAllErrors());
			/* set alert */
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("formWithErrors", null, Locale.getDefault()));
			/* --------- */
			return addCourse(courseForm, redirectAttributes, loggedUser);
		} else {
			final Course course = courseForm.build();
			boolean done;

			try {
				done = courseService.create(course);
			} catch (final DataIntegrityViolationException e) {
				done = false;
			}
			if(!done){
				LOGGER.warn("User {} could not add course, Result = {}", loggedUser.getDni(), done);
				redirectAttributes.addFlashAttribute("alert", "danger");
				redirectAttributes.addFlashAttribute("message", messageSource.getMessage("COURSE_EXISTS_ID", null, Locale.getDefault()));
				return addCourse(courseForm, redirectAttributes, loggedUser);
			} else {
				LOGGER.info("User {} added course {}", loggedUser.getDni(), courseForm.getId());
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
			LOGGER.warn("User {} tried to delete a course and doesn't have DELETE_COURSE authority [POST]", loggedUser.getDni());
			return new ModelAndView(UNAUTHORIZED);
		}

		final boolean done = courseService.deleteCourse(id);
		final String urlRedirect;
		if(done) {
			LOGGER.info("User {} deleted a course successfully", loggedUser.getDni());
			redirectAttributes.addFlashAttribute("alert", "success");
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("deleteCourse_success",
					null,
					Locale.getDefault()));
			urlRedirect = "/courses";
		} else { // if course with the specified id does not exist, it will enter here
			LOGGER.warn("User {} could not delete course, Result = {}", loggedUser.getDni(), done);
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("COURSE_EXISTS_INSCRIPTION_OR_GRADE", null, Locale.getDefault()));
			urlRedirect = request.getHeader("referer");
		}

		return new ModelAndView("redirect:" + urlRedirect);
	}

	@RequestMapping(value = "/courses/{course_id}/add_correlative", method = RequestMethod.GET)
	public ModelAndView addCorrelative(@PathVariable final Integer course_id, Model model,
	                                   @ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("ADD_CORRELATIVE")) {
			LOGGER.warn("User {} tried to add a correlative and doesn't have ADD_CORRELATIVE authority [POST]", loggedUser.getDni());
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
			LOGGER.warn("User {} tried to access course {} that does not exist", loggedUser.getDni(), course_id);
			return new ModelAndView(NOT_FOUND);
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
			LOGGER.warn("User {} tried to edit a course and doesn't have EDIT_COURSE authority [POST]", loggedUser.getDni());
			return new ModelAndView(UNAUTHORIZED);
		}

		if (errors.hasErrors()){
			LOGGER.warn("User {} could not add correlative due to {} [POST]", loggedUser.getDni(), errors.getAllErrors());
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.correlativeForm", errors);
			redirectAttributes.addFlashAttribute("correlativeForm", correlativeForm);
			return new ModelAndView("redirect:/courses/" + course_id + "/add_correlative");
		}

		boolean done = courseService.addCorrelative(correlativeForm.getCourseId(), correlativeForm.getCorrelativeId());

		if (!done) {
			LOGGER.warn("User {} could not add correlative, Result = {}", loggedUser.getDni(), done);
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("CORRELATIVE_LOGIC_INCORRECT", null, Locale.getDefault()));

		} else { // if course with the specified id does not exist, it will enter here
			LOGGER.info("User {} added correlative successfully", loggedUser.getDni());
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
			LOGGER.warn("User {} tried to delete correlative and doesn't have DELETE_CORRELATIVE authority [POST]", loggedUser);
			return new ModelAndView(UNAUTHORIZED);
		}

		if (errors.hasErrors()){
			LOGGER.warn("User {} could not delete correlative due to {} [POST]", loggedUser.getDni(), errors.getAllErrors());
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.correlativeForm", errors);
			redirectAttributes.addFlashAttribute("correlativeForm", correlativeForm);
			return new ModelAndView("redirect:/courses/" + course_id + "/info");
		}

		boolean done = courseService.deleteCorrelative(correlativeForm.getCourseId(), correlativeForm.getCorrelativeId());

		if (!done) {
			LOGGER.warn("User {} could not delete correlative, Result = {}", loggedUser.getDni(), done);
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("ERROR_UNKNOWN", null, Locale.getDefault()));

		} else { // if course with the specified id does not exist, it will enter here
			LOGGER.info("User {} deleted correlative successfully", loggedUser.getDni());
			redirectAttributes.addFlashAttribute("alert", "success");
			redirectAttributes.addFlashAttribute("message",
					messageSource.getMessage("correlative_delete_success",
							new Object[] {correlativeForm.getCourseName(), correlativeForm.getCorrelativeName()},
							Locale.getDefault()));
		}

		return new ModelAndView("redirect:/courses/" + course_id + "/info");

	}

	@RequestMapping(value = "/courses/{id}/students_passed", method = RequestMethod.GET)
	public ModelAndView getCourseStudentsThatPassed(@PathVariable("id") final Integer id, final Model model,
	                                                @ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("VIEW_STUDENTS_APPROVED")) {
			LOGGER.warn("User {} tried to get the students that passed course {} and doesn't have VIEW_STUDENTS_APPROVED authority [POST]", loggedUser, id);
			return new ModelAndView(UNAUTHORIZED);
		}

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
		final Course course = courseService.getStudentsThatPassedCourse(id, studentFilter);
		if (course == null) {
			LOGGER.warn("User {} tried to access course {} that does not exist", loggedUser.getDni(), id);
			return new ModelAndView(NOT_FOUND);
		}


		final ModelAndView mav = new ModelAndView("courseStudents");
		mav.addObject("course", course);
		//TODO: DELETE - mav.addObject("students", course.getStudents());
		mav.addObject("students", course.getApprovedStudents());
		mav.addObject("section2", "studentsPassed");
		mav.addObject("studentFilterFormAction", "/courses/" + id + "/students_passed/studentFilterForm");
		return mav;
	}

	@RequestMapping(value = "/courses/{id}/students_passed/studentFilterForm", method = RequestMethod.GET)
	public ModelAndView getCourseStudentsThatPassedFilterForm(
			@PathVariable("id") final int id,
			@Valid @ModelAttribute("studentFilterForm") final StudentFilterForm studentFilterForm,
			final BindingResult errors,
			final RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.studentFilterForm", errors);
		redirectAttributes.addFlashAttribute("studentFilterForm", studentFilterForm);
		return new ModelAndView("redirect:/courses/" + id + "/students_passed");
	}

}
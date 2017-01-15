package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Grade;
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

//@Controller
public class OldStudentController {

	private final static Logger LOGGER = LoggerFactory.getLogger(OldStudentController.class);
	private static final String STUDENTS_SECTION = "students";
	private static final String UNAUTHORIZED = "redirect:/errors/403";
	private static final String NOT_FOUND = "404";
	private static final ModelAndView NOT_FOUND_MAV = new ModelAndView(NOT_FOUND);

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private StudentService studentService;

	@Autowired
	private HttpServletRequest request;

	@ModelAttribute("section")
	public String sectionManager(){
		return STUDENTS_SECTION;
	}

	@ModelAttribute("user")
	public UserSessionDetails user(final Authentication authentication) {
		return (UserSessionDetails) authentication.getPrincipal();
	}

	@ModelAttribute("deleteStudentForm")
	public StudentFilterForm deleteStudentForm() {
		return new StudentFilterForm();
	}

	@ModelAttribute("resetPasswordForm")
	public ResetPasswordForm resetPasswordForm() {
		return new ResetPasswordForm();
	}

//	@RequestMapping(value = "/students", method = RequestMethod.GET)
//	public ModelAndView getStudents(@Valid @ModelAttribute("studentFilterForm") final StudentFilterForm studentFilterForm,
//									final BindingResult errors,
//									final Model model,
//									@ModelAttribute("user") UserSessionDetails loggedUser) {
//
//		if (!loggedUser.hasAuthority("VIEW_STUDENTS")) {
//			LOGGER.warn("User {} tried to view all students and doesn't have VIEW_STUDENTS authority [GET]", loggedUser.getDni());
//			return new ModelAndView(UNAUTHORIZED);
//		}
//
//		if (!model.containsAttribute("deleteStudentForm")) {
//			model.addAttribute("deleteStudentForm", new StudentFilterForm()); /* +++xcheck: if it is necessary to create a new Form */
//		}
//
//		final StudentFilter studentFilter = new StudentFilter.StudentFilterBuilder()
//				.docket(studentFilterForm.getDocket())
//				.firstName(studentFilterForm.getFirstName())
//				.lastName(studentFilterForm.getLastName())
//				.build();
//
//		final ModelAndView mav = new ModelAndView("students");
//
//		final List<Student> students = loadStudentsByFilter(mav, errors, studentFilter);
//
//		if (students == null) {
//			return NOT_FOUND_MAV;
//		}
//
//		mav.addObject("students", students);
//		mav.addObject("studentFilterFormAction", "/students");
//		mav.addObject("subsection_students", true);
//		return mav;
//	}

//	@RequestMapping("/students/{docket}/info")
//	public ModelAndView getStudent(@PathVariable final int docket,
//								   @ModelAttribute("user") UserSessionDetails loggedUser) {
//
//		if (!loggedUser.hasAuthority("VIEW_STUDENT")) {
//			LOGGER.warn("User {} tried to view student with docket {} and doesn't have VIEW_STUDENT authority [GET]", loggedUser.getDni(), docket);
//			return new ModelAndView(UNAUTHORIZED);
//		}
//
//		final Student student =  studentService.getByDocket(docket);
//
//		final ModelAndView mav;
//
//		if (student == null) {
//			return NOT_FOUND_MAV;
//		}
//
//		mav = new ModelAndView("student");
//		mav.addObject("student", student);
//		mav.addObject("section2", "info");
//		return mav;
//	}

//	@RequestMapping("/students/{docket}/grades")
//	public ModelAndView getStudentGrades(@PathVariable final int docket, Model model,
//										 RedirectAttributes redirectAttributes,
//										 @ModelAttribute("user") UserSessionDetails loggedUser) {
//
//		if (!loggedUser.hasAuthority("VIEW_GRADES")
//				|| (loggedUser.getId() != docket && !loggedUser.hasAuthority("ADMIN"))) {
//			LOGGER.warn("User {} tried to get the grades of a student {} and doesn't have VIEW_GRADES authority [POST]", loggedUser.getDni(), docket);
//			return new ModelAndView(UNAUTHORIZED);
//		}
//		final Student student = studentService.getGrades(docket);
//		final ModelAndView mav;
//		final Integer totalCredits, passedCredits, percentage;
//
//		if (student == null) {
//			return new ModelAndView("forward:/errors/404.html");
//		}
//
//		if (!model.containsAttribute("gradeForm")) {
//			model.addAttribute("gradeForm", new GradeForm());
//		}
//
//		mav = new ModelAndView("grades_old"); // +++xchange
//
//		HTTPErrorsController.setAlertMessages(mav, redirectAttributes);
//
//		mav.addObject("student", student);
//		mav.addObject("section2", "grades");
//		mav.addObject("subsection_grades", true);
//		mav.addObject("gradeFormAction", "/students/" + docket + "/grades/edit");
//
//		totalCredits = studentService.getTotalPlanCredits();
//		passedCredits = studentService.getPassedCredits(docket);
//		percentage = (!totalCredits.equals(0)) ? (passedCredits * 100) / totalCredits : 0;
//
//		mav.addObject("section2", "grades");
//		mav.addObject("semesters", studentService.getTranscript(docket));
//		mav.addObject("total_credits", totalCredits);
//		mav.addObject("passed_credits", passedCredits);
//		mav.addObject("percentage", percentage);
//		return mav;
//	}

//	@RequestMapping(value = "/students/{docket}/courses", method = RequestMethod.GET)
//	public ModelAndView getStudentCourses(@PathVariable final int docket, final Model model,
//	                                      @ModelAttribute("user") UserSessionDetails loggedUser,
//	                                      @Valid @ModelAttribute("courseFilterForm")
//	                                          final CourseFilterForm courseFilterForm,
//	                                      final BindingResult errors) {
//
//		if (!loggedUser.hasAuthority("VIEW_COURSES")) {
//			LOGGER.warn("User {} tried to view all the courses the student with docket {} is enrolled in " +
//					"and doesn't have VIEW_COURSES authority [GET]", loggedUser.getDni(), docket);
//			return new ModelAndView(UNAUTHORIZED);
//		}
//
//		if (!model.containsAttribute("inscriptionForm")) {
//			model.addAttribute("inscriptionForm", new InscriptionForm());
//		}
//		if (!model.containsAttribute("gradeForm")) {
//			model.addAttribute("gradeForm", new GradeForm());
//		}
//
//		final CourseFilter courseFilter = new CourseFilter.CourseFilterBuilder().
//				id(courseFilterForm.getId()).keyword(courseFilterForm.getName()).build();
//
//		final ModelAndView mav = new ModelAndView("courses");
//
//		final List<Course> courses = loadCoursesForDocketByFilter(docket, mav, errors, courseFilter);
//		if (courses == null) {
//			return NOT_FOUND_MAV;
//		}
//
//		final Student student = studentService.getByDocket(docket);
//		if (student == null) {
//			return NOT_FOUND_MAV;
//		}
//
//		mav.addObject("student", student);
//		mav.addObject("section2", "courses");
//		mav.addObject("courseFilterFormAction", "/students/" + docket + "/courses");
//		mav.addObject("inscriptionFormAction", "/students/" + docket + "/courses/unenroll");
//		mav.addObject("gradeFormAction", "/students/" + docket + "/grades/add");
//		mav.addObject("subsection_courses", true); /* only different line from /inscription */
//		mav.addObject("courses", courses);
//		mav.addObject("docket", docket);
//		return mav;
//	}

//	@RequestMapping(value = "/students/{docket}/courses/unenroll", method = RequestMethod.POST)
//	public ModelAndView unenroll(@PathVariable final Integer docket,
//								 @Valid @ModelAttribute("inscriptionForm") InscriptionForm inscriptionForm,
//								 final BindingResult errors,
//								 final RedirectAttributes redirectAttributes,
//								 @ModelAttribute("user") UserSessionDetails loggedUser) {
//
//		if (!loggedUser.hasAuthority("DELETE_INSCRIPTION")
//				|| (loggedUser.getId() != docket && !loggedUser.hasAuthority("ADMIN"))) {
//			LOGGER.warn("User {} tried to unenroll a student {} from a course and doesn't have DELETE_INSCRIPTION authority [POST]", loggedUser.getDni(), docket);
//			return new ModelAndView(UNAUTHORIZED);
//		}
//		if (errors.hasErrors()) {
//			LOGGER.warn("User {} could not unenroll docket {} due to {} [POST]", loggedUser.getDni(), docket, errors.getAllErrors());
//			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.inscriptionForm", errors);
//			redirectAttributes.addFlashAttribute("inscriptionForm", inscriptionForm);
//			return new ModelAndView("redirect:/students/" + docket + "/courses");
//		}
//
//		final boolean done = studentService.unenroll(inscriptionForm.getStudentDocket(), inscriptionForm.getCourseId());
//
//		if (!done) {
//			LOGGER.warn("User {} could not unenroll a student {}, Result = {}", loggedUser.getDni(), docket, done);
//			redirectAttributes.addFlashAttribute("alert", "danger");
//			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("ERROR_UNKNOWN", null, Locale.getDefault()));
//		} else {
//			LOGGER.info("User {} unenrolled student {} successfully", loggedUser.getDni(), docket);
//			redirectAttributes.addFlashAttribute("alert", "success");
//			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("unenroll_success",
//					new Object[] {inscriptionForm.getCourseId(), inscriptionForm.getCourseName()},
//					Locale.getDefault()));
//		}
//
//		return new ModelAndView("redirect:/students/" + docket + "/courses");
//
//	}

	@RequestMapping(value = "/students/{docket}/inscription", method = RequestMethod.GET)
	public ModelAndView studentInscription(@PathVariable final int docket, final Model model,
	                                       @ModelAttribute("user") UserSessionDetails loggedUser,
	                                       @Valid @ModelAttribute("courseFilterForm")
	                                           final CourseFilterForm courseFilterForm,
	                                       final BindingResult errors) {

		if (!loggedUser.hasAuthority("ADD_INSCRIPTION")
				|| (loggedUser.getId() != docket && !loggedUser.hasAuthority("ADMIN"))) {
			LOGGER.warn("User {} tried to add inscription for student {} and doesn't have ADD_INSCRIPTION authority [GET]", loggedUser.getDni(), docket);
			return new ModelAndView(UNAUTHORIZED);
		}

		if (!model.containsAttribute("inscriptionForm")) {
			model.addAttribute("inscriptionForm", new InscriptionForm());
		}

		final ModelAndView mav = new ModelAndView("courses");

		final CourseFilter courseFilter = new CourseFilter.CourseFilterBuilder().
				id(courseFilterForm.getId()).keyword(courseFilterForm.getName()).build();

		final List<Course> courses = loadAvailableInscriptionsForDocketByFilter(docket, mav, errors, courseFilter);
		if (courses == null) {
			return NOT_FOUND_MAV;
		}

		final Student student = studentService.getByDocket(docket);
		if (student == null) {
			return NOT_FOUND_MAV;
		}

		mav.addObject("student", student);
		mav.addObject("section2", "inscription");
		mav.addObject("courseFilterFormAction", "/students/" + docket + "/inscription");
		mav.addObject("inscriptionFormAction", "/students/" + docket + "/inscription");
		mav.addObject("subsection_enroll", true);
		mav.addObject("courses", courses);
		mav.addObject("docket", docket);
		return mav;
	}

//	@RequestMapping(value = "/students/{docket}/inscription", method = RequestMethod.POST)
//	public ModelAndView studentInscription(@PathVariable final int docket,
//										   @Valid @ModelAttribute("inscriptionForm") InscriptionForm inscriptionForm,
//										   final BindingResult errors,
//										   final RedirectAttributes redirectAttributes,
//										   @ModelAttribute("user") UserSessionDetails loggedUser) {
//
//		if (!loggedUser.hasAuthority("ADD_INSCRIPTION")
//				|| (loggedUser.getId() != docket && !loggedUser.hasAuthority("ADMIN"))) {
//			LOGGER.warn("User {} tried to add inscription for student {} and doesn't have ADD_INSCRIPTION authority [POST]", loggedUser.getDni(), docket);
//			return new ModelAndView(UNAUTHORIZED);
//		}
//		if (errors.hasErrors()){
//			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.inscriptionForm", errors);
//			redirectAttributes.addFlashAttribute("inscriptionForm", inscriptionForm);
//			return new ModelAndView("redirect:/students/" + docket + "/inscription");
//		}
//
//		boolean done = studentService.enroll(inscriptionForm.getStudentDocket(), inscriptionForm.getCourseId());
//
//		if (!done) {
//			LOGGER.warn("User {} could not add inscription for student {}, Result = {}", loggedUser.getDni(), docket, done);
//			redirectAttributes.addFlashAttribute("alert", "danger");
//			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("ERROR_CORRELATIVE_NOT_APPROVED", null, Locale.getDefault()));
//
//		} else {
//			LOGGER.info("User {} added inscription of student {} successfully", loggedUser.getDni(), docket);
//			redirectAttributes.addFlashAttribute("alert", "success");
//			redirectAttributes.addFlashAttribute("message",
//					messageSource.getMessage("inscription_success",
//							new Object[] {inscriptionForm.getCourseId(), inscriptionForm.getCourseName()},
//							Locale.getDefault()));
//		}
//
//		return new ModelAndView("redirect:/students/" + docket + "/inscription");
//	}

//	@RequestMapping(value = "/students/{docket}/inscription/courseFilterForm", method = RequestMethod.GET)
//	public ModelAndView studentInscriptionCourseFilter(@PathVariable final int docket,
//													   @Valid @ModelAttribute("courseFilterForm") final CourseFilterForm courseFilterForm,
//													   final BindingResult errors,
//													   final RedirectAttributes redirectAttributes) {
//		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.courseFilterForm", errors);
//		redirectAttributes.addFlashAttribute("courseFilterForm", courseFilterForm);
//		return new ModelAndView("redirect:/students/" + docket + "/inscription");
//	}
//
//	@RequestMapping(value = "/students/{docket}/courses/courseFilterForm", method = RequestMethod.GET)
//	public ModelAndView studentCoursesCourseFilter(@PathVariable final int docket,
//												   @Valid @ModelAttribute("courseFilterForm") final CourseFilterForm courseFilterForm,
//												   final BindingResult errors,
//												   final RedirectAttributes redirectAttributes) {
//		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.courseFilterForm", errors);
//		redirectAttributes.addFlashAttribute("courseFilterForm", courseFilterForm);
//		return new ModelAndView("redirect:/students/" + docket + "/courses");
//	}

//	@RequestMapping(value = "/students/add_student", method = RequestMethod.GET)
//	public ModelAndView addStudent(@ModelAttribute("studentForm") final StudentForm studentForm,
//								   RedirectAttributes redirectAttributes,
//								   @ModelAttribute("user") UserSessionDetails loggedUser) {
//
//		if (!loggedUser.hasAuthority("ADD_STUDENT")) {
//			LOGGER.warn("User {} tried to add student with DNI {} and doesn't have ADD_STUDENT authority [GET]", loggedUser.getDni(), studentForm.getDni());
//			return new ModelAndView(UNAUTHORIZED);
//		}
//		ModelAndView mav = new ModelAndView("addUser");
//		mav.addObject("section2", "addStudent");
//		HTTPErrorsController.setAlertMessages(mav, redirectAttributes);
//		return mav;
//	}


//	@RequestMapping(value = "/students/add_student", method = RequestMethod.POST)
//	public ModelAndView addStudent(@Valid @ModelAttribute("studentForm") StudentForm studentForm,
//								   final BindingResult errors, RedirectAttributes redirectAttributes,
//								   @ModelAttribute("user") UserSessionDetails loggedUser) {
//
//		if (!loggedUser.hasAuthority("ADD_STUDENT")) {
//			LOGGER.warn("User {} tried to add student with DNI {} and doesn't have ADD_STUDENT authority [POST]", loggedUser.getDni(), studentForm.getDni());
//			return new ModelAndView(UNAUTHORIZED);
//		}
//		if (errors.hasErrors()){
//			LOGGER.warn("User {} could not add student due to {} [POST]", loggedUser.getDni(), errors.getAllErrors());
//			/* set alert */
//			redirectAttributes.addFlashAttribute("alert", "danger");
//			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("formWithErrors", null, Locale.getDefault()));
//			/* --------- */
//			return addStudent(studentForm, redirectAttributes, loggedUser);
//		} else {
//			final Student student = studentForm.build();
//			boolean done;
//
//			try {
//				done = studentService.create(student);
//			} catch (final DataIntegrityViolationException e) {
//				LOGGER.warn("User {} could not add student with DNI {}. Reason: ", loggedUser.getDni(), student.getDni(), e);
//				done = false;
//			}
//
//			if(!done) {
//				redirectAttributes.addFlashAttribute("alert", "danger");
//				redirectAttributes.addFlashAttribute("message", messageSource.getMessage("USER_EXISTS_DNI", null, Locale.getDefault()));
//				return addStudent(studentForm, redirectAttributes, loggedUser);
//			}
//			redirectAttributes.addFlashAttribute("alert", "success");
//			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("addStudent_success",
//					null,
//					Locale.getDefault()));
//			return new ModelAndView("redirect:/students");
//		}
//	}

	@RequestMapping(value = "/students/{docket}/grades/edit", method = RequestMethod.POST)
	public ModelAndView editGrade(@Valid @ModelAttribute("gradeForm") GradeForm gradeForm, final BindingResult errors,
								  @PathVariable Integer docket, RedirectAttributes redirectAttributes,
								  @ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("EDIT_GRADE")
				|| (loggedUser.getId() != docket && !loggedUser.hasAuthority("ADMIN"))) {
			LOGGER.warn("User {} tried to edit grades of student with DNI {} and doesn't have EDIT_GRADE authority [POST]", loggedUser.getDni());
			return new ModelAndView(UNAUTHORIZED);
		}

		if (errors.hasErrors()){
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.gradeForm", errors);
			redirectAttributes.addFlashAttribute("gradeForm", gradeForm);
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message",
					messageSource.getMessage("editGrade_fail",
							new Object[] {},
							Locale.getDefault()));


			return new ModelAndView("redirect:/students/" + docket + "/grades");
		}

		gradeForm.setStudent(studentService.getByDocket(docket));

		Grade newGrade = gradeForm.build();

		boolean done = studentService.editGrade(newGrade, gradeForm.getOldGrade());
		if(!done){
			LOGGER.warn("User {} could not edit grades, Result = {}", loggedUser.getDni(), done);
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("ERROR_UNKNOWN", null, Locale.getDefault()));
			return new ModelAndView("redirect:/students/" + docket + "/grades");
		}
		redirectAttributes.addFlashAttribute("alert", "success");
		redirectAttributes.addFlashAttribute("message",
				messageSource.getMessage("editGrade_success",
						new Object[] {},
						Locale.getDefault()));
		return new ModelAndView("redirect:/students/" + docket + "/grades");
	}

//	@RequestMapping(value = "/students/{docket}/grades/add", method = RequestMethod.POST)
//	public ModelAndView addGrade(@PathVariable Integer docket,
//								 @Valid @ModelAttribute("gradeForm") GradeForm gradeForm,
//								 final BindingResult errors,
//								 RedirectAttributes redirectAttributes,
//								 @ModelAttribute("user") UserSessionDetails loggedUser) {
//
//		if (!loggedUser.hasAuthority("ADD_GRADE")
//				|| (loggedUser.getId() != docket && !loggedUser.hasAuthority("ADMIN"))) {
//			LOGGER.warn("User {} tried to add a grade and doesn't have ADD_GRADE authority [POST]", loggedUser.getDni());
//			return new ModelAndView(UNAUTHORIZED);
//		}
//
//		final Integer c = gradeForm.getCourseId();
//		if (c == null) {
//			final String referrer = request.getHeader("referer");
//			return new ModelAndView("redirect:" + referrer);
//		}
//
//		if (errors.hasErrors()) {
//			LOGGER.warn("User {} could not add a grade due to {} [POST]", loggedUser.getDni(), errors.getAllErrors());
//			redirectAttributes.addFlashAttribute("alert", "danger");
//			redirectAttributes.addFlashAttribute("message",
//					messageSource.getMessage("addGrade_fail",
//							new Object[] {},
//							Locale.getDefault()));
//			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.gradeForm", errors);
//			redirectAttributes.addFlashAttribute("gradeForm", gradeForm);
//
//			return new ModelAndView("redirect:/courses/" + c + "/students");
//		}
//
//		/********************************/
//
//		gradeForm.setStudent(studentService.getByDocket(docket));
//		Grade grade = gradeForm.build();
//		boolean done = studentService.addGrade(grade);
//
//		if (!done) {
//			LOGGER.warn("User {} could not add a grade, Result is NULL", loggedUser.getDni());
//			redirectAttributes.addFlashAttribute("alert", "danger");
//			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("ERROR_UNKNOWN", null, Locale.getDefault()));
//		} else {
//			LOGGER.info("User {} added a grade successfully", loggedUser.getDni());
//			redirectAttributes.addFlashAttribute("alert", "success");
//			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("addGrade_success",
//					new Object[] {},
//					Locale.getDefault()));
//		}
//
//		return new ModelAndView("redirect:/courses/" + c + "/students");
//	}

//	@RequestMapping(value = "/students/{docket}/delete", method = RequestMethod.POST)
//	public ModelAndView removeStudent(@PathVariable final Integer docket, RedirectAttributes redirectAttributes,
//									  @ModelAttribute("user") UserSessionDetails loggedUser) {
//
//		if (!loggedUser.hasAuthority("DELETE_STUDENT")
//				|| (loggedUser.getId() != docket && !loggedUser.hasAuthority("ADMIN"))) {
//			LOGGER.warn("User {} tried to delete a student and doesn't have DELETE_STUDENT authority [POST]", loggedUser.getDni());
//			return new ModelAndView(UNAUTHORIZED);
//		}
//		studentService.deleteStudent(docket);
//		redirectAttributes.addFlashAttribute("alert", "success");
//		redirectAttributes.addFlashAttribute("message", messageSource.getMessage("deleteStudent_success",
//				null,
//				Locale.getDefault()));
//
//		return new ModelAndView("redirect:/students");
//	}

//	@RequestMapping("/students/{docket}/edit")
//	public ModelAndView editStudent(
//			@PathVariable final Integer docket,
//			@ModelAttribute("studentForm") final StudentForm studentForm,
//			final RedirectAttributes redirectAttributes,
//			@ModelAttribute("user") UserSessionDetails loggedUser) {
//
//		if (!loggedUser.hasAuthority("EDIT_STUDENT")
//				|| (loggedUser.getId() != docket && !loggedUser.hasAuthority("ADMIN"))) {
//			LOGGER.warn("User {} tried to edit a student and doesn't have EDIT_STUDENT authority [GET]", loggedUser.getDni());
//			return new ModelAndView(UNAUTHORIZED);
//		}
//
//		final ModelAndView mav = new ModelAndView("addUser");
//
//		HTTPErrorsController.setAlertMessages(mav, redirectAttributes);
//		Student student = studentService.getByDocket(docket);
//
//		if (student == null){
//			redirectAttributes.addFlashAttribute("alert", "danger");
//			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("editStudent_fail",
//					null,
//					Locale.getDefault()));
//			return new ModelAndView("redirect:/students");
//		}
//
//		if (!redirectAttributes.getFlashAttributes().containsKey("justCalled")) {
//			studentForm.loadFromStudent(student);
//		}
//
//		mav.addObject("docket", docket);
//		mav.addObject("student", student);
//		mav.addObject("section2", "edit");
//		return mav;
//	}

//	@RequestMapping(value = "/students/{docket}/edit", method = RequestMethod.POST)
//	public ModelAndView editStudent(@PathVariable final Integer docket,
//									@Valid @ModelAttribute("studentForm") StudentForm studentForm,
//									final BindingResult errors,
//									RedirectAttributes redirectAttributes,
//									@ModelAttribute("user") UserSessionDetails loggedUser) {
//
//		if (!loggedUser.hasAuthority("EDIT_STUDENT")
//				|| (loggedUser.getId() != docket && !loggedUser.hasAuthority("ADMIN"))) {
//			LOGGER.warn("User {} tried to edit a student and doesn't have EDIT_STUDENT authority [POST]", loggedUser.getDni());
//			return new ModelAndView(UNAUTHORIZED);
//		}
//		if (errors.hasErrors()){
//			LOGGER.warn("User {} could not edit student due to {} [POST]", loggedUser.getDni(), errors.getAllErrors());
//			/* set alert */
//			redirectAttributes.addFlashAttribute("alert", "danger");
//			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("formWithErrors", null, Locale.getDefault()));
//			/* --------- */
//			redirectAttributes.addFlashAttribute("justCalled", true);
//			return editStudent(docket, studentForm, redirectAttributes, loggedUser);
//		}
//
//		final Student s = studentService.getByDocket(docket);
//
//
//
//		// Write data that was hidden at the form --> not submitted
//		studentForm.setId_seq(s.getId_seq());
//		studentForm.setAddress_id_seq(s.getAddress().getId_seq());
//		studentForm.setPassword(s.getPassword());
//		studentForm.setEmail(s.getEmail());
//
//		Student student = studentForm.build();
//
//		final boolean done = studentService.update(docket, student);
//
//		if(!done) {
//			LOGGER.warn("User {} could not edit student, Result = {}", loggedUser.getDni());
//			redirectAttributes.addFlashAttribute("alert", "danger");
//			redirectAttributes.addFlashAttribute("message",
//					messageSource.getMessage("ERROR_UNKNOWN", null, Locale.getDefault()));
//			redirectAttributes.addFlashAttribute("justCalled", true);
//			return editStudent(docket, studentForm, redirectAttributes, loggedUser);
//		}
//
//		redirectAttributes.addFlashAttribute("alert", "success");
//		redirectAttributes.addFlashAttribute("message", messageSource.getMessage("editStudent_success",
//				null,
//				Locale.getDefault()));
//
//		final String referrer = request.getHeader("referer");
//		return new ModelAndView("redirect:" + referrer);
//	}


	@RequestMapping(value = "/students/{docket}/final_inscription", method = RequestMethod.GET)
	public ModelAndView studentFinalInscription(@PathVariable final int docket, final Model model,
										   @ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("ADD_FINAL_INSCRIPTION")
				|| (loggedUser.getId() != docket && !loggedUser.hasAuthority("ADMIN"))) {
			LOGGER.warn("User {} tried to add inscription for student {} and doesn't have ADD_FINAL_INSCRIPTION authority [GET]", loggedUser.getDni(), docket);
			return new ModelAndView(UNAUTHORIZED);
		}


		if (!model.containsAttribute("finalInscriptionForm")) {
			model.addAttribute("finalInscriptionForm", new FinalInscriptionForm());
		}


		// +++ximprove with Spring Security
		final Student student = studentService.getByDocket(docket);
		if (student == null) {
			return new ModelAndView("forward:/errors/404.html");
		}

		final ModelAndView mav = new ModelAndView("finalInscription2");
		mav.addObject("student", student);
		mav.addObject("section2", "final_inscription");
		mav.addObject("finalInscriptionFormAction", "/students/" + docket + "/final_inscription");
        mav.addObject("finalInscriptionDropFormAction", "/students/" + docket + "/drop_inscription");

		mav.addObject("finalInscriptions", studentService.getAvailableFinalInscriptions(docket));
		mav.addObject("docket", docket);

		mav.addObject("finalInscriptionsTaken", studentService.getFinalInscriptionsTaken(docket));

        return mav;
	}

	@RequestMapping(value = "/students/{docket}/final_inscription", method = RequestMethod.POST)
	public ModelAndView studentFinalInscription(@PathVariable final int docket,
										   @Valid @ModelAttribute("finalInscriptionForm") FinalInscriptionForm finalInscriptionForm,
										   final BindingResult errors,
										   final RedirectAttributes redirectAttributes,
										   @ModelAttribute("user") UserSessionDetails loggedUser) {

		if (!loggedUser.hasAuthority("ADD_FINAL_INSCRIPTION")
				|| (loggedUser.getId() != docket && !loggedUser.hasAuthority("ADMIN"))) {
			LOGGER.warn("User {} tried to add inscription for student {} and doesn't have ADD_FINAL_INSCRIPTION authority [POST]", loggedUser.getDni(), docket);
			return new ModelAndView(UNAUTHORIZED);
		}
		if (errors.hasErrors()){
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.finalInscriptionForm", errors);
			redirectAttributes.addFlashAttribute("finalInscriptionForm", finalInscriptionForm);
			return new ModelAndView("redirect:/students/" + docket + "/final_inscription");
		}

		//Result result = studentService.enroll(inscriptionForm.getStudentDocket(), inscriptionForm.getCourseId());
		//TODO: Create a method for creating finalInscription
		//Result result = studentService.enroll(finalInscriptionForm.getStudentDocket(), finalInscriptionForm.getCourseId());
		//TODO: Implement this: FinalInscription finalInscription = finalInscriptionForm.build();

		//FinalInscription finalInscription = studentService.getFinalInscription(finalInscriptionForm.getId());

		//TODO: check for vacancy
		boolean result = studentService.addStudentFinalInscription(docket, finalInscriptionForm.getId());


		if (!result) {
			LOGGER.warn("User {} could not add final inscription for student {}", loggedUser.getDni(), docket);
            redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("final_inscription_fail", null, Locale.getDefault()));

		} else {
			LOGGER.info("User {} added final inscription of student {} successfully", loggedUser.getDni(), docket);
			redirectAttributes.addFlashAttribute("alert", "success");
			redirectAttributes.addFlashAttribute("message",
					messageSource.getMessage("final_inscription_success", null, Locale.getDefault()));
		}

		return new ModelAndView("redirect:/students/" + docket + "/final_inscription");
	}

    @RequestMapping(value = "/students/{docket}/drop_inscription", method = RequestMethod.POST)
    public ModelAndView studentDropFinalInscription(@PathVariable final int docket,
                                                @Valid @ModelAttribute("finalInscriptionForm") FinalInscriptionForm finalInscriptionForm,
                                                final BindingResult errors,
                                                final RedirectAttributes redirectAttributes,
                                                @ModelAttribute("user") UserSessionDetails loggedUser) {

        if (!loggedUser.hasAuthority("DELETE_FINAL_INSCRIPTION")
                || (loggedUser.getId() != docket && !loggedUser.hasAuthority("ADMIN"))) {
            LOGGER.warn("User {} tried to delete inscription for student {} and doesn't have DELETE_FINAL_INSCRIPTION authority [POST]", loggedUser.getDni(), docket);
            return new ModelAndView(UNAUTHORIZED);
        }
        if (errors.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.finalInscriptionForm", errors);
            redirectAttributes.addFlashAttribute("finalInscriptionForm", finalInscriptionForm);
            return new ModelAndView("redirect:/students/" + docket + "/final_inscription");
        }


        boolean result = studentService.deleteStudentFinalInscription(docket, finalInscriptionForm.getId());


        if (!result) {
            //TODO: Ver que hacemos aca
            LOGGER.warn("User {} could not remove final inscription for student {}, Result = {}", loggedUser.getDni(), docket, result);
           // redirectAttributes.addFlashAttribute("alert", "danger");
            //redirectAttributes.addFlashAttribute("message", messageSource.getMessage(result.toString(), null, Locale.getDefault()));
        } else {
            LOGGER.info("User {} removed final inscription of student {} successfully", loggedUser.getDni(), docket);
            //redirectAttributes.addFlashAttribute("alert", "success");
            //redirectAttributes.addFlashAttribute("message",
            messageSource.getMessage("final_inscription_success", null, Locale.getDefault());
        }

        return new ModelAndView("redirect:/students/" + docket + "/final_inscription");
    }

	/**************************************************************/
	private List<Student> loadStudentsByFilter(final ModelAndView mav,
	                                           final BindingResult errors,
	                                           final StudentFilter studentFilter) {
		final List<Student> students;
		if (errors.hasErrors()) {
			LOGGER.warn("Could not get students due to {} [GET]", errors.getAllErrors());

			mav.addObject("alert", "danger");
			mav.addObject("message", messageSource.getMessage("search_fail",
					null,
					Locale.getDefault()));
			students = studentService.getByFilter(null);
		} else {
			students = studentService.getByFilter(studentFilter);
		}
		return students;
	}


	private List<Course> loadCoursesForDocketByFilter(final int docket, final ModelAndView mav, final BindingResult errors, final CourseFilter courseFilter) {
		final List<Course> courses;
		if (errors.hasErrors()) {
			LOGGER.warn("Could not get available inscription courses due to {} [GET]", errors.getAllErrors());

			mav.addObject("alert", "danger");
			mav.addObject("message", messageSource.getMessage("search_fail",
					null,
					Locale.getDefault()));
			courses = studentService.getStudentCourses(docket, null);
		} else {
			courses = studentService.getStudentCourses(docket, courseFilter);
		}
		return courses;
	}

	private List<Course> loadAvailableInscriptionsForDocketByFilter(final int docket,
	                                                  final ModelAndView mav,
	                                                  final BindingResult errors,
	                                                  final CourseFilter courseFilter) {
        final List<Course> courses;
        if (errors.hasErrors()) {
            LOGGER.warn("Could not get available inscription courses due to {} [GET]", errors.getAllErrors());

            mav.addObject("alert", "danger");
            mav.addObject("message", messageSource.getMessage("search_fail",
                    null,
                    Locale.getDefault()));
            courses = studentService.getAvailableInscriptionCourses(docket, null);
        } else {
            courses = studentService.getAvailableInscriptionCourses(docket, courseFilter);
        }
        return courses;
    }
}

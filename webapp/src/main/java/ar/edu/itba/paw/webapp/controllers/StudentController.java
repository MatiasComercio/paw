package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.Grade;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.shared.StudentFilter;
import ar.edu.itba.paw.webapp.auth.UserSessionDetails;
import ar.edu.itba.paw.webapp.forms.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

@Controller
public class StudentController { /* +++xchange: see if it's necessary to call this StudentController */

	private final static Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
	private static final String STUDENTS_SECTION = "students";

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

	@RequestMapping(value = "/students", method = RequestMethod.GET)
	public ModelAndView getStudents(@Valid @ModelAttribute("studentFilterForm") final StudentFilterForm studentFilterForm,
	                                final BindingResult errors,
	                                final Model model) {

		final ModelAndView mav = new ModelAndView("students");

		if (errors.hasErrors()) {
			/* Cancel current search */
			studentFilterForm.empty();

			mav.addObject("alert", "danger");
			mav.addObject("message", messageSource.getMessage("search_fail",
					null,
					Locale.getDefault()));
		}

		if (!model.containsAttribute("deleteStudentForm")) {
			model.addAttribute("deleteStudentForm", new StudentFilterForm()); /* +++xcheck: if it is necessary to create a new Form */
		}

		final StudentFilter studentFilter = new StudentFilter.StudentFilterBuilder()
				.docket(studentFilterForm.getDocket())
				.firstName(studentFilterForm.getFirstName())
				.lastName(studentFilterForm.getLastName())
				.build();

		// +++ximprove with Spring Security
		final List<Student> students = studentService.getByFilter(studentFilter);
		if (students == null) {
			return new ModelAndView("forward:/errors/404.html");
		}

		mav.addObject("students", students);
		mav.addObject("studentFilterFormAction", "/students");
		mav.addObject("subsection_students", true);
		return mav;
	}

	@RequestMapping("/students/{docket}/info")
	public ModelAndView getStudent(@PathVariable final int docket) {
		final Student student =  studentService.getByDocket(docket);

		final ModelAndView mav;

		if (student == null) {
			return new ModelAndView("forward:/errors/404.html");
		}

		mav = new ModelAndView("student");
		mav.addObject("student", student);
		mav.addObject("section2", "info");
		return mav;
	}

	@RequestMapping("/students/{docket}/grades")
	public ModelAndView getStudentGrades(@PathVariable final int docket, Model model,
	                                     RedirectAttributes redirectAttributes) {
		final Student student = studentService.getGrades(docket);
		final ModelAndView mav;
		final Integer totalCredits, passedCredits, percentage;

		if (student == null) {
			return new ModelAndView("forward:/errors/404.html");
		}

		if (!model.containsAttribute("gradeForm")) {
			model.addAttribute("gradeForm", new GradeForm());
		}

		mav = new ModelAndView("grades_old"); // +++xchange

		HTTPErrorsController.setAlertMessages(mav, redirectAttributes);

		mav.addObject("student", student);
		mav.addObject("section2", "grades");
		mav.addObject("subsection_grades", true);
		mav.addObject("gradeFormAction", "/students/" + docket + "/grades/edit");

		totalCredits = studentService.getTotalPlanCredits();
		passedCredits = studentService.getPassedCredits(docket);
		percentage = (!totalCredits.equals(0))? (passedCredits * 100)/totalCredits: 0;

		mav.addObject("semesters", studentService.getTranscript(docket));
		mav.addObject("total_credits", totalCredits);
		mav.addObject("passed_credits", passedCredits);
		mav.addObject("percentage", percentage);
		return mav;
	}

	@RequestMapping(value = "/students/{docket}/courses", method = RequestMethod.GET)
	public ModelAndView getStudentCourses(@PathVariable final int docket, final Model model) {

		if (!model.containsAttribute("courseFilterForm")) {
			model.addAttribute("courseFilterForm", new CourseFilterForm());
		}
		if (!model.containsAttribute("inscriptionForm")) {
			model.addAttribute("inscriptionForm", new InscriptionForm());
		}
		if (!model.containsAttribute("gradeForm")) {
			model.addAttribute("gradeForm", new GradeForm());
		}

		final CourseFilterForm courseFilterForm = (CourseFilterForm) model.asMap().get("courseFilterForm");
		final CourseFilter courseFilter = new CourseFilter.CourseFilterBuilder().
				id(courseFilterForm.getId()).keyword(courseFilterForm.getName()).build();

		// +++ximprove with Spring Security
		final Student student = studentService.getByDocket(docket);
		if (student == null) {
			return new ModelAndView("forward:/errors/404.html");
		}

		final ModelAndView mav = new ModelAndView("courses");
		mav.addObject("student", student);
		mav.addObject("section2", "courses");
		mav.addObject("courseFilterFormAction", "/students/" + docket + "/courses/courseFilterForm"); /* only different line from /inscription */
		mav.addObject("inscriptionFormAction", "/students/" + docket + "/courses/unenroll");
		mav.addObject("gradeFormAction", "/students/" + docket + "/grades/add");
		mav.addObject("subsection_courses", true); /* only different line from /inscription */
		mav.addObject("courses", studentService.getStudentCourses(docket, courseFilter));
		mav.addObject("docket", docket);
		return mav;
	}

	@RequestMapping(value = "/students/{docket}/courses/unenroll", method = RequestMethod.POST)
	public ModelAndView unenroll(@PathVariable final Integer docket,
	                             @Valid @ModelAttribute("inscriptionForm") InscriptionForm inscriptionForm,
	                             final BindingResult errors,
	                             final RedirectAttributes redirectAttributes){
		if (errors.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.inscriptionForm", errors);
			redirectAttributes.addFlashAttribute("inscriptionForm", inscriptionForm);
			return new ModelAndView("redirect:/students/" + docket + "/courses");
		}

		Result result = studentService.unenroll(inscriptionForm.getStudentDocket(), inscriptionForm.getCourseId());

		if (result == null) {
			result = Result.ERROR_UNKNOWN;
		}
		if (!result.equals(Result.OK)) {
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", result.getMessage());
		} else {
			redirectAttributes.addFlashAttribute("alert", "success");
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("unenroll_success",
					new Object[] {inscriptionForm.getCourseId(), inscriptionForm.getCourseName()},
					Locale.getDefault()));
		}

		return new ModelAndView("redirect:/students/" + docket + "/courses");

	}

	@RequestMapping(value = "/students/{docket}/inscription", method = RequestMethod.GET)
	public ModelAndView studentInscription(@PathVariable final int docket, final Model model) {

		if (!model.containsAttribute("courseFilterForm")) {
			model.addAttribute("courseFilterForm", new CourseFilterForm());
		}
		if (!model.containsAttribute("inscriptionForm")) {
			model.addAttribute("inscriptionForm", new InscriptionForm());
		}

		final CourseFilterForm courseFilterForm = (CourseFilterForm) model.asMap().get("courseFilterForm");
		final CourseFilter courseFilter = new CourseFilter.CourseFilterBuilder().
				id(courseFilterForm.getId()).keyword(courseFilterForm.getName()).build();

		// +++ximprove with Spring Security
		final Student student = studentService.getByDocket(docket);
		if (student == null) {
			return new ModelAndView("forward:/errors/404.html");
		}

		final ModelAndView mav = new ModelAndView("courses");
		mav.addObject("student", student);
		mav.addObject("courseFilterFormAction", "/students/" + docket + "/inscription/courseFilterForm");
		mav.addObject("inscriptionFormAction", "/students/" + docket + "/inscription");
		mav.addObject("subsection_enroll", true);
		mav.addObject("courses", studentService.getAvailableInscriptionCourses(docket, courseFilter));
		mav.addObject("docket", docket);
		return mav;
	}

	@RequestMapping(value = "/students/{docket}/inscription", method = RequestMethod.POST)
	public ModelAndView studentInscription(@PathVariable final int docket,
	                                       @Valid @ModelAttribute("inscriptionForm") InscriptionForm inscriptionForm,
	                                       final BindingResult errors,
	                                       final RedirectAttributes redirectAttributes) {
		if (errors.hasErrors()){
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.inscriptionForm", errors);
			redirectAttributes.addFlashAttribute("inscriptionForm", inscriptionForm);
			return new ModelAndView("redirect:/students/" + docket + "/inscription");
		}

		Result result = studentService.enroll(inscriptionForm.getStudentDocket(), inscriptionForm.getCourseId());
		if (result == null) {
			result = Result.ERROR_UNKNOWN;
		}
		if (!result.equals(Result.OK)) {
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", result.getMessage());

		} else {
			redirectAttributes.addFlashAttribute("alert", "success");
			redirectAttributes.addFlashAttribute("message",
					messageSource.getMessage("inscription_success",
							new Object[] {inscriptionForm.getCourseId(), inscriptionForm.getCourseName()},
							Locale.getDefault()));
		}

		return new ModelAndView("redirect:/students/" + docket + "/inscription");
	}

	@RequestMapping(value = "/students/{docket}/inscription/courseFilterForm", method = RequestMethod.GET)
	public ModelAndView studentInscriptionCourseFilter(@PathVariable final int docket,
	                                                   @Valid @ModelAttribute("courseFilterForm") final CourseFilterForm courseFilterForm,
	                                                   final BindingResult errors,
	                                                   final RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.courseFilterForm", errors);
		redirectAttributes.addFlashAttribute("courseFilterForm", courseFilterForm);
		return new ModelAndView("redirect:/students/" + docket + "/inscription");
	}

	@RequestMapping(value = "/students/{docket}/courses/courseFilterForm", method = RequestMethod.GET)
	public ModelAndView studentCoursesCourseFilter(@PathVariable final int docket,
	                                               @Valid @ModelAttribute("courseFilterForm") final CourseFilterForm courseFilterForm,
	                                               final BindingResult errors,
	                                               final RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.courseFilterForm", errors);
		redirectAttributes.addFlashAttribute("courseFilterForm", courseFilterForm);
		return new ModelAndView("redirect:/students/" + docket + "/courses");
	}


	@RequestMapping(value = "/students/add_student", method = RequestMethod.GET)
	public ModelAndView addStudent(@ModelAttribute("studentForm") final StudentForm studentForm,
	                               RedirectAttributes redirectAttributes){
		ModelAndView mav = new ModelAndView("addUser");
		mav.addObject("section2", "addStudent");
		HTTPErrorsController.setAlertMessages(mav, redirectAttributes);
		return mav;
	}

	@RequestMapping(value = "/students/add_student", method = RequestMethod.POST)
	public ModelAndView addStudent(@Valid @ModelAttribute("studentForm") StudentForm studentForm,
	                               final BindingResult errors, RedirectAttributes redirectAttributes) {
		if (errors.hasErrors()){
			return addStudent(studentForm, null);
		}
		else{
			Student student = studentForm.build();
			Result result = studentService.create(student);
			if(!result.equals(Result.OK)){
				redirectAttributes.addFlashAttribute("alert", "danger");
				redirectAttributes.addFlashAttribute("message", result.getMessage());
				return addStudent(studentForm, redirectAttributes);
			}
			redirectAttributes.addFlashAttribute("alert", "success");
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("addStudent_success",
					null,
					Locale.getDefault()));
			return new ModelAndView("redirect:/students");
		}
	}

//	@RequestMapping(value = "/students/{docket}/grades/edit/{courseName}/{grade}/{modified}/{courseId}", method = RequestMethod.GET)
//	public ModelAndView editGrade(@ModelAttribute("gradeForm") GradeForm gradeForm,
//	                              @PathVariable Integer docket, @PathVariable Integer courseId, @PathVariable Timestamp modified,
//								  @PathVariable BigDecimal grade, @PathVariable String courseName,
//								  RedirectAttributes redirectAttributes){
//		ModelAndView mav = new ModelAndView("editGrade");
//		HTTPErrorsController.setAlertMessages(mav, redirectAttributes);
//		gradeForm.setGrade(grade); //Set the old grade (to be displayed in the edit view)
//		gradeForm.setDocket(docket);
//		gradeForm.setCourseId(courseId);
//		gradeForm.setModified(modified);
//		gradeForm.setCourseName(courseName); //Avoid the @NotBlank validation
//
//		mav.addObject("docket", docket);
//		mav.addObject("courseId", courseId);
//		mav.addObject("courseName", courseName);
//
//		return mav;
//	}

	@RequestMapping(value = "/students/{docket}/grades/edit", method = RequestMethod.POST)
	public ModelAndView editGrade(@Valid @ModelAttribute("gradeForm") GradeForm gradeForm, final BindingResult errors,
	                              @PathVariable Integer docket, RedirectAttributes redirectAttributes){

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

		Grade newGrade = gradeForm.build();

		Result result = studentService.editGrade(newGrade, gradeForm.getOldGrade());
		if(!result.equals(Result.OK)){
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", result.getMessage());
			return new ModelAndView("redirect:/students/" + docket + "/grades");
		}
		redirectAttributes.addFlashAttribute("alert", "success");
		redirectAttributes.addFlashAttribute("message",
				messageSource.getMessage("editGrade_success",
						new Object[] {},
						Locale.getDefault()));
		return new ModelAndView("redirect:/students/" + docket + "/grades");

	}

	@RequestMapping(value = "/students/{docket}/grades/add", method = RequestMethod.POST)
	public ModelAndView addGrade(@PathVariable Integer docket,
	                             @Valid @ModelAttribute("gradeForm") GradeForm gradeForm,
	                             final BindingResult errors,
	                             RedirectAttributes redirectAttributes){
		if (errors.hasErrors()) {
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message",
					messageSource.getMessage("addGrade_fail",
							new Object[] {},
							Locale.getDefault()));
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.gradeForm", errors);
			redirectAttributes.addFlashAttribute("gradeForm", gradeForm);
			return new ModelAndView("redirect:/students/" + docket + "/courses");
		}

		/********************************/

		/*gradeForm.setDocket(docket);*/
		Grade grade = gradeForm.build();
		Result result = studentService.addGrade(grade);

		if (result == null) {
			result = Result.ERROR_UNKNOWN;
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", result.getMessage());
		} else if (!result.equals(Result.OK)) {
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", result.getMessage());
		} else {
			redirectAttributes.addFlashAttribute("alert", "success");
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("addGrade_success",
					new Object[] {},
					Locale.getDefault()));
		}

		return new ModelAndView("redirect:/students/" + docket + "/courses");

		/********************/

/*		*//*gradeForm.setDocket(docket);*//*
		Grade grade = gradeForm.build();
		Result result = studentService.addGrade(grade);
		if(!result.equals(Result.OK)) {
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", result.getMessage());
			return addGrade(gradeForm, redirectAttributes);
		}
		redirectAttributes.addFlashAttribute("alert", "success");
		redirectAttributes.addFlashAttribute("message", "La nota se ha guardado correctamente.");
		*//* +++xchange redirect *//*
		return new ModelAndView("redirect:/students/" + docket + "/info");*/
	}

	@RequestMapping(value = "/students/{docket}/delete", method = RequestMethod.POST)
	public ModelAndView removeStudent(@PathVariable final Integer docket, RedirectAttributes redirectAttributes) {
		final Result result = studentService.deleteStudent(docket);
//		ModelAndView mav = new ModelAndView("studentsSearch");
		redirectAttributes.addFlashAttribute("alert", "success");
		redirectAttributes.addFlashAttribute("message", messageSource.getMessage("deleteStudent_success",
				null,
				Locale.getDefault()));

		return new ModelAndView("redirect:/students");
	}

	@RequestMapping("/students/{docket}/edit")
	public ModelAndView editStudent(
			@PathVariable final Integer docket,
			@ModelAttribute("studentForm") final StudentForm studentForm,
			final RedirectAttributes redirectAttributes) {

		final ModelAndView mav = new ModelAndView("addUser");

		HTTPErrorsController.setAlertMessages(mav, redirectAttributes);
		Student student = studentService.getByDocket(docket);

		if (student == null){
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", messageSource.getMessage("editStudent_fail",
					null,
					Locale.getDefault()));
			return new ModelAndView("redirect:/students");
		}

		studentForm.loadFromStudent(student);

		mav.addObject("docket", docket);
		mav.addObject("student", student);
		mav.addObject("section2", "edit");
		return mav;
	}


	@RequestMapping(value = "/students/{docket}/edit", method = RequestMethod.POST)
	public ModelAndView editStudent(@PathVariable final Integer docket,
	                                @Valid @ModelAttribute("studentForm") StudentForm studentForm,
	                                final BindingResult errors,
	                                RedirectAttributes redirectAttributes){
		if (errors.hasErrors()){
			return editStudent(docket, studentForm, redirectAttributes);
		}

		Student student = studentForm.build();
		Result result = studentService.update(docket, student);

		if(!result.equals(Result.OK)){
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", result.getMessage());
			return editStudent(docket, studentForm, redirectAttributes);
		}

		redirectAttributes.addFlashAttribute("alert", "success");
		redirectAttributes.addFlashAttribute("message", messageSource.getMessage("editStudent_success",
				null,
				Locale.getDefault()));

		final String referrer = request.getHeader("referer");
		return new ModelAndView("redirect:" + referrer);
	}
}

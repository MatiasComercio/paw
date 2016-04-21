package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.webapp.forms.InscriptionForm;
import ar.edu.itba.paw.webapp.forms.StudentForm;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.StudentFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController { /* +++xchange: see if it's necessary to call this StudentController */

	private static final String STUDENTS_SECTION = "students";

	@Autowired
	private StudentService studentService;

	@RequestMapping("/students")
	public ModelAndView getStudentsByFilter(@RequestParam(required = false) final Integer docket,
	                                        @RequestParam(required = false) final String firstName,
	                                        @RequestParam(required = false) final String lastName,
	                                        @RequestParam(required = false) final String genre) {
		final ModelAndView mav = new ModelAndView("studentsSearch");
		final StudentFilter studentFilter = new StudentFilter.StudentFilterBuilder()
				.docket(docket)
				.firstName(firstName)
				.lastName(lastName)
				.genre(genre)
				.build();
		final List<Student> students = studentService.getByFilter(studentFilter);
		mav.addObject("students", students);
		mav.addObject("section", STUDENTS_SECTION);
		return mav;
	}

	@RequestMapping("/students/{docket}/info")
	public ModelAndView getStudent(@PathVariable final int docket) {
		final Student student =  studentService.getByDocket(docket);

		final ModelAndView mav;

		if (student == null) {
			return studentNotFound(docket);
		}

		mav = new ModelAndView("student");
		mav.addObject("student", student);
		mav.addObject("section", STUDENTS_SECTION);
		return mav;
	}

	@RequestMapping("/students/{docket}/grades")
	public ModelAndView getStudentGrades(@PathVariable final int docket) {
		final Student student =  studentService.getGrades(docket);

		final ModelAndView mav;

		if (student == null) {
			return studentNotFound(docket);
		}

		mav = new ModelAndView("grades");
		mav.addObject("student", student);
		mav.addObject("section", STUDENTS_SECTION);
		return mav;
	}

	private ModelAndView studentNotFound(final int docket) {
		final ModelAndView mav = new ModelAndView("error");
		mav.addObject("page_header", "No se pudo encontrar la página");
		mav.addObject("description", "El alumno con legajo " + docket + " no existe.");
		return mav;
	}

	@RequestMapping("/students/{docket}/courses")
	public ModelAndView getStudentsCourse(@PathVariable final Integer docket){
		final ModelAndView mav = new ModelAndView("courses");
		final List<Course> courses = studentService.getStudentCourses(docket);

		if (courses == null) {
			return studentNotFound(docket);
		}

		mav.addObject("courses", courses);
		mav.addObject("section", STUDENTS_SECTION);
		return mav;
	}


	@Autowired
	private CourseService courseService;

	@RequestMapping(value = "/students/{docket}/inscription", method = RequestMethod.GET)
	public ModelAndView studentInscription(@PathVariable final int docket,
											@ModelAttribute("inscriptionForm") final InscriptionForm inscriptionForm,
											@RequestParam(required = false) String keyword,
											@RequestParam(required = false) Integer id) {

		inscriptionForm.setStudentDocket(docket);
		ModelAndView mav = new ModelAndView("inscription");

		final CourseFilter courseFilter = new CourseFilter.CourseFilterBuilder().keyword(keyword).id(id).build();
		mav.addObject("courses", courseService.getByFilter(courseFilter));

		mav.addObject("section", STUDENTS_SECTION);
		return mav;
	}

	@RequestMapping(value = "/students/{docket}/inscription", method = RequestMethod.POST)
	public ModelAndView studentInscription(@PathVariable final int docket,
											@Valid @ModelAttribute("inscriptionForm") InscriptionForm inscriptionForm,
                                           @RequestParam(required = false) String keyword,
                                           @RequestParam(required = false) Integer id,
											final BindingResult errors,
											final RedirectAttributes redirectAttributes) {
		if (errors.hasErrors()){
			return studentInscription(docket, inscriptionForm, keyword, id);
		}

		final Result result = studentService.enroll(inscriptionForm.getStudentDocket(), inscriptionForm.getCourseId());
		if (result == null || !result.equals(Result.OK)) {
			redirectAttributes.addFlashAttribute("alert", "danger");
			redirectAttributes.addFlashAttribute("message", result.getMessage());
			return new ModelAndView("redirect:/app/students/" + docket + "/inscription");
		}
		redirectAttributes.addFlashAttribute("alert", "success");
		redirectAttributes.addFlashAttribute("message", "Inscripción realizada con éxito.");
		return new ModelAndView("redirect:/app/students/" + docket + "/info");
	}




	@RequestMapping(value = "/students/add_student", method = RequestMethod.GET)
	public ModelAndView addStudent(@ModelAttribute("studentForm") final StudentForm studentForm){
		//final ModelAndView mav = new ModelAndView("addStudent", "command", new StudentForm());
		ModelAndView mav = new ModelAndView("addStudent");
		mav.addObject("section", STUDENTS_SECTION);
		return mav;
	}

	@RequestMapping(value = "/students/add_student", method = RequestMethod.POST)
	public ModelAndView addStudent(@Valid @ModelAttribute("studentForm") StudentForm studentForm,
								   final BindingResult errors) {
		if (!errors.hasErrors()){
			Student student = studentForm.build();
			studentService.create(student);
			return new ModelAndView("redirect:/app/students");
		}
		else{
			return addStudent(studentForm);
		}
	}
}

package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.webapp.forms.StudentForm;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.StudentFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {
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

	@RequestMapping(value = "/students/add_student", method = RequestMethod.GET)
	public ModelAndView addStudent(){
		final ModelAndView mav = new ModelAndView("addStudent", "command", new StudentForm());
		mav.addObject("section", STUDENTS_SECTION);
		return mav;
	}

	@RequestMapping(value = "/students/add_student", method = RequestMethod.POST)
	public String addStudent(@ModelAttribute("addStudent") StudentForm studentForm) {
		Student student = studentForm.build();
		studentService.create(student);
		return "redirect:/app/students";
	}

}

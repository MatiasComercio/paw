package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.StudentFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {
	private static final String STUDENTS_SECTION = "students";

	@Autowired
	private StudentService studentService;

	@RequestMapping("/students")
	public ModelAndView getStudentsByFilter(@RequestParam(defaultValue = "") final Integer docket,
	                                        @RequestParam(defaultValue = "") final String firstName,
	                                        @RequestParam(defaultValue = "") final String lastName,
	                                        @RequestParam(defaultValue = "") final String genre) {
		final ModelAndView mav = new ModelAndView("students");
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
			mav = new ModelAndView("notFound");
			mav.addObject("description", "Student with docket " + docket + " does not exists.");
			return mav;
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
			mav = new ModelAndView("notFound");
			mav.addObject("description", "Student with docket " + docket + " does not exists.");
			return mav;
		}

		mav = new ModelAndView("grades");
		mav.addObject("student", student);
		mav.addObject("section", STUDENTS_SECTION);
		return mav;
	}

	@RequestMapping("/students/{docket}/courses")
	public ModelAndView getStudentsCourse(@PathVariable final Integer docket){
		final ModelAndView mav = new ModelAndView("courses");
		mav.addObject("courses", studentService.getStudentCourses(docket));
		mav.addObject("section", STUDENTS_SECTION);
		return mav;
	}
}

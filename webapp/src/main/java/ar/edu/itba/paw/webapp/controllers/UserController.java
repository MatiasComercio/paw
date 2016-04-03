package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.users.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

	@Autowired
	private StudentService studentService;

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
		return mav;
	}

	@RequestMapping("/students/{docket}/courses/")
	public ModelAndView getStudentsCourse(@PathVariable final Integer docket){
		final ModelAndView mav = new ModelAndView("courses");
		mav.addObject("courses", studentService.getStudentCourses(docket));
		return mav;
	}
}

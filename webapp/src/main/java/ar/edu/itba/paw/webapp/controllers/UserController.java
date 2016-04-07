package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.forms.StudentForm;
import ar.edu.itba.paw.models.users.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {
	private static final String STUDENTS_SECTION = "students";

	@Autowired
	private StudentService studentService;

	@RequestMapping("/students")
	public ModelAndView getAllStudents() {
		final ModelAndView mav = new ModelAndView("students");
        final List<Student> students =  studentService.getAll();
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

package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.shared.Result;
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
import java.util.Map;

@Controller
public class UserController {
	private static final String STUDENTS_SECTION = "students";

	@Autowired
	private StudentService studentService;

    @ModelAttribute("section")
    public String sectionManager(){
        return STUDENTS_SECTION;
    }

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
		return mav;
	}

	@RequestMapping(value = "/students/add_student", method = RequestMethod.GET)
	public ModelAndView addStudent(@ModelAttribute("studentForm") final StudentForm studentForm,
								   RedirectAttributes redirectAttributes){
		ModelAndView mav = new ModelAndView("addStudent");
		if(redirectAttributes != null) {
			Map<String, ?> raMap = redirectAttributes.getFlashAttributes();
			if (raMap.get("alert") != null) {
				mav.addObject("alert", raMap.get("alert"));
				mav.addObject("message", raMap.get("message"));
			}
		}
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
			redirectAttributes.addFlashAttribute("message", "El alumno se ha guardado correctamente.");
			return new ModelAndView("redirect:/app/students");
		}
	}

	@RequestMapping(value = "/students/{docket}/delete", method = RequestMethod.POST)
	public ModelAndView removeStudent(@PathVariable final Integer docket, RedirectAttributes redirectAttributes) {
		final Result result = studentService.deleteCourse(docket);
//		ModelAndView mav = new ModelAndView("studentsSearch");
		redirectAttributes.addFlashAttribute("message", result.getMessage());

		return new ModelAndView("redirect:/app/students");
	}
}

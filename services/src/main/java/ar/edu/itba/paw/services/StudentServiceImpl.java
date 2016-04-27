package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Grade;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.shared.StudentFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private CourseService courseService;

	@Override
	public Student getByDocket(final int docket) {
		return docket <= 0 ? null : studentDao.getByDocket(docket);
	}

	@Override
	public List<Course> getStudentCourses(final int docket, final CourseFilter courseFilter) {
		if (docket <= 0) {
			return null;
		}

		List<Course> courses = studentDao.getStudentCourses(docket);
		if (courses == null) {
			return null;
		}

		List<Course> coursesFiltered = courseService.getByFilter(courseFilter);
		courses.retainAll(coursesFiltered);

		return courses;
	}

	@Override
	public List<Student> getByFilter(StudentFilter studentFilter) {
		return studentDao.getByFilter(studentFilter);
	}

	@Override
	public Result create(Student student) {
		return studentDao.create(student);
	}

	@Override
	public Result deleteStudent(Integer docket) {
		if(docket <= 0) {
			return Result.ERROR_DOCKET_OUT_OF_BOUNDS;
		}
		return studentDao.deleteStudent(docket);
	}

	@Override
	public Result addGrade(final Grade grade) {
		if (grade == null) {
			return null;
		}

		Result result = studentDao.addGrade(grade);
		if (result != null && result.equals(Result.OK)) {
			result = studentDao.unenroll(grade.getStudentDocket(), grade.getCourseId());
		}
		return result;
	}

	@Override
	public Result editGrade(Grade newGrade, BigDecimal oldGrade) {
		return studentDao.editGrade(newGrade, oldGrade);
	}

	@Override
	public Result update(Integer docket, Student student) {
		final Integer dni = studentDao.getDniByDocket(docket);

        if (dni == null)
            return Result.STUDENT_NOT_EXISTS;

		if (studentDao.hasAddress(dni)){
            System.out.println("HAS DNI");
            studentDao.updateAddress(dni, student);
		} else {
            System.out.println("CREATE DNI");
            studentDao.createAddress(dni, student);
		}

		return studentDao.update(docket, dni, student);
	}

	@Override
	public Student getGrades(final int docket) {
		return docket <= 0 ? null : studentDao.getGrades(docket);
	}

	@Override
	public List<Course> getAvailableInscriptionCourses(final int docket, final CourseFilter courseFilter) {
		if (docket <= 0) {
			return null;
		}

		final List<Course> courses = courseService.getByFilter(courseFilter);

		if (courses == null) {
			return null;
		}

		Collection<Course> currentCourses = getStudentCourses(docket, null);
		if (currentCourses != null) {
			courses.removeAll(currentCourses);
		}

		Collection<Course> approvedCourses = getApprovedCourses(docket);
		if (currentCourses != null) {
			courses.removeAll(approvedCourses);
		}

		return courses;
	}

	@Override
	public Result enroll(final int studentDocket, final int courseId) {
		if (studentDocket <= 0 ) {
			return Result.ERROR_DOCKET_OUT_OF_BOUNDS;
		}
		if (courseId <= 0) {
			return Result.ERROR_ID_OUT_OF_BOUNDS;
		}

		Result result;

		/* +++xtodo: implement
		result = checkCorrelatives(studentDocket, courseId);
		if (result.equals(Result.DISPROVED_CORRELATIVES)) {
			return Result.ERROR_UNKNOWN;
		}
		*/

		result = studentDao.enroll(studentDocket, courseId);
		if (result == null) {
			return Result.ERROR_UNKNOWN;
		}
		if (!result.equals(Result.OK)) {
			return result;
		}

		/* notifyInscription(studentDocket, courseId); mail +++xtodo */

		return result;
	}

	@Override
	public Result unenroll(final Integer studentDocket, final Integer courseId) {
		if (studentDocket <= 0 ) {
			return Result.ERROR_DOCKET_OUT_OF_BOUNDS;
		}
		if (courseId <= 0) {
			return Result.ERROR_ID_OUT_OF_BOUNDS;
		}

		Result result;

		result = studentDao.unenroll(studentDocket, courseId);
		if (result == null) {
			return Result.ERROR_UNKNOWN;
		}
		if (!result.equals(Result.OK)) {
			return result;
		}

		/* notifyUnenrollment(studentDocket, courseId); mail +++xtodo */
		return result;
	}

	@Override
	public Collection<Course> getApprovedCourses(final int docket) {
		return docket <= 0 ? null : studentDao.getApprovedCourses(docket);
	}

	/* Test purpose only */
	/* default */ void setStudentDao(final StudentDao studentDao) {
		this.studentDao = studentDao;
	}
	/* Test purpose only */
	/* default */ void setCourseService(final CourseService courseService) {
		this.courseService = courseService;
	}
}

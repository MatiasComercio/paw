package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.shared.StudentFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<Course> getStudentCourses(int docket) {
		return docket <= 0 ? null : studentDao.getStudentCourses(docket);
	}

	@Override
	public List<Student> getByFilter(StudentFilter studentFilter) {
		return studentDao.getByFilter(studentFilter);
	}

	@Override
	public void create(final Student student) {
		studentDao.create(student);
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

		courses.removeAll(this.getStudentCourses(docket));

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

	/* Test purpose only */
	/* default */ void setStudentDao(final StudentDao studentDao) {
		this.studentDao = studentDao;
	}
	/* Test purpose only */
	/* default */ void setCourseService(final CourseService courseService) {
		this.courseService = courseService;
	}
}

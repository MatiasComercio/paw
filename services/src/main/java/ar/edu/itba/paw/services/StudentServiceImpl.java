package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.shared.StudentFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDao studentDao;

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
	public Result enroll(final int studentDocket, final int courseId) {
		if (studentDocket <= 0 || courseId <= 0) {
			System.out.println("Here");
			return Result.ERROR_UNKNOWN;
		}

		Result result;
		/* +++xtodo: implement
		result = checkCorrelatives(studentDocket, courseId);
		if (result.equals(Result.DISPROVED_CORRELATIVES)) {
			return Result.ERROR_UNKNOWN;
		}
		*/

		result = studentDao.enroll(studentDocket, courseId);
		System.out.println(result); /* +++xdebug */
		if (result == null) {
			return Result.ERROR_UNKNOWN;
		}
		if (!result.equals(Result.OK)) {
			return result;
		}

		/* notifyInscription(studentDocket, courseId); mail +++xtodo */

		return result;
	}

	/* Test purpose only */
	/* default */ void setStudentDao(final StudentDao studentDao) {
		this.studentDao = studentDao;
	}
}

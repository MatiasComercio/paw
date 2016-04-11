package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.StudentFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
	public Student getGrades(final int docket) {
		return docket <= 0 ? null : studentDao.getGrades(docket);
	}

	/* Test purpose only */
	/* default */ void setStudentDao(final StudentDao studentDao) {
		this.studentDao = studentDao;
	}
}
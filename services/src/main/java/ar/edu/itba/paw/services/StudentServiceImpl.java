package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.users.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDao studentDao;

	@Override
	public Student getByDocket(final int docket) {
		return docket < 0 ? null : studentDao.getByDocket(docket);
	}

	/* Test purpose only */
	/* default */ void setStudentDao(final StudentDao studentDao) {
		this.studentDao = studentDao;
	}
}

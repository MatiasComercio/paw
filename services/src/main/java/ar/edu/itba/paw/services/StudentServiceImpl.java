package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.Student;
import org.springframework.beans.factory.annotation.Autowired;

public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDao studentJdbcDao;

	@Override
	public Student getByDocket(final int docket) {
		return docket < 0 ? null : studentJdbcDao.getByDocket(docket);
	}
}

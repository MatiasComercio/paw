package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.users.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDao studentJdbcDao;

	@Override
	public Student getByDocket(final int docket) {
		return docket < 0 ? null : studentJdbcDao.getByDocket(docket);
	}
}

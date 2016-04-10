package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.StudentDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class StudentServiceImplTest {

	private static final int DOCKET_VALID = 7357;
	private static final int DOCKET_VALID_LIMIT = 1;
	private static final int DOCKET_INVALID_LIMIT = 0;
	private static final int DOCKET_INVALID = -7357;

	private StudentServiceImpl studentService;

	@Mock
	private StudentDao studentDao;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		studentService = new StudentServiceImpl();
		studentService.setStudentDao(studentDao);
	}

	@Test
	public void testGetByDocket() {
		/* Checks that studentDao.getByDocket() is called 1 time when input is valid */
		studentService.getByDocket(DOCKET_VALID);
		verify(studentDao, times(1)).getByDocket(DOCKET_VALID);

		/* Checks that studentDao.getByDocket() is called 1 time when input is valid */
		studentService.getByDocket(DOCKET_VALID_LIMIT);
		verify(studentDao, times(1)).getByDocket(DOCKET_VALID_LIMIT);

		/* Checks that studentDao.getByDocket() is no called (0 times) when input is invalid */
		studentService.getByDocket(DOCKET_INVALID_LIMIT);
		verify(studentDao, times(0)).getByDocket(DOCKET_INVALID_LIMIT);

		/* Checks that studentDao.getByDocket() is no called (0 times) when input is invalid */
		studentService.getByDocket(DOCKET_INVALID);
		verify(studentDao, times(0)).getByDocket(DOCKET_INVALID);
	}

	@Test
	public void testGetGrades() {
		/* Checks that studentDao.getGrades() is called 1 time when input is valid */
		studentService.getGrades(DOCKET_VALID);
		verify(studentDao, times(1)).getGrades(DOCKET_VALID);

		/* Checks that studentDao.getGrades() is called 1 time when input is valid */
		studentService.getGrades(DOCKET_VALID_LIMIT);
		verify(studentDao, times(1)).getGrades(DOCKET_VALID_LIMIT);

		/* Checks that studentDao.getGrades() is no called (0 times) when input is invalid */
		studentService.getGrades(DOCKET_INVALID_LIMIT);
		verify(studentDao, times(0)).getGrades(DOCKET_INVALID_LIMIT);

		/* Checks that studentDao.getGrades() is no called (0 times) when input is invalid */
		studentService.getGrades(DOCKET_INVALID);
		verify(studentDao, times(0)).getGrades(DOCKET_INVALID);
	}

	@Test
	public void testGetCourseStudents() {
		studentService.getStudentCourses(DOCKET_VALID);
		verify(studentDao, times(1)).getStudentCourses(DOCKET_VALID);

		/* Checks that studentDao.getGrades() is called 1 time when input is valid */
		studentService.getStudentCourses(DOCKET_VALID_LIMIT);
		verify(studentDao, times(1)).getStudentCourses(DOCKET_VALID_LIMIT);

		/* Checks that studentDao.getGrades() is no called (0 times) when input is invalid */
		studentService.getStudentCourses(DOCKET_INVALID_LIMIT);
		verify(studentDao, times(0)).getStudentCourses(DOCKET_INVALID_LIMIT);

		/* Checks that studentDao.getGrades() is no called (0 times) when input is invalid */
		studentService.getStudentCourses(DOCKET_INVALID);
		verify(studentDao, times(0)).getStudentCourses(DOCKET_INVALID);
	}
}

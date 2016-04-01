package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.StudentDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

// ++xdoing
public class StudentServiceImplTest {

	private static final int DOCKET_VALID = 7357;
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
		studentService.getByDocket(DOCKET_VALID);
		Mockito.verify(studentDao).getByDocket(Mockito.eq(DOCKET_VALID));

		/*Mockito.when(studentService.getByDocket(DOCKET_INVALID)).;

		Mockito.verify(studentDao, Mockito.times(1)).getByDocket(Mockito.eq(DOCKET_INVALID));*/
	}

}

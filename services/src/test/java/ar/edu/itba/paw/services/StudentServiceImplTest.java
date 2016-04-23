package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.shared.CourseFilter;
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

	private static final int COURSE_ID_VALID = 7357;
	private static final int COURSE_ID_VALID_LIMIT = 1;
	private static final int COURSE_ID_INVALID_LIMIT = 0;
	private static final int COURSE_ID_INVALID = -7357;

	private StudentServiceImpl studentService;

	@Mock
	private StudentDao studentDao;

	@Mock
	private CourseService courseService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		studentService = new StudentServiceImpl();
		studentService.setStudentDao(studentDao);
		studentService.setCourseService(courseService);
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

	@Test
	public void testEnroll() {
		studentService.enroll(DOCKET_VALID, COURSE_ID_VALID);
		verify(studentDao, times(1)).enroll(DOCKET_VALID, COURSE_ID_VALID);

		/* Checks that studentDao.getGrades() is called 1 time when input is valid */
		studentService.enroll(DOCKET_VALID_LIMIT, COURSE_ID_VALID_LIMIT);
		verify(studentDao, times(1)).enroll(DOCKET_VALID_LIMIT, COURSE_ID_VALID_LIMIT);

		/* Checks that studentDao.getGrades() is no called (0 times) when input is invalid */
		studentService.enroll(DOCKET_INVALID_LIMIT, COURSE_ID_INVALID_LIMIT);
		verify(studentDao, times(0)).enroll(DOCKET_INVALID_LIMIT, COURSE_ID_INVALID_LIMIT);

		/* Checks that studentDao.getGrades() is no called (0 times) when input is invalid */
		studentService.enroll(DOCKET_INVALID, COURSE_ID_INVALID);
		verify(studentDao, times(0)).enroll(DOCKET_INVALID, COURSE_ID_INVALID);
	}

	@Test
	public void testUnenroll() {
		studentService.unenroll(DOCKET_VALID, COURSE_ID_VALID);
		verify(studentDao, times(1)).unenroll(DOCKET_VALID, COURSE_ID_VALID);

		/* Checks that studentDao.getGrades() is called 1 time when input is valid */
		studentService.unenroll(DOCKET_VALID_LIMIT, COURSE_ID_VALID_LIMIT);
		verify(studentDao, times(1)).unenroll(DOCKET_VALID_LIMIT, COURSE_ID_VALID_LIMIT);

		/* Checks that studentDao.getGrades() is no called (0 times) when input is invalid */
		studentService.unenroll(DOCKET_INVALID_LIMIT, COURSE_ID_INVALID_LIMIT);
		verify(studentDao, times(0)).unenroll(DOCKET_INVALID_LIMIT, COURSE_ID_INVALID_LIMIT);

		/* Checks that studentDao.getGrades() is no called (0 times) when input is invalid */
		studentService.unenroll(DOCKET_INVALID, COURSE_ID_INVALID);
		verify(studentDao, times(0)).unenroll(DOCKET_INVALID, COURSE_ID_INVALID);
	}

	@Test
	public void testGetAvailableInscriptionCourses() {
		CourseFilter courseFilter = new CourseFilter.CourseFilterBuilder().build();

		/* Checks when input is valid */
		studentService.getAvailableInscriptionCourses(DOCKET_VALID, null);
		verify(courseService, times(1)).getByFilter(null);
		verify(studentDao, times(1)).getStudentCourses(DOCKET_VALID);

		studentService.getAvailableInscriptionCourses(DOCKET_VALID, courseFilter);
		verify(courseService, times(1)).getByFilter(courseFilter);
		verify(studentDao, times(2)).getStudentCourses(DOCKET_VALID);

		/* Checks when input is valid */
		studentService.getAvailableInscriptionCourses(DOCKET_VALID_LIMIT, null);
		verify(courseService, times(2)).getByFilter(null);
		verify(studentDao, times(1)).getStudentCourses(DOCKET_VALID_LIMIT);

		studentService.getAvailableInscriptionCourses(DOCKET_VALID_LIMIT, courseFilter);
		verify(courseService, times(2)).getByFilter(courseFilter);
		verify(studentDao, times(2)).getStudentCourses(DOCKET_VALID_LIMIT);

		/* Checks when input is invalid */
		studentService.getAvailableInscriptionCourses(DOCKET_INVALID_LIMIT, null);
		verify(courseService, times(2)).getByFilter(null);
		verify(studentDao, times(0)).getStudentCourses(DOCKET_INVALID_LIMIT);

		studentService.getAvailableInscriptionCourses(DOCKET_INVALID_LIMIT, courseFilter);
		verify(courseService, times(2)).getByFilter(courseFilter);
		verify(studentDao, times(0)).getStudentCourses(DOCKET_INVALID_LIMIT);

		/* Checks when input is invalid */
		studentService.getAvailableInscriptionCourses(DOCKET_INVALID, null);
		verify(courseService, times(2)).getByFilter(null);
		verify(studentDao, times(0)).getStudentCourses(DOCKET_INVALID);

		studentService.getAvailableInscriptionCourses(DOCKET_INVALID, courseFilter);
		verify(courseService, times(2)).getByFilter(courseFilter);
		verify(studentDao, times(0)).getStudentCourses(DOCKET_INVALID);
	}
}

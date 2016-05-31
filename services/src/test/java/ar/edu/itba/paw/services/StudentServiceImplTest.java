package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class StudentServiceImplTest {

	private static final int DOCKET_VALID = 7357;
	private static final int DOCKET_VALID_LIMIT = 1;
	private static final int DOCKET_INVALID_LIMIT = 0;
	private static final int DOCKET_INVALID = -7357;

	private static final int DNI_VALID = 12345678;
	private static final int DNI_VALID_LIMIT = 1;
	private static final int DNI_INVALID_LIMIT = 0;
	private static final int DNI_INVALID = -1;

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
	public void testGetByDni() {
		studentService.getByDni(DNI_VALID);
		verify(studentDao, times(1)).getByDni(DNI_VALID);

		studentService.getByDni(DNI_VALID_LIMIT);
		verify(studentDao, times(1)).getByDni(DNI_VALID_LIMIT);

		studentService.getByDni(DNI_INVALID_LIMIT);
		verify(studentDao, times(0)).getByDni(DNI_INVALID_LIMIT);

		studentService.getByDni(DNI_INVALID);
		verify(studentDao, times(0)).getByDni(DNI_INVALID);
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
	public void testGetStudentCourses() {
		CourseFilter courseFilter = new CourseFilter.CourseFilterBuilder().build();

		/* Checks when input is valid */
		studentService.getStudentCourses(DOCKET_VALID, null);
		verify(courseService, times(1)).getByFilter(null);
		verify(studentDao, times(1)).getStudentCourses(DOCKET_VALID);

		studentService.getStudentCourses(DOCKET_VALID, courseFilter);
		verify(courseService, times(1)).getByFilter(courseFilter);
		verify(studentDao, times(2)).getStudentCourses(DOCKET_VALID);

		/* Checks when input is valid */
		studentService.getStudentCourses(DOCKET_VALID_LIMIT, null);
		verify(courseService, times(2)).getByFilter(null);
		verify(studentDao, times(1)).getStudentCourses(DOCKET_VALID_LIMIT);

		studentService.getStudentCourses(DOCKET_VALID_LIMIT, courseFilter);
		verify(courseService, times(2)).getByFilter(courseFilter);
		verify(studentDao, times(2)).getStudentCourses(DOCKET_VALID_LIMIT);

		/* Checks when input is invalid */
		studentService.getStudentCourses(DOCKET_INVALID_LIMIT, null);
		verify(courseService, times(2)).getByFilter(null);
		verify(studentDao, times(0)).getStudentCourses(DOCKET_INVALID_LIMIT);

		studentService.getStudentCourses(DOCKET_INVALID_LIMIT, courseFilter);
		verify(courseService, times(2)).getByFilter(courseFilter);
		verify(studentDao, times(0)).getStudentCourses(DOCKET_INVALID_LIMIT);

		/* Checks when input is invalid */
		studentService.getStudentCourses(DOCKET_INVALID, null);
		verify(courseService, times(2)).getByFilter(null);
		verify(studentDao, times(0)).getStudentCourses(DOCKET_INVALID);

		studentService.getStudentCourses(DOCKET_INVALID, courseFilter);
		verify(courseService, times(2)).getByFilter(courseFilter);
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
		/* Note that getStudentCourses calls getByFilter with null value too */

		/* Checks when input is valid */
		studentService.getAvailableInscriptionCourses(DOCKET_VALID, null);
		verify(courseService, times(2)).getByFilter(null); /* getAvailable + getStudent */
		verify(studentDao, times(1)).getStudentCourses(DOCKET_VALID);

		studentService.getAvailableInscriptionCourses(DOCKET_VALID, courseFilter);
		verify(courseService, times(1)).getByFilter(courseFilter); /* getAvailable */
		verify(courseService, times(3)).getByFilter(null); /* getStudent + 2 prev calls*/
		verify(studentDao, times(2)).getStudentCourses(DOCKET_VALID);

		/* Checks when input is valid */
		studentService.getAvailableInscriptionCourses(DOCKET_VALID_LIMIT, null);
		verify(courseService, times(5)).getByFilter(null); /* getAvailable + getStudent + 3 prev call*/
		verify(studentDao, times(1)).getStudentCourses(DOCKET_VALID_LIMIT);

		studentService.getAvailableInscriptionCourses(DOCKET_VALID_LIMIT, courseFilter);
		verify(courseService, times(2)).getByFilter(courseFilter); /* getAvailable + prev call*/
		verify(courseService, times(6)).getByFilter(null); /* getStudent + 5 prev calls */
		verify(studentDao, times(2)).getStudentCourses(DOCKET_VALID_LIMIT);


		/*************** invalid input *************/

		/* Checks when input is invalid */
		studentService.getAvailableInscriptionCourses(DOCKET_INVALID_LIMIT, null);
		verify(courseService, times(6)).getByFilter(null); /* getAvailable + getStudent + 6 prev call*/
		verify(studentDao, times(0)).getStudentCourses(DOCKET_INVALID_LIMIT);

		studentService.getAvailableInscriptionCourses(DOCKET_INVALID_LIMIT, courseFilter);
		verify(courseService, times(2)).getByFilter(courseFilter); /* getAvailable + prev call*/
		verify(courseService, times(6)).getByFilter(null); /* getStudent + 5 prev calls */
		verify(studentDao, times(0)).getStudentCourses(DOCKET_INVALID_LIMIT);

		/* Checks when input is invalid */
		studentService.getAvailableInscriptionCourses(DOCKET_INVALID, null);
		verify(courseService, times(6)).getByFilter(null); /* getStudent + 5 prev calls */
		verify(studentDao, times(0)).getStudentCourses(DOCKET_INVALID);

		studentService.getAvailableInscriptionCourses(DOCKET_INVALID, courseFilter);
		verify(courseService, times(2)).getByFilter(courseFilter); /* getAvailable + prev call*/
		verify(courseService, times(6)).getByFilter(null); /* getStudent + 5 prev calls */
		verify(studentDao, times(0)).getStudentCourses(DOCKET_INVALID);
	}

	@Test
	public void testGetApprovedCourses() {
		/* Checks when input is valid */
		studentService.getApprovedCourses(DOCKET_VALID);
		verify(studentDao, times(1)).getApprovedCourses(DOCKET_VALID);

		studentService.getApprovedCourses(DOCKET_VALID_LIMIT);
		verify(studentDao, times(1)).getApprovedCourses(DOCKET_VALID_LIMIT);

		/* Checks when input is invalid */
		studentService.getApprovedCourses(DOCKET_INVALID_LIMIT);
		verify(studentDao, times(0)).getApprovedCourses(DOCKET_INVALID_LIMIT);

		studentService.getApprovedCourses(DOCKET_INVALID);
		verify(studentDao, times(0)).getApprovedCourses(DOCKET_INVALID);
	}

	// +++xchange: method was bad implemented - doing operations daos should do. Rewrite.
//	@Test
//	public void testUpdate() {
//        Student student = new Student.Builder(DOCKET_VALID, DNI_VALID).build();
//
//        /* valid input with address*/
//        when(studentDao.getDniByDocket(DOCKET_VALID)).then((invocation) -> DNI_VALID);
//        when(studentDao.hasAddress(DNI_VALID)).then((invocation) -> true);
//        studentService.update(DOCKET_VALID, student);
//        verify(studentDao, times(1)).getDniByDocket(DOCKET_VALID);
//        verify(studentDao, times(1)).updateAddress(DNI_VALID, student);
//        verify(studentDao, times(0)).createAddress(DNI_VALID, student);
//
//        /* valid input without address*/
//        when(studentDao.getDniByDocket(DOCKET_VALID)).then((invocation) -> DNI_VALID);
//        when(studentDao.hasAddress(DNI_VALID)).then((invocation) -> false);
//        studentService.update(DOCKET_VALID, student);
//        verify(studentDao, times(2)).getDniByDocket(DOCKET_VALID);
//        verify(studentDao, times(1)).updateAddress(DNI_VALID, student);
//        verify(studentDao, times(1)).createAddress(DNI_VALID, student);
//    }
}

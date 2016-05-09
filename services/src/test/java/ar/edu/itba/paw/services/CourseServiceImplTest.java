package ar.edu.itba.paw.services;


import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.StudentFilter;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theory;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Null;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Documentation
 *
 * https://www.javacodegeeks.com/2013/12/parameterized-junit-tests-with-junitparams.html
 * https://github.com/junit-team/junit4/wiki/Parameterized-tests
 */


@RunWith(Parameterized.class)
public class CourseServiceImplTest {

    private static final int DOCKET_VALID = 7357;
    private static final int DOCKET_VALID_LIMIT = 1;
    private static final int DOCKET_INVALID_LIMIT = 0;
    private static final int DOCKET_INVALID = -7357;

    private static final int DNI_VALID = 7357;
    private static final int DNI_VALID_LIMIT = 1;
    private static final int DNI_INVALID_LIMIT = 0;
    private static final int DNI_INVALID = -7357;

    private static final int COURSE_1_ID_VALID = 10;
    private static final int COURSE_1_ID_VALID_LIMIT = 1;
    private static final int COURSE_1_ID_INVALID_LIMIT = 0;
    private static final int COURSE_1_ID_INVALID = -1;

    private static final int COURSE_2_ID_VALID = 7357;
    private static final int COURSE_2_ID_VALID_LIMIT = 1;
    private static final int COURSE_2_ID_INVALID_LIMIT = 0;
    private static final int COURSE_2_ID_INVALID = -7357;

    @Mock
    private CourseDao courseDao;

    @Mock
    private StudentService studentService;


    private CourseServiceImpl courseService;

    @Parameters
    public static Collection<Object[]> data() {

        final Student student1 = new Student.Builder(DOCKET_VALID, DNI_VALID).build();
        final Student student2 = new Student.Builder(DOCKET_VALID_LIMIT, DNI_VALID_LIMIT).build();

        /* Note that getStudentCourses calls getByFilter with null value too */
        Answer<Course> courseAnswer = (invocation) -> {
            final List<Student> students = new LinkedList<>();
            students.add(student1);
            students.add(student2);

            Object[] objects = invocation.getArguments();
            int id = (int) objects[0];
            final Course course = new Course.Builder(id).build();
            course.setStudents(students);

            return course;
        };


        final StudentFilter studentFilter = new StudentFilter.StudentFilterBuilder().docket(DOCKET_VALID).build();
        Answer<List<Student>> studentServiceGetByFilter1 = (invocation) -> {
            final List<Student> students = new LinkedList<>();
            students.add(student1);
            students.add(student2);

            return students;
        };

        Answer<List<Student>> studentServiceGetByFilter2 = (invocation) -> {
            final List<Student> students = new LinkedList<>();
            students.add(student1);

            return students;
        };


        List<Student> expectedStudents1 = new LinkedList<>();
        expectedStudents1.add(student1);
        expectedStudents1.add(student2);

        List<Student> expectedStudents2 = new LinkedList<>();
        expectedStudents2.add(student1);

        return Arrays.asList(new Object[][] {
                { COURSE_1_ID_VALID, null, courseAnswer, studentServiceGetByFilter1, expectedStudents1},
                { COURSE_1_ID_VALID, studentFilter, courseAnswer, studentServiceGetByFilter2, expectedStudents2},
                { COURSE_1_ID_INVALID, studentFilter, null, null, null },
                { COURSE_1_ID_INVALID, null, null, null, null }
        });
    }

    @Parameter // first data value (0) is default
    public /* NOT private */ int courseId;

    @Parameter(value = 1)
    public /* NOT private */ StudentFilter studentFilter;

    @Parameter(value = 2)
    public /* NOT private */ Answer<Course> courseAnswer;

    @Parameter(value = 3)
    public /* NOT private */ Answer<List<Student>> studentServiceGetByFilter;

    @Parameter(value = 4)
    public /* NOT private */ List<Student> expectedStudents;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        courseService = new CourseServiceImpl();
        courseService.setCourseDao(courseDao);
        courseService.setStudentService(studentService);
    }

    @Test
    public void getById() {
        courseService.getById(COURSE_1_ID_VALID);
        verify(courseDao, times(1)).getById(COURSE_1_ID_VALID);

        courseService.getById(COURSE_1_ID_VALID_LIMIT);
        verify(courseDao, times(1)).getById(COURSE_1_ID_VALID);

        courseService.getById(COURSE_1_ID_INVALID_LIMIT);
        verify(courseDao, times(0)).getById(COURSE_1_ID_INVALID_LIMIT);

        courseService.getById(COURSE_1_ID_INVALID);
        verify(courseDao, times(0)).getById(COURSE_1_ID_INVALID);
    }

    @Test
    public void testGetAllCourses() {
        courseService.getAllCourses();
        verify(courseDao, times(1)).getByFilter(null);
    }

    @Test
    public void testGetCourseStudents() {

        if(courseAnswer != null) {
            when(courseDao.getCourseStudents(courseId)).then(courseAnswer);
        }
        if(studentServiceGetByFilter != null) {
            when(studentService.getByFilter(studentFilter)).then(studentServiceGetByFilter);
        }

        List<Student> students;
        students = courseService.getCourseStudents(courseId, studentFilter);
        assertThat(students, is(expectedStudents));
    }

    @Test
    public void testGetStudentsThatPassedCourse() {
        if(courseAnswer != null) {
            when(courseDao.getStudentsThatPassedCourse(courseId)).then(courseAnswer);
        }
        Course course;

        course = courseService.getStudentsThatPassedCourse(courseId, studentFilter);
        if(course != null) {
            assertThat(course.getStudents(), is(expectedStudents));
        }
    }

    /* +++xtodo TODO: write remaining tests */

}

package ar.edu.itba.paw.services;


import ar.edu.itba.paw.interfaces.CourseDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CourseServiceImplTest {
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

    private CourseServiceImpl courseService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        courseService = new CourseServiceImpl();
        courseService.setCourseDao(courseDao);
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

    /* +++xtodo TODO: write remaining tests */
}

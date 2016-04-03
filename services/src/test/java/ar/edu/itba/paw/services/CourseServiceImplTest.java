package ar.edu.itba.paw.services;


import ar.edu.itba.paw.interfaces.CourseDao;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CourseServiceImplTest {
    private static final int ID_VALID = 10;
    private static final int ID_VALID_LIMIT = 1;
    private static final int ID_INVALID_LIMIT = 0;
    private static final int ID_INVALID = -1;

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
        courseService.getById(ID_VALID);
        verify(courseDao, times(1)).getById(ID_VALID);

        courseService.getById(ID_VALID_LIMIT);
        verify(courseDao, times(1)).getById(ID_VALID);

        courseService.getById(ID_INVALID_LIMIT);
        verify(courseDao, times(0)).getById(ID_INVALID_LIMIT);

        courseService.getById(ID_INVALID);
        verify(courseDao, times(0)).getById(ID_INVALID);
    }
}

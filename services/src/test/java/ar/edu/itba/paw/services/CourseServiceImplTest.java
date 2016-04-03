package ar.edu.itba.paw.services;


import ar.edu.itba.paw.interfaces.CourseDao;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CourseServiceImplTest {
    @Mock
    private CourseDao courseDao;

    private CourseServiceImpl courseService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        courseService = new CourseServiceImpl();
        courseService.setCourseDao(courseDao);
    }
}

package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.shared.Result;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class CourseJdbcDaoTest {
    private static final String COURSE_TABLE = "course";

    /* Columns */
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CREDITS = "credits";

    /* Example Args */
    private static final int ID_1 = 1;
    private static final String NAME_1 = "Metodos";
    private static final int CREDITS_1 = 3;

    private static final int ID_2 = 2;
    private static final String NAME_2 = "PI";
    private static final int CREDITS_2 = 9;

    private static final int ID_3 = 3;
    private static final String NAME_3 = "Logica";
    private static final int CREDITS_3 = 6;

    private static final int ID_4 = 4;
    private static final String NAME_4 = "Sistemas de Representacion";
    private static final int CREDITS_4 = 3;

    private static final int ID_5 = 5;
    private static final String NAME_5 = "Fisica II";
    private static final int CREDITS_5 = 6;

    private static final int ID_6 = 6;
    private static final String NAME_6 = "Algebra";
    private static final int CREDITS_6 = 9;


    private static final int ID_1_INVALID = -1;
    private static final int CREDITS_1_INVALID = -3;


    @Autowired
    private DataSource dataSource;

    @Autowired
    private CourseJdbcDao courseJdbcDao;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert courseInsert;

    /* Keys */
    private int id1;
    private int id2;
    private int id3;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        courseInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(COURSE_TABLE);

        /* Clean DB */
        JdbcTestUtils.deleteFromTables(jdbcTemplate, COURSE_TABLE);

        /* Insertion */
        final Map<String, Object> courseArgs1 = new HashMap<>();
        final Map<String, Object> courseArgs2 = new HashMap<>();
        final Map<String, Object> courseArgs3 = new HashMap<>();

        courseArgs1.put(COLUMN_ID, ID_1);
        courseArgs1.put(COLUMN_NAME, NAME_1);
        courseArgs1.put(COLUMN_CREDITS, CREDITS_1);
        courseArgs2.put(COLUMN_ID, ID_2);
        courseArgs2.put(COLUMN_NAME, NAME_2);
        courseArgs2.put(COLUMN_CREDITS, CREDITS_2);
        courseArgs3.put(COLUMN_ID, ID_3);
        courseArgs3.put(COLUMN_NAME, NAME_3);
        courseArgs3.put(COLUMN_CREDITS, CREDITS_3);

        courseInsert.execute(courseArgs1);
        courseInsert.execute(courseArgs2);
        courseInsert.execute(courseArgs3);
    }

    @Test
    public void getById() {
        Course course = courseJdbcDao.getById(ID_1);

        assertNotNull(course);
        assertEquals(ID_1, course.getId());
        assertEquals(NAME_1, course.getName());
        assertEquals(CREDITS_1, course.getCredits());

        course = courseJdbcDao.getById(ID_2);

        assertNotNull(course);
        assertEquals(ID_2, course.getId());
        assertEquals(NAME_2, course.getName());
        assertEquals(CREDITS_2, course.getCredits());

        course = courseJdbcDao.getById(ID_3);

        assertNotNull(course);
        assertEquals(ID_3, course.getId());
        assertEquals(NAME_3, course.getName());
        assertEquals(CREDITS_3, course.getCredits());
    }


    @Test
    public void createCourse() {

        /* OK insertion */
        Result result = courseJdbcDao.create(new Course.Builder(ID_4).name(NAME_4).credits(CREDITS_4).build());
        assertEquals(Result.OK, result);
        /******************/

		/* Existing  ID*/
        result = courseJdbcDao.create(new Course.Builder(ID_4).name(NAME_4).credits(CREDITS_4).build());
        assertEquals(Result.COURSE_EXISTS_ID, result);
        /******************/

		/* Invalid course id */
        result = courseJdbcDao.create(new Course.Builder(ID_1_INVALID).name(NAME_1).credits(CREDITS_1).build());
        assertEquals(Result.INVALID_INPUT_PARAMETERS, result);
        /******************/

		/* Invalid credits */
        result = courseJdbcDao.create(new Course.Builder(ID_5).name(NAME_1).credits(CREDITS_1_INVALID).build());
        assertEquals(Result.INVALID_INPUT_PARAMETERS, result);
        /******************/

		/* Check if saved correctly */
        Course course1 = new Course.Builder(ID_2).name(NAME_2).credits(CREDITS_2).build();
        Course course2 = courseJdbcDao.getById(ID_2);
        assertEquals(course1, course2);

    }

}
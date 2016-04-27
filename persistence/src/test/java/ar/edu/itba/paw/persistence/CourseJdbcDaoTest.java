package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.shared.Result;
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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class CourseJdbcDaoTest {
    private static final String COURSE_TABLE = "course";
    private static final String STUDENT_TABLE = "student";
    private static final String USER_TABLE = "users";
    private static final String GRADE_TABLE = "grade";
    private static final String INSCRIPTION_TABLE = "inscription";

    /* Columns */
    private static final String COURSE__ID_COLUMN = "id";
    private static final String COURSE__NAME_COLUMN = "name";
    private static final String COURSE__CREDITS_COLUMN = "credits";

    private static final String STUDENT__DOCKET_COLUMN = "docket";
    private static final String STUDENT__DNI_COLUMN = "dni";

    private static final String USER__DNI_COLUMN = "dni";
    private static final String USER__FIRST_NAME_COLUMN = "first_name";
    private static final String USER__LAST_NAME_COLUMN = "last_name";
    private static final String USER__GENRE_COLUMN = "genre";
    private static final String USER__BIRTHDAY_COLUMN = "birthday";
    private static final String USER__EMAIL_COLUMN = "email";

    private static final String GRADE__DOCKET_COLUMN = "docket";
    private static final String GRADE__COURSE_ID_COLUMN = "course_id";
    private static final String GRADE__GRADE_COLUMN = "grade";
    private static final String GRADE__MODIFIED_COLUMN = "modified";

    private static final String INSCRIPTION__COURSE_ID_COLUMN = "course_id";
    private static final String INSCRIPTION__DOCKET_COLUMN = "docket";

    /* Example Args */
    private static final int COURSE_ID_1 = 1;
    private static final String COURSE_NAME_1 = "Metodos";
    private static final int COURSE_CREDITS_1 = 3;
    private static final int COURSE_ID_2 = 2;
    private static final String COURSE_NAME_2 = "PI";
    private static final int COURSE_CREDITS_2 = 9;
    private static final int COURSE_ID_3 = 3;
    private static final String COURSE__NAME_3 = "Logica";
    private static final int COURSE_CREDITS_3 = 6;
    private static final int COURSE_ID_4 = 4;
    private static final String COURSE__NAME_4 = "EDA";
    private static final int COURSE_CREDITS_4 = 6;

    private static final int DOCKET_1 = 1;
    private static final int DOCKET_2 = 2;

    private static final int DNI_1 = 12345678;
    private static final String FIRST_NAME_1 = "MaTías NIColas";
    private static final String FIRST_NAME_1_EXPECTED = "Matías Nicolas";
    private static final String LAST_NAME_1 = "Comercio vazquez";
    private static final String LAST_NAME_1_EXPECTED = "Comercio Vazquez";
    private static final String GENRE_1 = "M";
    private static final String GENRE_1_EXPECTED = "Male";
    private static final LocalDate BIRTHDAY_1 = LocalDate.parse("1994-08-17");
    private static final String EMAIL_1 = "mcomercio@bait.edu.ar";
    private int docket1; /* Auto-generated field */

    private static final int DNI_2 = 87654321;
    private static final String FIRST_NAME_2 = "BreNda LiHuéN ";
    private static final String FIRST_NAME_2_EXPECTED = "Brenda Lihuén";
    private static final String LAST_NAME_2 = "MaYan";
    private static final String LAST_NAME_2_EXPECTED = "Mayan";
    private static final String EMAIL_2 = "blihuen@bait.edu.ar";
    private int docket2; /* Auto-generated field */

    private static final BigDecimal GRADE_APPROVED = BigDecimal.valueOf(9);



    @Autowired
    private DataSource dataSource;

    @Autowired
    private CourseJdbcDao courseJdbcDao;

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert courseInsert;
    private SimpleJdbcInsert userInsert;
    private SimpleJdbcInsert studentInsert;
    private SimpleJdbcInsert inscriptionInsert;
    private SimpleJdbcInsert gradeInsert;

    /* Keys */
    private int id1;
    private int id2;
    private int id3;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        courseInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(COURSE_TABLE);
        userInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(USER_TABLE);
        studentInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(STUDENT_TABLE).usingGeneratedKeyColumns(STUDENT__DOCKET_COLUMN);
        inscriptionInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(INSCRIPTION_TABLE).usingColumns(INSCRIPTION__COURSE_ID_COLUMN, INSCRIPTION__DOCKET_COLUMN);
        gradeInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(GRADE_TABLE).usingColumns(GRADE__DOCKET_COLUMN, GRADE__COURSE_ID_COLUMN, GRADE__GRADE_COLUMN);;

        /* Clean DB */

        JdbcTestUtils.deleteFromTables(jdbcTemplate, GRADE_TABLE);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, INSCRIPTION_TABLE);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, STUDENT_TABLE);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, USER_TABLE);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, COURSE_TABLE);

        /* Insertion */
        final Map<String, Object> courseArgs1 = new HashMap<>();
        final Map<String, Object> courseArgs2 = new HashMap<>();
        final Map<String, Object> courseArgs3 = new HashMap<>();

        courseArgs1.put(COURSE__ID_COLUMN, COURSE_ID_1);
        courseArgs1.put(COURSE__NAME_COLUMN, COURSE_NAME_1);
        courseArgs1.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_1);
        courseArgs2.put(COURSE__ID_COLUMN, COURSE_ID_2);
        courseArgs2.put(COURSE__NAME_COLUMN, COURSE_NAME_2);
        courseArgs2.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_2);
        courseArgs3.put(COURSE__ID_COLUMN, COURSE_ID_3);
        courseArgs3.put(COURSE__NAME_COLUMN, COURSE__NAME_3);
        courseArgs3.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_3);

        courseInsert.execute(courseArgs1);
        courseInsert.execute(courseArgs2);
        courseInsert.execute(courseArgs3);
    }

    @Test
    public void deleteCourse() {
        Result result;
        Course course;

        // Delete non existant course
        result = courseJdbcDao.deleteCourse(COURSE_ID_4);
        assertEquals(Result.ERROR_UNKNOWN, result);

        /**
         * Delete existant course (with no inscriptions or grades) and then check that is deleted
         */
        result = courseJdbcDao.deleteCourse(COURSE_ID_1);
        assertEquals(Result.OK, result);
        course = courseJdbcDao.getById(COURSE_ID_1);
        assertNull(course);

        /**
         * Delete existant course (with inscriptions) and then check that it was not deleted
         */
        final Map<String, Object> userArgs1 = new HashMap<>();
        final Map<String, Object> studentArgs1 = new HashMap<>();
        final Map<String, Object> inscriptionArgs = new HashMap<>();

        userArgs1.put(USER__DNI_COLUMN, DNI_1);
        userArgs1.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
        userArgs1.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
        userArgs1.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
        userInsert.execute(userArgs1);

        studentArgs1.put(STUDENT__DNI_COLUMN, DNI_1);
        docket1 = studentInsert.executeAndReturnKey(studentArgs1).intValue();

        inscriptionArgs.put(INSCRIPTION__COURSE_ID_COLUMN, COURSE_ID_2);
        inscriptionArgs.put(INSCRIPTION__DOCKET_COLUMN, docket1);
        inscriptionInsert.execute(inscriptionArgs);

        result = courseJdbcDao.deleteCourse(COURSE_ID_2);
        assertEquals(Result.COURSE_EXISTS_INSCRIPTION, result);

        course = courseJdbcDao.getById(COURSE_ID_2);
        assertNotNull(course);

        /**
         * Delete existant course (with grades) and then check that it was not deleted
         */
        final Map<String, Object> userArgs2 = new HashMap<>();
        final Map<String, Object> studentArgs2 = new HashMap<>();
        final Map<String, Object> gradeArgs = new HashMap<>();

        userArgs2.put(USER__DNI_COLUMN, DNI_2);
        userArgs2.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_2.toLowerCase());
        userArgs2.put(USER__LAST_NAME_COLUMN, LAST_NAME_2.toLowerCase());
        userArgs2.put(USER__EMAIL_COLUMN, EMAIL_2.toLowerCase());
        userInsert.execute(userArgs2);

        studentArgs2.put(STUDENT__DNI_COLUMN, DNI_2);
        docket2 = studentInsert.executeAndReturnKey(studentArgs2).intValue();

        gradeArgs.put(GRADE__DOCKET_COLUMN, docket2);
        gradeArgs.put(GRADE__COURSE_ID_COLUMN, COURSE_ID_3);
        gradeArgs.put(GRADE__GRADE_COLUMN, GRADE_APPROVED);
        gradeInsert.execute(gradeArgs);

        result = courseJdbcDao.deleteCourse(COURSE_ID_3);
        assertEquals(Result.COURSE_EXISTS_GRADE, result);

        course = courseJdbcDao.getById(COURSE_ID_3);
        assertNotNull(course);
    }

    @Test
    public void getById() {
        Course course = courseJdbcDao.getById(COURSE_ID_1);

        assertNotNull(course);
        assertEquals(COURSE_ID_1, course.getId());
        assertEquals(COURSE_NAME_1, course.getName());
        assertEquals(COURSE_CREDITS_1, course.getCredits());

        course = courseJdbcDao.getById(COURSE_ID_2);

        assertNotNull(course);
        assertEquals(COURSE_ID_2, course.getId());
        assertEquals(COURSE_NAME_2, course.getName());
        assertEquals(COURSE_CREDITS_2, course.getCredits());

        course = courseJdbcDao.getById(COURSE_ID_3);

        assertNotNull(course);
        assertEquals(COURSE_ID_3, course.getId());
        assertEquals(COURSE__NAME_3, course.getName());
        assertEquals(COURSE_CREDITS_3, course.getCredits());
    }
}
//package ar.edu.itba.paw.persistence;
//
//import ar.edu.itba.paw.models.Course;
//import ar.edu.itba.paw.models.users.Student;
//import ar.edu.itba.paw.shared.Result;
//import org.hamcrest.Matcher;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.jdbc.JdbcTestUtils;
//
//import javax.sql.DataSource;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//import static org.hamcrest.CoreMatchers.anyOf;
//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.*;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
//@Sql("classpath:schema.sql")
//public class CourseJdbcDaoTest {
//    private static final String COURSE_TABLE = "course";
//    private static final String STUDENT_TABLE = "student";
//    private static final String USER_TABLE = "users";
//    private static final String GRADE_TABLE = "grade";
//    private static final String INSCRIPTION_TABLE = "inscription";
//    private static final String CORRELATIVE_TABLE = "correlative";
//    private static final String ROLE_TABLE = "role";
//
//
//    /* Columns */
//    private static final String COURSE__ID_COLUMN = "id";
//    private static final String COURSE__NAME_COLUMN = "name";
//    private static final String COURSE__CREDITS_COLUMN = "credits";
//    private static final String COURSE__SEMESTER_COLUMN = "semester";
//
//    private static final String STUDENT__DOCKET_COLUMN = "docket";
//    private static final String STUDENT__DNI_COLUMN = "dni";
//
//    private static final String USER__DNI_COLUMN = "dni";
//    private static final String USER__FIRST_NAME_COLUMN = "first_name";
//    private static final String USER__LAST_NAME_COLUMN = "last_name";
//    private static final String USER__GENRE_COLUMN = "genre";
//    private static final String USER__BIRTHDAY_COLUMN = "birthday";
//    private static final String USER__EMAIL_COLUMN = "email";
//    private static final String USER__ROLE_COLUMN = "role";
//
//    private static final String GRADE__DOCKET_COLUMN = "docket";
//    private static final String GRADE__COURSE_ID_COLUMN = "course_id";
//    private static final String GRADE__GRADE_COLUMN = "grade";
//    private static final String GRADE__MODIFIED_COLUMN = "modified";
//
//    private static final String INSCRIPTION__COURSE_ID_COLUMN = "course_id";
//    private static final String INSCRIPTION__DOCKET_COLUMN = "docket";
//
//    private static final String CORRELATIVE__COURSE_ID_COLUMN = "course_id";
//    private static final String CORRELATIVE__CORRELATIVE_ID_COLUMN = "correlative_id";
//
//    private static final String ROLE__ROLE_COLUMN = "role";
//
//
//    /* Example Args */
//    private static final int COURSE_ID_1 = 1;
//    private static final String COURSE_NAME_1 = "Metodos";
//    private static final int COURSE_CREDITS_1 = 3;
//    private static final int COURSE_SEMESTER_1 = 1;
//    private static final int COURSE_ID_2 = 2;
//    private static final String COURSE_NAME_2 = "PI";
//    private static final int COURSE_CREDITS_2 = 9;
//    private static final int COURSE_SEMESTER_2 = 2;
//    private static final int COURSE_ID_3 = 3;
//    private static final String COURSE__NAME_3 = "Logica";
//    private static final int COURSE_CREDITS_3 = 6;
//    private static final int COURSE_SEMESTER_3 = 3;
//    private static final int COURSE_ID_4 = 4;
//    private static final String COURSE_NAME_4 = "EDA";
//    private static final int COURSE_CREDITS_4 = 6;
//    private static final int COURSE_SEMESTER_4 = 4;
//
//    private static final int DOCKET_1 = 1;
//    private static final int DOCKET_2 = 2;
//
//    private static final int DNI_1 = 12345678;
//    private static final String FIRST_NAME_1 = "MaTías NIColas";
//    private static final String FIRST_NAME_1_EXPECTED = "Matías Nicolas";
//    private static final String LAST_NAME_1 = "Comercio vazquez";
//    private static final String LAST_NAME_1_EXPECTED = "Comercio Vazquez";
//    private static final String GENRE_1 = "M";
//    private static final String GENRE_1_EXPECTED = "Male";
//    private static final LocalDate BIRTHDAY_1 = LocalDate.parse("1994-08-17");
//    private static final String EMAIL_1 = "mcomercio@bait.edu.ar";
//    private int docket1; /* Auto-generated field */
//
//    private static final int DNI_2 = 87654321;
//    private static final String FIRST_NAME_2 = "BreNda LiHuéN ";
//    private static final String FIRST_NAME_2_EXPECTED = "Brenda Lihuén";
//    private static final String LAST_NAME_2 = "MaYan";
//    private static final String LAST_NAME_2_EXPECTED = "Mayan";
//    private static final String EMAIL_2 = "blihuen@bait.edu.ar";
//    private int docket2; /* Auto-generated field */
//
//    private static final String ROLE_1 = "ADMIN";
//    private static final String ROLE_2 = "STUDENT";
//
//    private static final BigDecimal GRADE_APPROVED = BigDecimal.valueOf(9);
//    private static final BigDecimal GRADE_NOT_APPROVED = BigDecimal.valueOf(3);
//
//
//
//    /*private static final int COURSE_ID_4 = 4;
//    private static final String COURSE_NAME_4 = "Sistemas de Representacion";
//    private static final int COURSE_CREDITS_4 = 3;
//*/
//    private static final int COURSE_ID_5 = 5;
//    private static final String COURSE_NAME_5 = "Fisica II";
//    private static final int COURSE_CREDITS_5 = 6;
//
//    private static final int COURSE_ID_6 = 6;
//    private static final String COURSE_NAME_6 = "Algebra";
//    private static final int COURSE_CREDITS_6 = 9;
//
//    private static final int COURSE_ID_7 = 1010;
//    private static final String COURSE_NAME_7 = "Fisica III";
//    private static final int COURSE_CREDITS_7 = 6;
//    private static final int COURSE_SEMESTER_7 = 3;
//
//    private static final int COURSE_ID_8 = 1011;
//    private static final String COURSE_NAME_8 = "Fisica IV";
//    private static final int COURSE_CREDITS_8 = 6;
//    private static final int COURSE_SEMESTER_8 = 4;
//
//    private static final int COURSE_ID_1_INVALID = -1;
//    private static final int COURSE_CREDITS_1_INVALID = -3;
//
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Autowired
//    private CourseJdbcDao courseJdbcDao;
//
//    private JdbcTemplate jdbcTemplate;
//    private SimpleJdbcInsert courseInsert;
//    private SimpleJdbcInsert userInsert;
//    private SimpleJdbcInsert studentInsert;
//    private SimpleJdbcInsert inscriptionInsert;
//    private SimpleJdbcInsert gradeInsert;
//    private SimpleJdbcInsert correlativeInsert;
//    private SimpleJdbcInsert roleInsert;
//
//    /* Keys */
//    private int id1;
//    private int id2;
//    private int id3;
//
//    @Before
//    public void setUp() {
//        jdbcTemplate = new JdbcTemplate(dataSource);
//        courseInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(COURSE_TABLE);
//        userInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(USER_TABLE);
//        studentInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(STUDENT_TABLE).usingGeneratedKeyColumns(STUDENT__DOCKET_COLUMN);
//        inscriptionInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(INSCRIPTION_TABLE).usingColumns(INSCRIPTION__COURSE_ID_COLUMN, INSCRIPTION__DOCKET_COLUMN);
//        gradeInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(GRADE_TABLE).usingColumns(GRADE__DOCKET_COLUMN, GRADE__COURSE_ID_COLUMN, GRADE__GRADE_COLUMN);;
//        correlativeInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(CORRELATIVE_TABLE).usingColumns(CORRELATIVE__COURSE_ID_COLUMN, CORRELATIVE__CORRELATIVE_ID_COLUMN);
//        roleInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(ROLE_TABLE);
//
//        /* Clean DB */
//
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, CORRELATIVE_TABLE);
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, GRADE_TABLE);
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, INSCRIPTION_TABLE);
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, STUDENT_TABLE);
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, USER_TABLE);
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, COURSE_TABLE);
//        JdbcTestUtils.deleteFromTables(jdbcTemplate, ROLE_TABLE);
//
//
//        final Map<String, Object> roleArgs = new HashMap<>();
//
//        roleArgs.put(ROLE__ROLE_COLUMN, ROLE_1);
//        roleInsert.execute(roleArgs);
//        roleArgs.put(ROLE__ROLE_COLUMN, ROLE_2);
//        roleInsert.execute(roleArgs);
//
//        /* Insertion */
//        final Map<String, Object> courseArgs1 = new HashMap<>();
//        final Map<String, Object> courseArgs2 = new HashMap<>();
//        final Map<String, Object> courseArgs3 = new HashMap<>();
//
//        courseArgs1.put(COURSE__ID_COLUMN, COURSE_ID_1);
//        courseArgs1.put(COURSE__NAME_COLUMN, COURSE_NAME_1);
//        courseArgs1.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_1);
//        courseArgs1.put(COURSE__SEMESTER_COLUMN, COURSE_SEMESTER_1);
//        courseArgs2.put(COURSE__ID_COLUMN, COURSE_ID_2);
//        courseArgs2.put(COURSE__NAME_COLUMN, COURSE_NAME_2);
//        courseArgs2.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_2);
//        courseArgs2.put(COURSE__SEMESTER_COLUMN, COURSE_SEMESTER_2);
//        courseArgs3.put(COURSE__ID_COLUMN, COURSE_ID_3);
//        courseArgs3.put(COURSE__NAME_COLUMN, COURSE__NAME_3);
//        courseArgs3.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_3);
//        courseArgs3.put(COURSE__SEMESTER_COLUMN, COURSE_SEMESTER_3);
//
//        courseInsert.execute(courseArgs1);
//        courseInsert.execute(courseArgs2);
//        courseInsert.execute(courseArgs3);
//    }
//
//    @Test
//    public void deleteCourse() {
//        Result result;
//        Course course;
//
//        // Delete non existant course
//        result = courseJdbcDao.deleteCourse(COURSE_ID_4);
//        assertEquals(Result.ERROR_UNKNOWN, result);
//
//        /**
//         * Delete existant course (with no inscriptions or grades) and then check that is deleted
//         */
//        result = courseJdbcDao.deleteCourse(COURSE_ID_1);
//        assertEquals(Result.OK, result);
//        course = courseJdbcDao.getById(COURSE_ID_1);
//        assertNull(course);
//
//        /**
//         * Delete existant course (with inscriptions) and then check that it was not deleted
//         */
//        final Map<String, Object> userArgs1 = new HashMap<>();
//        final Map<String, Object> studentArgs1 = new HashMap<>();
//        final Map<String, Object> inscriptionArgs = new HashMap<>();
//
//        userArgs1.put(USER__DNI_COLUMN, DNI_1);
//        userArgs1.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
//        userArgs1.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
//        userArgs1.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
//        userArgs1.put(USER__ROLE_COLUMN, ROLE_1);
//        userInsert.execute(userArgs1);
//
//        studentArgs1.put(STUDENT__DNI_COLUMN, DNI_1);
//        docket1 = studentInsert.executeAndReturnKey(studentArgs1).intValue();
//
//        inscriptionArgs.put(INSCRIPTION__COURSE_ID_COLUMN, COURSE_ID_2);
//        inscriptionArgs.put(INSCRIPTION__DOCKET_COLUMN, docket1);
//        inscriptionInsert.execute(inscriptionArgs);
//
//        //TODO: deleteCourse not returning COURSE_EXISTS_INSCRIPTION in any moment.
//        result = courseJdbcDao.deleteCourse(COURSE_ID_2);
//        //assertEquals(Result.COURSE_EXISTS_INSCRIPTION, result); Note: This is now checked in the Service!!
//        assertEquals(Result.INVALID_INPUT_PARAMETERS, result);
//        /* INVALID_INPUT_PARAMETER is returned when a DataIntegrityViolation is thrown, which means a DB
//        restriction is being violated (In this case, the foreign key from inscription table(courseId) cannot be
//        null, and we are trying to be removed the course)
//        */
//
//        course = courseJdbcDao.getById(COURSE_ID_2);
//        assertNotNull(course);
//
//        /**
//         * Delete existant course (with grades) and then check that it was not deleted
//         */
//        final Map<String, Object> userArgs2 = new HashMap<>();
//        final Map<String, Object> studentArgs2 = new HashMap<>();
//        final Map<String, Object> gradeArgs = new HashMap<>();
//
//        userArgs2.put(USER__DNI_COLUMN, DNI_2);
//        userArgs2.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_2.toLowerCase());
//        userArgs2.put(USER__LAST_NAME_COLUMN, LAST_NAME_2.toLowerCase());
//        userArgs2.put(USER__EMAIL_COLUMN, EMAIL_2.toLowerCase());
//        userArgs2.put(USER__ROLE_COLUMN, ROLE_2);
//        userInsert.execute(userArgs2);
//
//        studentArgs2.put(STUDENT__DNI_COLUMN, DNI_2);
//        docket2 = studentInsert.executeAndReturnKey(studentArgs2).intValue();
//
//        gradeArgs.put(GRADE__DOCKET_COLUMN, docket2);
//        gradeArgs.put(GRADE__COURSE_ID_COLUMN, COURSE_ID_3);
//        gradeArgs.put(GRADE__GRADE_COLUMN, GRADE_APPROVED);
//        gradeInsert.execute(gradeArgs);
//
//        result = courseJdbcDao.deleteCourse(COURSE_ID_3);
//        assertEquals(Result.INVALID_INPUT_PARAMETERS, result);
//        //assertEquals(Result.COURSE_EXISTS_GRADE, result);
//
//        course = courseJdbcDao.getById(COURSE_ID_3);
//        assertNotNull(course);
//    }
//
//    @Test
//    public void getById() {
//        Course course = courseJdbcDao.getById(COURSE_ID_1);
//
//        assertNotNull(course);
//        assertEquals(COURSE_ID_1, course.getId());
//        assertEquals(COURSE_NAME_1, course.getName());
//        assertEquals(COURSE_CREDITS_1, course.getCredits());
//
//        course = courseJdbcDao.getById(COURSE_ID_2);
//
//        assertNotNull(course);
//        assertEquals(COURSE_ID_2, course.getId());
//        assertEquals(COURSE_NAME_2, course.getName());
//        assertEquals(COURSE_CREDITS_2, course.getCredits());
//
//        course = courseJdbcDao.getById(COURSE_ID_3);
//
//        assertNotNull(course);
//        assertEquals(COURSE_ID_3, course.getId());
//        assertEquals(COURSE__NAME_3, course.getName());
//        assertEquals(COURSE_CREDITS_3, course.getCredits());
//    }
//
//    @Test
//    public void getStudentsThatPassedCourse() {
//        final Map<String, Object> userArgs = new HashMap<>();
//        final Map<String, Object> studentArgs = new HashMap<>();
//        final Map<String, Object> gradeArgs = new HashMap<>();
//
//        userArgs.put(USER__DNI_COLUMN, DNI_1);
//        userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
//        userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
//        userArgs.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
//        userArgs.put(USER__ROLE_COLUMN, ROLE_1);
//        userInsert.execute(userArgs);
//
//        studentArgs.put(STUDENT__DNI_COLUMN, DNI_1);
//        docket1 = studentInsert.executeAndReturnKey(studentArgs).intValue();
//
//        Student expectedStudent = new Student.Builder(docket1, DNI_1)
//                .build();
//
//        gradeArgs.put(GRADE__DOCKET_COLUMN, docket1);
//        gradeArgs.put(GRADE__COURSE_ID_COLUMN, COURSE_ID_3);
//        gradeArgs.put(GRADE__GRADE_COLUMN, GRADE_APPROVED);
//        gradeInsert.execute(gradeArgs);
//
//        /**
//         * Get the grades of a non existent course
//         */
//        Course course = courseJdbcDao.getStudentsThatPassedCourse(COURSE_ID_4);
//        assertNull(course);
//
//        /**
//         * Get the grades of an existent course without students
//         */
//        course = courseJdbcDao.getStudentsThatPassedCourse(COURSE_ID_1);
//        assertNotNull(course);
//        assertTrue(course.getStudents().isEmpty());
//
//        /**
//         * Get the grades of an existent course with students that have passed
//         */
//        course = courseJdbcDao.getStudentsThatPassedCourse(COURSE_ID_3);
//        assertNotNull(course);
//        assertEquals(1, course.getStudents().size());
//        assertEquals(1, course.getStudents().size());
//
//        Student student = course.getStudents().get(0);
//        assertEquals(expectedStudent.getDocket(), student.getDocket());
//
//        /**
//         * Get the grades of an existent course with students that have passed and students that haven't passed
//         */
//        userArgs.put(USER__DNI_COLUMN, DNI_2);
//        userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_2.toLowerCase());
//        userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_2.toLowerCase());
//        userArgs.put(USER__EMAIL_COLUMN, EMAIL_2.toLowerCase());
//        userArgs.put(USER__ROLE_COLUMN, ROLE_2);
//        userInsert.execute(userArgs);
//
//        studentArgs.put(STUDENT__DNI_COLUMN, DNI_2);
//        docket2 = studentInsert.executeAndReturnKey(studentArgs).intValue();
//
//        expectedStudent = new Student.Builder(docket1, DNI_1)
//                .build();
//
//        gradeArgs.put(GRADE__DOCKET_COLUMN, docket1);
//        gradeArgs.put(GRADE__COURSE_ID_COLUMN, COURSE_ID_3);
//        gradeArgs.put(GRADE__GRADE_COLUMN, GRADE_NOT_APPROVED);
//        gradeInsert.execute(gradeArgs);
//
//        course = courseJdbcDao.getStudentsThatPassedCourse(COURSE_ID_3);
//        assertEquals(1, course.getStudents().size());
//        assertEquals(1, course.getStudents().size());
//
//        student = course.getStudents().get(0);
//        assertEquals(expectedStudent.getDocket(), student.getDocket());
//    }
//
//
//    @Test
//    public void createCourse() {
//
//        /* OK insertion */
//        Result result = courseJdbcDao.create(new Course.Builder(COURSE_ID_4).name(COURSE_NAME_4).
//                credits(COURSE_CREDITS_4).semester(COURSE_SEMESTER_4).build());
//        assertEquals(Result.OK, result);
//        /******************/
//
//		/* Existing  ID*/
//        result = courseJdbcDao.create(new Course.Builder(COURSE_ID_4).name(COURSE_NAME_4).credits(COURSE_CREDITS_4)
//                .build());
//        assertEquals(Result.COURSE_EXISTS_ID, result);
//        /******************/
//
//		/* Invalid course id */
//        result = courseJdbcDao.create(new Course.Builder(COURSE_ID_1_INVALID).name(COURSE_NAME_4).credits(COURSE_CREDITS_4).build());
//        assertEquals(Result.INVALID_INPUT_PARAMETERS, result);
//        /******************/
//
//		/* Invalid credits */
//        result = courseJdbcDao.create(new Course.Builder(COURSE_ID_5).name(COURSE_NAME_4).credits(COURSE_CREDITS_1_INVALID).build());
//        assertEquals(Result.INVALID_INPUT_PARAMETERS, result);
//        /******************/
//
//		/* Check if saved correctly */
//        Course course1 = new Course.Builder(COURSE_ID_4).name(COURSE_NAME_4).credits(COURSE_CREDITS_4).build();
//        Course course2 = courseJdbcDao.getById(COURSE_ID_4);
//        assertEquals(course1, course2);
//
//    }
//
//    @Test
//    public void update(){
//        /* OK update */
//        HashMap<String, Object> map = new HashMap<>();
//        map.put(COURSE__ID_COLUMN, COURSE_ID_7);
//        map.put(COURSE__NAME_COLUMN, COURSE_NAME_7);
//        map.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_7);
//        map.put(COURSE__SEMESTER_COLUMN, COURSE_SEMESTER_7);
//        courseInsert.execute(map);
//
//        Course course = new Course.Builder(COURSE_ID_8).name(COURSE_NAME_8).credits(COURSE_CREDITS_8).semester(COURSE_SEMESTER_8).build();
//        Result result = courseJdbcDao.update(COURSE_ID_7, course);
//        assertEquals(result, Result.OK);
//
//        Course newCourse = courseJdbcDao.getById(COURSE_ID_8);
//        assertEquals(course, newCourse);
//
//        /* Existing id */
//        map = new HashMap<>();
//        map.put(COURSE__ID_COLUMN, COURSE_ID_7);
//        map.put(COURSE__NAME_COLUMN, COURSE_NAME_7);
//        map.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_7);
//        map.put(COURSE__SEMESTER_COLUMN, COURSE_SEMESTER_7);
//        courseInsert.execute(map);
//
//        course = new Course.Builder(COURSE_ID_7).name(COURSE_NAME_7).credits(COURSE_CREDITS_7).semester(COURSE_SEMESTER_7).build();
//
//        result = courseJdbcDao.update(COURSE_ID_8, course);
//        assertEquals(result, Result.COURSE_EXISTS_ID);
//
//    }
//
//    @Test
//    public void checkCorrelativityLoop(){
//
//        //Insert courses;
//        HashMap<String, Object> map = new HashMap<>();
//        map.put(COURSE__ID_COLUMN, COURSE_ID_7);
//        map.put(COURSE__NAME_COLUMN, COURSE_NAME_7);
//        map.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_7);
//        map.put(COURSE__SEMESTER_COLUMN, COURSE_SEMESTER_7);
//        courseInsert.execute(map);
//
//        map.put(COURSE__ID_COLUMN, COURSE_ID_8);
//        map.put(COURSE__NAME_COLUMN, COURSE_NAME_8);
//        map.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_8);
//        map.put(COURSE__SEMESTER_COLUMN, COURSE_SEMESTER_8);
//        courseInsert.execute(map);
//
//
//
//        //Add correlativity line
//        map = new HashMap<>();
//        map.put(CORRELATIVE__COURSE_ID_COLUMN, COURSE_ID_8);
//        map.put(CORRELATIVE__CORRELATIVE_ID_COLUMN, COURSE_ID_7);
//        correlativeInsert.execute(map);
//
//        Boolean result = courseJdbcDao.checkCorrelativityLoop(COURSE_ID_7, COURSE_ID_1);
//        assertFalse(result);
//
//        map.put(CORRELATIVE__COURSE_ID_COLUMN, COURSE_ID_7);
//        map.put(CORRELATIVE__CORRELATIVE_ID_COLUMN, COURSE_ID_1);
//        correlativeInsert.execute(map);
//
//        result = courseJdbcDao.checkCorrelativityLoop(COURSE_ID_1, COURSE_ID_7);
//        assertTrue(result);
//
//        result = courseJdbcDao.checkCorrelativityLoop(COURSE_ID_7, COURSE_ID_8);
//        assertTrue(result);
//
//        result = courseJdbcDao.checkCorrelativityLoop(COURSE_ID_1, COURSE_ID_8);
//        assertTrue(result);
//    }
//
//    @Test
//    public void addCorrelativity(){
//        HashMap<String, Object> map = new HashMap<>();
//        map.put(COURSE__ID_COLUMN, COURSE_ID_7);
//        map.put(COURSE__NAME_COLUMN, COURSE_NAME_7);
//        map.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_7);
//        map.put(COURSE__SEMESTER_COLUMN, COURSE_SEMESTER_7);
//        courseInsert.execute(map);
//
//        map.put(COURSE__ID_COLUMN, COURSE_ID_8);
//        map.put(COURSE__NAME_COLUMN, COURSE_NAME_8);
//        map.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_8);
//        map.put(COURSE__SEMESTER_COLUMN, COURSE_SEMESTER_8);
//        courseInsert.execute(map);
//
//        Result result = courseJdbcDao.addCorrelativity(COURSE_ID_7, COURSE_ID_8);
//        assertEquals(result, Result.OK);
//
//        result = courseJdbcDao.addCorrelativity(COURSE_ID_7, COURSE_ID_8);
//        assertEquals(result, Result.CORRELATIVE_CORRELATIVITY_EXISTS);
//
//    }
//
//    @Test
//    public void courseExists(){
//        HashMap<String, Object> map = new HashMap<>();
//        map.put(COURSE__ID_COLUMN, COURSE_ID_7);
//        map.put(COURSE__NAME_COLUMN, COURSE_NAME_7);
//        map.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_7);
//        map.put(COURSE__SEMESTER_COLUMN, COURSE_SEMESTER_7);
//        courseInsert.execute(map);
//
//        Boolean result = courseJdbcDao.courseExists(COURSE_ID_1);
//        assertTrue(result);
//
//        result = courseJdbcDao.courseExists(COURSE_ID_7);
//        assertTrue(result);
//
//        result = courseJdbcDao.courseExists(COURSE_ID_8);
//        assertFalse(result);
//
//    }
//
//    @Test
//    public void getTotalSemesters(){
//        int max = Math.max(Math.max(COURSE_SEMESTER_1, COURSE_SEMESTER_2), COURSE_SEMESTER_3);
//        int semesters = courseJdbcDao.getTotalSemesters();
//        assertEquals(semesters, max);
//    }
//
//    @Test
//    public void getCorrelativesIds(){
//        final Map<String, Object> correlativeArgs = new HashMap<>();
//        final Map<String, Object> correlativeArgs2 = new HashMap<>();
//
//        correlativeArgs.put(CORRELATIVE__COURSE_ID_COLUMN, COURSE_ID_3);
//        correlativeArgs.put(CORRELATIVE__CORRELATIVE_ID_COLUMN, COURSE_ID_1);
//        correlativeInsert.execute(correlativeArgs);
//
//        correlativeArgs2.put(CORRELATIVE__COURSE_ID_COLUMN, COURSE_ID_3);
//        correlativeArgs2.put(CORRELATIVE__CORRELATIVE_ID_COLUMN, COURSE_ID_2);
//        correlativeInsert.execute(correlativeArgs2);
//
//        List<Integer> list = courseJdbcDao.getCorrelativesIds(COURSE_ID_3);
//
//        assertEquals(list.size(), 2);
//        assertTrue( (list.get(0).equals(COURSE_ID_1) || list.get(0).equals(COURSE_ID_2) ));
//        assertTrue( (list.get(1).equals(COURSE_ID_1) || list.get(1).equals(COURSE_ID_2) ));
//    }
//
//
//    @Test
//    public void getUpperCorrelatives(){
//        final Map<String, Object> correlativeArgs = new HashMap<>();
//        final Map<String, Object> correlativeArgs2 = new HashMap<>();
//
//        correlativeArgs.put(CORRELATIVE__COURSE_ID_COLUMN, COURSE_ID_3);
//        correlativeArgs.put(CORRELATIVE__CORRELATIVE_ID_COLUMN, COURSE_ID_1);
//        correlativeInsert.execute(correlativeArgs);
//
//        correlativeArgs2.put(CORRELATIVE__COURSE_ID_COLUMN, COURSE_ID_2);
//        correlativeArgs2.put(CORRELATIVE__CORRELATIVE_ID_COLUMN, COURSE_ID_1);
//        correlativeInsert.execute(correlativeArgs2);
//
//        List<Integer> list = courseJdbcDao.getUpperCorrelatives(COURSE_ID_1);
//
//        assertEquals(list.size(), 2);
//        assertTrue( (list.get(0).equals(COURSE_ID_3) || list.get(0).equals(COURSE_ID_2) ));
//        assertTrue( (list.get(1).equals(COURSE_ID_3) || list.get(1).equals(COURSE_ID_2) ));
//    }
//
//    @Test
//    public void inscriptionExists(){
//
//        final Map<String, Object> userArgs = new HashMap<>();
//        final Map<String, Object> studentArgs = new HashMap<>();
//        final Map<String, Object> inscriptionArgs = new HashMap<>();
//
//        userArgs.put(USER__DNI_COLUMN, DNI_1);
//        userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
//        userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
//        userArgs.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
//        userArgs.put(USER__ROLE_COLUMN, ROLE_1);
//
//        userInsert.execute(userArgs);
//
//        studentArgs.put(USER__DNI_COLUMN, DNI_1);
//        Number key = studentInsert.executeAndReturnKey(studentArgs);
//        docket1 = key.intValue();
//
//        //Request with no inscription
//        assertFalse(courseJdbcDao.inscriptionExists(COURSE_ID_1));
//
//        //Request with an existing inscription
//        inscriptionArgs.put(INSCRIPTION__DOCKET_COLUMN, docket1);
//        inscriptionArgs.put(INSCRIPTION__COURSE_ID_COLUMN, COURSE_ID_1);
//        inscriptionInsert.execute(inscriptionArgs);
//
//        assertTrue(courseJdbcDao.inscriptionExists(COURSE_ID_1));
//    }
//
//    @Test
//    public void deleteCorrelative() {
//
//        final Map<String, Object> correlativeArgs = new HashMap<>();
//        final Map<String, Object> correlativeArgs2 = new HashMap<>();
//        Result result;
//
//        correlativeArgs.put(CORRELATIVE__COURSE_ID_COLUMN, COURSE_ID_3);
//        correlativeArgs.put(CORRELATIVE__CORRELATIVE_ID_COLUMN, COURSE_ID_1);
//        correlativeInsert.execute(correlativeArgs);
//
//        correlativeArgs2.put(CORRELATIVE__COURSE_ID_COLUMN, COURSE_ID_2);
//        correlativeArgs2.put(CORRELATIVE__CORRELATIVE_ID_COLUMN, COURSE_ID_1);
//        correlativeInsert.execute(correlativeArgs2);
//
//
//        // Delete non existant correlativity
//        result = courseJdbcDao.deleteCorrelative(COURSE_ID_4, COURSE_ID_1);
//        assertEquals(Result.ERROR_UNKNOWN, result);
//
//        //Delete existant correlativity
//        result = courseJdbcDao.deleteCorrelative(COURSE_ID_3, COURSE_ID_1);
//        assertEquals(Result.OK, result);
//
//    }
//
//    @Test
//    public void getCorrelativeCourses(){
//        final Map<String, Object> correlativeArgs = new HashMap<>();
//        final Map<String, Object> correlativeArgs2 = new HashMap<>();
//
//        correlativeArgs.put(CORRELATIVE__COURSE_ID_COLUMN, COURSE_ID_3);
//        correlativeArgs.put(CORRELATIVE__CORRELATIVE_ID_COLUMN, COURSE_ID_1);
//        correlativeInsert.execute(correlativeArgs);
//
//        correlativeArgs2.put(CORRELATIVE__COURSE_ID_COLUMN, COURSE_ID_3);
//        correlativeArgs2.put(CORRELATIVE__CORRELATIVE_ID_COLUMN, COURSE_ID_2);
//        correlativeInsert.execute(correlativeArgs2);
//
//        Course course1 = courseJdbcDao.getById(COURSE_ID_1);
//        Course course2 = courseJdbcDao.getById(COURSE_ID_2);
//
//        List<Course> list = courseJdbcDao.getCorrelativeCourses(COURSE_ID_3);
//
//        assertEquals(list.size(), 2);
//        assertTrue( (list.get(0).equals(course1) || list.get(0).equals(course2) ));
//        assertTrue( (list.get(1).equals(course1) || list.get(1).equals(course2) ));
//    }
//}
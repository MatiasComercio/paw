package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Grade;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.models.users.User;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.shared.StudentFilter;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class StudentJdbcDaoTest {
	private static final String EMAIL_DOMAIN = "@bait.edu.ar";

	private static final String STUDENT_TABLE = "student";
	private static final String USER_TABLE = "users";
	private static final String ADDRESS_TABLE = "address";
	private static final String GRADE_TABLE = "grade";
	private static final String COURSE_TABLE = "course";
	private static final String INSCRIPTION_TABLE = "inscription";

	private static final String STUDENT__DOCKET_COLUMN = "docket";
	private static final String STUDENT__DNI_COLUMN = "dni";

	private static final String USER__DNI_COLUMN = "dni";
	private static final String USER__FIRST_NAME_COLUMN = "first_name";
	private static final String USER__LAST_NAME_COLUMN = "last_name";
	private static final String USER__GENRE_COLUMN = "genre";
	private static final String USER__BIRTHDAY_COLUMN = "birthday";
	private static final String USER__EMAIL_COLUMN = "email";
	private static final String USER__ROLE_COLUMN = "role";

	private static final String ADDRESS__DNI_COLUMN = "dni";
	private static final String ADDRESS__COUNTRY_COLUMN = "country";
	private static final String ADDRESS__CITY_COLUMN = "city";
	private static final String ADDRESS__NEIGHBORHOOD_COLUMN = "neighborhood";
	private static final String ADDRESS__STREET_COLUMN = "street";
	private static final String ADDRESS__NUMBER_COLUMN = "number";
	private static final String ADDRESS__FLOOR_COLUMN = "floor";
	private static final String ADDRESS__DOOR_COLUMN = "door";
	private static final String ADDRESS__TELEPHONE_COLUMN = "telephone";
	private static final String ADDRESS__ZIP_CODE_COLUMN = "zip_code";

	private static final String GRADE__DOCKET_COLUMN = "docket";
	private static final String GRADE__COURSE_ID_COLUMN = "course_id";
	private static final String GRADE__GRADE_COLUMN = "grade";
	private static final String GRADE__MODIFIED_COLUMN = "modified";

	private static final String COURSE__ID_COLUMN = "id";
	private static final String COURSE__NAME_COLUMN = "name";
	private static final String COURSE__CREDITS_COLUMN = "credits";
	private static final String COURSE__SEMESTER_COLUMN = "semester";

	private static final String INSCRIPTION__COURSE_ID_COLUMN = "course_id";
	private static final String INSCRIPTION__DOCKET_COLUMN = "docket";


	/* Data to be inserted/expected in/from the columns */
	private static final int DOCKET_1 = 1;
	private static final int DOCKET_2 = 2;
	private static final int DOCKET_VALID = 7357;
	private static final int DOCKET_VALID_LIMIT = 1;
	private static final int DOCKET_INVALID_LIMIT = 0;
	private static final int DOCKET_INVALID = -7357;

	private static final int DNI_VALID = 7357;
	private static final int DNI_VALID_LIMIT = 1;
	private static final int DNI_INVALID_LIMIT = 0;
	private static final int DNI_INVALID = -7357;

	private static final int DNI_1 = 12345678;
	private static final String FIRST_NAME_1 = "MaTías NIColas";
	private static final String FIRST_NAME_1_EXPECTED = "Matías Nicolas";
	private static final String LAST_NAME_1 = "Comercio vazquez";
	private static final String LAST_NAME_1_EXPECTED = "Comercio Vazquez";
	private static final String GENRE_1 = "M";
	private static final User.Genre GENRE_1_EXPECTED = User.Genre.M;
	private static final LocalDate BIRTHDAY_1 = LocalDate.parse("1994-08-17");
	private static final String EMAIL_1 = "mcomercio@bait.edu.ar";
	private static final String ROLE_1 = "STUDENT";
	private int docket1; /* Auto-generated field */


	private static final int DNI_2 = 87654321;
	private static final String FIRST_NAME_2 = "BreNda LiHuéN ";
	private static final String FIRST_NAME_2_EXPECTED = "Brenda Lihuén";
	private static final String LAST_NAME_2 = "MaYan";
	private static final String LAST_NAME_2_EXPECTED = "Mayan";
	private static final String EMAIL_2 = "blihuen@bait.edu.ar";
	private static final String ROLE_2 = "ADMIN";
	private int docket2; /* Auto-generated field */


    private static final int DNI_3 = 321456789;

	private static final String ADDRESS__COUNTRY_VALUE = "ArgEnTINA";
	private static final String ADDRESS__CITY_VALUE = "BS. as.";
	private static final String ADDRESS__NEIGHBORHOOD_VALUE = "MonTECasTro";
	private static final String ADDRESS__STREET_VALUE = "santo TOME";
	private static final int ADDRESS__NUMBER_VALUE = 0;
	private static final int ADDRESS__FLOOR_VALUE = 15;
	private static final String ADDRESS__DOOR_VALUE = "Zav";
	private static final long ADDRESS__TELEPHONE_VALUE = 45666666;
	private static final int ADDRESS__ZIP_CODE_VALUE = 1418;

	private static final String ADDRESS__COUNTRY_EXPECTED = "Argentina";
	private static final String ADDRESS__CITY_EXPECTED = "Bs. As.";
	private static final String ADDRESS__NEIGHBORHOOD_EXPECTED = "Montecastro";
	private static final String ADDRESS__STREET_EXPECTED = "Santo Tome";
	private static final Integer ADDRESS__NUMBER_EXPECTED = 0;
	private static final Integer ADDRESS__FLOOR_EXPECTED = 15;
	private static final String ADDRESS__DOOR_EXPECTED = "ZAV";
	private static final Long ADDRESS__TELEPHONE_EXPECTED = 45666666L;
	private static final Integer ADDRESS__ZIP_CODE_EXPECTED = 1418;

	private static final String ADDRESS__COUNTRY_EXPECTED_EMPTY = "";
	private static final String ADDRESS__CITY_EXPECTED_EMPTY = "";
	private static final String ADDRESS__NEIGHBORHOOD_EXPECTED_EMPTY = "";
	private static final String ADDRESS__STREET_EXPECTED_EMPTY = "";
	private static final Integer ADDRESS__NUMBER_EXPECTED_EMPTY = null;
	private static final Integer ADDRESS__FLOOR_EXPECTED_EMPTY = null;
	private static final String ADDRESS__DOOR_EXPECTED_EMPTY = "";
	private static final Long ADDRESS__TELEPHONE_EXPECTED_EMPTY = null;
	private static final Integer ADDRESS__ZIP_CODE_EXPECTED_EMPTY = null;

	private static final int COURSE_ID_1 = 1;
	private static final String COURSE_NAME_1 = "Course 1";
	private static final int COURSE_CREDITS_1 = 1;
	private static final int COURSE_SEMESTER_1 = 1;

	private static final int COURSE_ID_2 = 2;
	private static final String COURSE_NAME_2 = "Course 2";
	private static final int COURSE_CREDITS_2 = 2;
	private static final int COURSE_SEMESTER_2 = 1;

	private static final int COURSE_ID_VALID = 7357;
	private static final int COURSE_ID_VALID_LIMIT = 1;
	private static final int COURSE_ID_INVALID_LIMIT = 0;
	private static final int COURSE_ID_INVALID = -7357;
	
	private static final BigDecimal GRADE_APPROVED = BigDecimal.valueOf(9);
	private static final BigDecimal GRADE_APPROVED_LIMIT = BigDecimal.valueOf(4);
	private static final BigDecimal GRADE_DISAPPROVED_LIMIT = BigDecimal.valueOf(3.99);
	private static final BigDecimal GRADE_DISAPPROVED = BigDecimal.valueOf(2);
	private static final BigDecimal GRADE_NEW = BigDecimal.valueOf(5.5);

	/**************************************/

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserDao userDao;

	@Autowired
	private StudentJdbcDao studentJdbcDao;

	@Parameters
	public static Collection<Object[]> data() {

		final Student.Builder studentBuilder1 = new Student.Builder(DOCKET_VALID, DNI_1);
		final Student.Builder studentBuilder2 = new Student.Builder(DOCKET_VALID_LIMIT, DNI_2);

        /* Note that getStudentCourses calls getByFilter with null value too */
		Answer<Student> studentAnswer = (invocation) -> {
			final List<Student.Builder> students = new LinkedList<>();
			students.add(studentBuilder1);
			students.add(studentBuilder2);

			Object[] objects = invocation.getArguments();
			int id = (int) objects[0];
			final Student student = new Student.Builder().build();
			students.;

			return student;
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
				{ DNI_1, studentBuilder1, user1, expectedStudent1 },
				{ DNI_2, studentFilter, user2, studentServiceGetByFilter2, expectedStudents2},
				{ DNI_3, studentFilter, null, null, null }
		});
	}

	@Test
	public void testGetByDni() {
		/**
		 * +++xcheck if we can avoid casting studentAnswer
		 */
		if(studentAnswer != null) {
			when(userDao.getByDni(dni, studentBuilder)).then((Answer<?>) studentAnswer);
		}

		Student student = studentJdbcDao.getByDni(dni);
		assertThat(student, is(expectedStudent));
	}

	@Parameter // first data value (0) is default
	public /* NOT private */ int dni;

	@Parameter(value = 1)
	public /* NOT private */ Student.Builder studentBuilder;

	@Parameter(value = 2)
	public /* NOT private */ Student studentAnswer;

	@Parameter(value = 3)
	public /* NOT private */ Student expectedStudent;

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert userInsert;
	private SimpleJdbcInsert studentInsert;
	private SimpleJdbcInsert addressInsert;
	private SimpleJdbcInsert courseInsert;
	private SimpleJdbcInsert gradeInsert;
	private SimpleJdbcInsert inscriptionInsert;

	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		userInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(USER_TABLE);
		studentInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(STUDENT_TABLE).usingGeneratedKeyColumns(STUDENT__DOCKET_COLUMN);
		addressInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(ADDRESS_TABLE);
		courseInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(COURSE_TABLE);
		gradeInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(GRADE_TABLE).usingColumns(GRADE__DOCKET_COLUMN, GRADE__COURSE_ID_COLUMN, GRADE__GRADE_COLUMN);
		inscriptionInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(INSCRIPTION_TABLE).usingColumns(INSCRIPTION__COURSE_ID_COLUMN, INSCRIPTION__DOCKET_COLUMN);
		/* Order of deletation is important so as not to remove tables referenced by others */
		JdbcTestUtils.deleteFromTables(jdbcTemplate, INSCRIPTION_TABLE);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, GRADE_TABLE);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, COURSE_TABLE);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, ADDRESS_TABLE);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, STUDENT_TABLE);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, USER_TABLE);
	}

    @Test
    public void update() {
        Result result;
        final Map<String, Object> userArgs = new HashMap<>();
        final Map<String, Object> studentArgs = new HashMap<>();

        /**
         * update the student's dni
         */
        userArgs.put(USER__DNI_COLUMN, DNI_1);
        userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
        userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
        userArgs.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
		userArgs.put(USER__ROLE_COLUMN, ROLE_2);
        userInsert.execute(userArgs);

        studentArgs.put(STUDENT__DNI_COLUMN, DNI_1);
        docket1 = studentInsert.executeAndReturnKey(studentArgs).intValue();
        Student student = new Student.Builder(docket1, DNI_2).build();

        result = studentJdbcDao.update(docket1, DNI_1, student);
        assertEquals(Result.OK, result);

        /**
         * add a second user and change dni to existing user's dni
         */
        userArgs.put(USER__DNI_COLUMN, DNI_1);
        userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_2.toLowerCase());
        userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_2.toLowerCase());
        userArgs.put(USER__EMAIL_COLUMN, EMAIL_2.toLowerCase());
		userArgs.put(USER__ROLE_COLUMN, ROLE_2);
        userInsert.execute(userArgs);

        studentArgs.put(STUDENT__DNI_COLUMN, DNI_1);
        docket2 = studentInsert.executeAndReturnKey(studentArgs).intValue();
        student = new Student.Builder(docket2, DNI_2).build();

        result = studentJdbcDao.update(docket2, DNI_1, student);
        assertNotEquals(Result.OK, result);
        assertEquals(Result.STUDENT_EXISTS_DNI, result);
    }

    @Test
    public void createAddress() {
        final Map<String, Object> userArgs = new HashMap<>();
        final Map<String, Object> studentArgs = new HashMap<>();
        final Map<String, Object> addressArgs = new HashMap<>();

        userArgs.put(USER__DNI_COLUMN, DNI_1);
        userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
        userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
        userArgs.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
		userArgs.put(USER__ROLE_COLUMN, ROLE_2);
        userInsert.execute(userArgs);

        studentArgs.put(STUDENT__DNI_COLUMN, DNI_1);
        docket1 = studentInsert.executeAndReturnKey(studentArgs).intValue();

        addressArgs.put(ADDRESS__CITY_COLUMN, ADDRESS__CITY_VALUE);
        addressArgs.put(ADDRESS__COUNTRY_COLUMN, ADDRESS__COUNTRY_VALUE);
        addressArgs.put(ADDRESS__DNI_COLUMN, DNI_1);
        addressArgs.put(ADDRESS__NEIGHBORHOOD_COLUMN, ADDRESS__NEIGHBORHOOD_VALUE);
        addressArgs.put(ADDRESS__STREET_COLUMN, ADDRESS__STREET_VALUE);
        addressArgs.put(ADDRESS__NUMBER_COLUMN, ADDRESS__NUMBER_VALUE);
        addressArgs.put(ADDRESS__FLOOR_COLUMN, ADDRESS__FLOOR_VALUE);
        addressArgs.put(ADDRESS__DOOR_COLUMN, ADDRESS__DOOR_VALUE);
        addressArgs.put(ADDRESS__TELEPHONE_COLUMN, ADDRESS__TELEPHONE_VALUE);
        addressArgs.put(ADDRESS__ZIP_CODE_COLUMN, ADDRESS__ZIP_CODE_VALUE);
        addressInsert.execute(addressArgs);

        Student student = studentJdbcDao.getByDocket(docket1);
        Address address = student.getAddress();

        assertEquals(ADDRESS__CITY_EXPECTED, address.getCity());
        assertEquals(ADDRESS__COUNTRY_EXPECTED, address.getCountry());
        // assertEquals(ADDRESS__DNI_COLUMN, ); /* TODO: add getDni for address
        assertEquals(ADDRESS__NEIGHBORHOOD_EXPECTED, address.getNeighborhood());
        assertEquals(ADDRESS__STREET_EXPECTED, address.getStreet());
        assertEquals(ADDRESS__NUMBER_EXPECTED, address.getNumber());
        assertEquals(ADDRESS__FLOOR_EXPECTED, address.getFloor());
        assertEquals(ADDRESS__DOOR_EXPECTED, address.getDoor());
        assertEquals(ADDRESS__TELEPHONE_EXPECTED, address.getTelephone());
        assertEquals(ADDRESS__ZIP_CODE_EXPECTED, address.getZipCode());
    }

    @Test
    public void hasAddress() {
        final Map<String, Object> userArgs = new HashMap<>();
        final Map<String, Object> addressArgs = new HashMap<>();
        boolean hasAddress;

        /**
         * add user without address and check that it effectively does not have it
         */
        userArgs.put(USER__DNI_COLUMN, DNI_1);
        userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
        userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
        userArgs.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
		userArgs.put(USER__ROLE_COLUMN, ROLE_2);
        userInsert.execute(userArgs);

        hasAddress = studentJdbcDao.hasAddress(DNI_1);
        assertFalse(hasAddress);

        /**
         * add address to the user
         */
        addressArgs.put(ADDRESS__CITY_COLUMN, ADDRESS__CITY_VALUE);
        addressArgs.put(ADDRESS__COUNTRY_COLUMN, ADDRESS__COUNTRY_VALUE);
        addressArgs.put(ADDRESS__DNI_COLUMN, DNI_1);
        addressArgs.put(ADDRESS__NEIGHBORHOOD_COLUMN, ADDRESS__NEIGHBORHOOD_VALUE);
        addressArgs.put(ADDRESS__STREET_COLUMN, ADDRESS__STREET_VALUE);
        addressArgs.put(ADDRESS__NUMBER_COLUMN, ADDRESS__NUMBER_VALUE);
        addressInsert.execute(addressArgs);

        hasAddress = studentJdbcDao.hasAddress(DNI_1);
        assertTrue(hasAddress);
    }

    @Test
	public void addGrade() {
		Student student;
		Grade expectedGrade;
		final Map<String, Object> userArgs = new HashMap<>();
		final Map<String, Object> courseArgs = new HashMap<>();
		final Map<String, Object> studentArgs = new HashMap<>();
		final Map<String, Object> gradeArgs = new HashMap<>();

		/**
		 * Add non existant grade (without time) and check that it was added
		 */
		userArgs.put(USER__DNI_COLUMN, DNI_1);
		userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
		userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
		userArgs.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
		userArgs.put(USER__ROLE_COLUMN, ROLE_2);
		userInsert.execute(userArgs);

		courseArgs.put(COURSE__ID_COLUMN, COURSE_ID_1);
		courseArgs.put(COURSE__NAME_COLUMN, COURSE_NAME_1);
		courseArgs.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_1);
		courseArgs.put(COURSE__SEMESTER_COLUMN, COURSE_SEMESTER_1);
		courseInsert.execute(courseArgs);

		studentArgs.put(STUDENT__DNI_COLUMN, DNI_1);
		docket1 = studentInsert.executeAndReturnKey(studentArgs).intValue();

//		gradeArgs.put(GRADE__DOCKET_COLUMN, docket1);
//		gradeArgs.put(GRADE__COURSE_ID_COLUMN, COURSE_ID_1);
//		gradeArgs.put(GRADE__GRADE_COLUMN, GRADE_APPROVED);
//		KeyHolder keys = gradeInsert.executeAndReturnKeyHolder(gradeArgs);
//		Timestamp modified = (Timestamp)keys.getKeys().get(GRADE__MODIFIED_COLUMN);
//		expectedGrade = new Grade.Builder(docket1, COURSE_ID_1, GRADE_APPROVED).modified(modified).build();
//
//		student = studentJdbcDao.getGrades(docket1);
//		assertTrue(student.getGrades().contains(expectedGrade));

//		/**
//		 * Add non existant grade (with time) and check that it exists
//		 */
//		final Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
//
//		gradeArgs.put(GRADE__DOCKET_COLUMN, docket1);
//		gradeArgs.put(GRADE__COURSE_ID_COLUMN, COURSE_ID_1);
//		gradeArgs.put(GRADE__GRADE_COLUMN, GRADE_APPROVED);
//		gradeArgs.put(GRADE__MODIFIED_COLUMN, timestamp);
//		gradeInsert.execute(gradeArgs);
//		expectedGrade = new Grade.Builder(docket1, COURSE_ID_1, GRADE_APPROVED).modified(timestamp).build();
//		student = studentJdbcDao.getGrades(docket1);
//
//		assertTrue(student.getGrades().contains(expectedGrade));



	}

	@Test
	public void deleteStudent() {
		Result result;
		Student student;
		final Map<String, Object> userArgs1 = new HashMap<>();
		final Map<String, Object> studentArgs1 = new HashMap<>();

		/**
		 * Delete non existant student
		 */
		result = studentJdbcDao.deleteStudent(DOCKET_1);
		assertEquals(Result.ERROR_UNKNOWN, result);

		/**
		 * Delete existant student and then check that it is deleted
		 */
		userArgs1.put(USER__DNI_COLUMN, DNI_1);
		userArgs1.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
		userArgs1.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
		userArgs1.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
		userArgs1.put(USER__ROLE_COLUMN, ROLE_2);
		userInsert.execute(userArgs1);

		studentArgs1.put(STUDENT__DNI_COLUMN, DNI_1);
		docket1 = studentInsert.executeAndReturnKey(studentArgs1).intValue();

		result = studentJdbcDao.deleteStudent(docket1);
		assertEquals(Result.OK, result);
		student = studentJdbcDao.getByDocket(docket1);
		assertNull(student);
	}

	@Test
	public void getStudentCourses() {
		final Map<String, Object> userArgs1 = new HashMap<>();
		final Map<String, Object> userArgs2 = new HashMap<>();
		final Map<String, Object> studentArgs1 = new HashMap<>();
		final Map<String, Object> studentArgs2 = new HashMap<>();
		final Map<String, Object> courseArgs1 = new HashMap<>();
		final Map<String, Object> courseArgs2 = new HashMap<>();
		final Map<String, Object> inscriptionArgs = new HashMap<>();

		userArgs1.put(USER__DNI_COLUMN, DNI_1);
		userArgs1.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
		userArgs1.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
		userArgs1.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
		userArgs1.put(USER__ROLE_COLUMN, ROLE_2);
		userInsert.execute(userArgs1);
		userArgs2.put(USER__DNI_COLUMN, DNI_2);
		userArgs2.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_2.toLowerCase());
		userArgs2.put(USER__LAST_NAME_COLUMN, LAST_NAME_2.toLowerCase());
		userArgs2.put(USER__EMAIL_COLUMN, EMAIL_2.toLowerCase());
		userArgs2.put(USER__ROLE_COLUMN, ROLE_2);
		userInsert.execute(userArgs2);

		studentArgs1.put(STUDENT__DNI_COLUMN, DNI_1);
		Number key = studentInsert.executeAndReturnKey(studentArgs1);
		docket1 = key.intValue();
		studentArgs2.put(STUDENT__DNI_COLUMN, DNI_2);
		key = studentInsert.executeAndReturnKey(studentArgs2);
		docket2 = key.intValue();

		/* add the courses to respective students */
		courseArgs1.put(COURSE__ID_COLUMN, COURSE_ID_1);
		courseArgs1.put(COURSE__NAME_COLUMN, COURSE_NAME_1);
		courseArgs1.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_1);
		courseArgs1.put(COURSE__SEMESTER_COLUMN, COURSE_SEMESTER_1);
		courseInsert.execute(courseArgs1);
		courseArgs2.put(COURSE__ID_COLUMN, COURSE_ID_2);
		courseArgs2.put(COURSE__NAME_COLUMN, COURSE_NAME_2);
		courseArgs2.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_2);
		courseArgs2.put(COURSE__SEMESTER_COLUMN, COURSE_SEMESTER_2);
		courseInsert.execute(courseArgs2);

		inscriptionArgs.put(INSCRIPTION__COURSE_ID_COLUMN, COURSE_ID_1);
		inscriptionArgs.put(INSCRIPTION__DOCKET_COLUMN, docket2);
		inscriptionInsert.execute(inscriptionArgs);
		inscriptionArgs.put(INSCRIPTION__COURSE_ID_COLUMN, COURSE_ID_2);
		inscriptionArgs.put(INSCRIPTION__DOCKET_COLUMN, docket2);
		inscriptionInsert.execute(inscriptionArgs);

		/* Invalid student */
		List<Course> courses = studentJdbcDao.getStudentCourses(DOCKET_INVALID);
		assertNull(courses);

		/* Student with no courses */
		courses = studentJdbcDao.getStudentCourses(docket1);
		assertNotNull(courses);
		assertTrue(courses.isEmpty());

		/* Student with courses */
		Course[] expectedCourses = new Course[] {
				new Course.Builder(COURSE_ID_1).name(COURSE_NAME_1).credits(COURSE_CREDITS_1).semester(COURSE_SEMESTER_1).build(),
				new Course.Builder(COURSE_ID_2).name(COURSE_NAME_2).credits(COURSE_CREDITS_2).semester(COURSE_SEMESTER_2).build()
		};
		courses = studentJdbcDao.getStudentCourses(docket2);
		assertNotNull(courses);
		assertFalse(courses.isEmpty());
		assertArrayEquals(expectedCourses, courses.toArray());
	}

	@Test
	public void getDniByDocket() {
        final Map<String, Object> userArgs = new HashMap<>();
        final Map<String, Object> studentArgs = new HashMap<>();

        userArgs.put(USER__DNI_COLUMN, DNI_1);
        userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1);
        userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_1);
        userArgs.put(USER__EMAIL_COLUMN, EMAIL_1);
		userArgs.put(USER__ROLE_COLUMN, ROLE_2);
        userInsert.execute(userArgs);

        studentArgs.put(STUDENT__DNI_COLUMN, DNI_1);
        studentArgs.put(STUDENT__DOCKET_COLUMN, DOCKET_1);
        Number key = studentInsert.executeAndReturnKey(studentArgs);
        docket1 = key.intValue();

        int dni = studentJdbcDao.getDniByDocket(docket1);
        assertEquals(DNI_1, dni);
    }

	@Test
	public void getByDocket() {
		final Map<String, Object> userArgs1 = new HashMap<>();
		final Map<String, Object> studentArgs1 = new HashMap<>();
		final Map<String, Object> addressArgs1 = new HashMap<>();
		final Map<String, Object> userArgs2 = new HashMap<>();
		final Map<String, Object> studentArgs2 = new HashMap<>();

		userArgs1.put(USER__DNI_COLUMN, DNI_1);
		userArgs1.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
		userArgs1.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
		userArgs1.put(USER__GENRE_COLUMN, GENRE_1);
		userArgs1.put(USER__BIRTHDAY_COLUMN, Date.valueOf(BIRTHDAY_1));
		userArgs1.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
		userArgs1.put(USER__ROLE_COLUMN, ROLE_2);
		userInsert.execute(userArgs1);

		studentArgs1.put(STUDENT__DNI_COLUMN, DNI_1);
		Number key = studentInsert.executeAndReturnKey(studentArgs1);
		docket1 = key.intValue();

		addressArgs1.put(ADDRESS__DNI_COLUMN, DNI_1);
		addressArgs1.put(ADDRESS__COUNTRY_COLUMN, ADDRESS__COUNTRY_VALUE);
		addressArgs1.put(ADDRESS__CITY_COLUMN, ADDRESS__CITY_VALUE);
		addressArgs1.put(ADDRESS__NEIGHBORHOOD_COLUMN, ADDRESS__NEIGHBORHOOD_VALUE);
		addressArgs1.put(ADDRESS__STREET_COLUMN, ADDRESS__STREET_VALUE);
		addressArgs1.put(ADDRESS__NUMBER_COLUMN, ADDRESS__NUMBER_VALUE);
		addressArgs1.put(ADDRESS__FLOOR_COLUMN, ADDRESS__FLOOR_VALUE);
		addressArgs1.put(ADDRESS__DOOR_COLUMN, ADDRESS__DOOR_VALUE);
		addressArgs1.put(ADDRESS__TELEPHONE_COLUMN, ADDRESS__TELEPHONE_VALUE);
		addressArgs1.put(ADDRESS__ZIP_CODE_COLUMN, ADDRESS__ZIP_CODE_VALUE);
		addressInsert.execute(addressArgs1);

		userArgs2.put(USER__DNI_COLUMN, DNI_2);
		userArgs2.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_2_EXPECTED.toLowerCase());
		userArgs2.put(USER__LAST_NAME_COLUMN, LAST_NAME_2_EXPECTED.toLowerCase());
		userArgs2.put(USER__EMAIL_COLUMN, EMAIL_2);
		userArgs2.put(USER__ROLE_COLUMN, ROLE_2);
		userInsert.execute(userArgs2);

		studentArgs2.put(STUDENT__DNI_COLUMN, DNI_2);
		key = studentInsert.executeAndReturnKey(studentArgs2);
		docket2 = key.intValue();

		Student student = studentJdbcDao.getByDocket(docket1);

		assertNotNull(student);
		assertEquals(docket1, student.getDocket());
		assertEquals(DNI_1, student.getDni());
		assertEquals(FIRST_NAME_1_EXPECTED, student.getFirstName());
		assertEquals(LAST_NAME_1_EXPECTED, student.getLastName());
		assertEquals(GENRE_1_EXPECTED, student.getGenre());
		assertEquals(BIRTHDAY_1, student.getBirthday());
		assertThat(student.getEmail(), anyOf(possibleEmails(docket1, FIRST_NAME_1.toLowerCase(), LAST_NAME_1.toLowerCase())));
		assertEquals(ADDRESS__COUNTRY_EXPECTED, student.getAddress().getCountry());
		assertEquals(ADDRESS__CITY_EXPECTED, student.getAddress().getCity());
		assertEquals(ADDRESS__NEIGHBORHOOD_EXPECTED, student.getAddress().getNeighborhood());
		assertEquals(ADDRESS__STREET_EXPECTED, student.getAddress().getStreet());
		assertEquals(Integer.valueOf(ADDRESS__NUMBER_EXPECTED), student.getAddress().getNumber());
		assertEquals(Integer.valueOf(ADDRESS__FLOOR_EXPECTED), student.getAddress().getFloor());
		assertEquals(ADDRESS__DOOR_EXPECTED, student.getAddress().getDoor());
		assertEquals(Long.valueOf(ADDRESS__TELEPHONE_EXPECTED), student.getAddress().getTelephone());
		assertEquals(Integer.valueOf(ADDRESS__ZIP_CODE_EXPECTED), student.getAddress().getZipCode());

		student = studentJdbcDao.getByDocket(docket2);
		assertNotNull(student);
		assertEquals(docket2, student.getDocket());
		assertEquals(DNI_2, student.getDni());
		assertEquals(FIRST_NAME_2_EXPECTED, student.getFirstName());
		assertEquals(LAST_NAME_2_EXPECTED, student.getLastName());
//		assertEquals(null, student.getGenre()); /* +++xfix */
		assertEquals(null, student.getBirthday());
		assertEquals(EMAIL_2, student.getEmail());
		assertEquals(ADDRESS__COUNTRY_EXPECTED_EMPTY, student.getAddress().getCountry());
		assertEquals(ADDRESS__CITY_EXPECTED_EMPTY, student.getAddress().getCity());
		assertEquals(ADDRESS__NEIGHBORHOOD_EXPECTED_EMPTY, student.getAddress().getNeighborhood());
		assertEquals(ADDRESS__STREET_EXPECTED_EMPTY, student.getAddress().getStreet());
		assertEquals(ADDRESS__NUMBER_EXPECTED_EMPTY, student.getAddress().getNumber());
		assertEquals(ADDRESS__FLOOR_EXPECTED_EMPTY, student.getAddress().getFloor());
		assertEquals(ADDRESS__DOOR_EXPECTED_EMPTY, student.getAddress().getDoor());
		assertEquals(ADDRESS__TELEPHONE_EXPECTED_EMPTY, student.getAddress().getTelephone());
		assertEquals(ADDRESS__ZIP_CODE_EXPECTED_EMPTY, student.getAddress().getZipCode());
	}

	// TODO: write remaining test

	@Test
	public void getGrades() {
		final Map<String, Object> userArgs = new HashMap<>();
		final Map<String, Object> studentArgs = new HashMap<>();
		final Map<String, Object> courseArgs = new HashMap<>();
		final Map<String, Object> gradeArgs = new HashMap<>();

		userArgs.put(USER__DNI_COLUMN, DNI_1);
		userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
		userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
		userArgs.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
		userArgs.put(USER__ROLE_COLUMN, ROLE_2);
		userInsert.execute(userArgs);

		studentArgs.put(USER__DNI_COLUMN, DNI_1);
		Number key = studentInsert.executeAndReturnKey(studentArgs);
		docket1 = key.intValue();

		courseArgs.put(COURSE__ID_COLUMN, COURSE_ID_1);
		courseArgs.put(COURSE__NAME_COLUMN, COURSE_NAME_1);
		courseArgs.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_1);
        courseArgs.put(COURSE__SEMESTER_COLUMN, COURSE_SEMESTER_1);
		courseInsert.execute(courseArgs);
		courseArgs.put(COURSE__ID_COLUMN, COURSE_ID_2);
		courseArgs.put(COURSE__NAME_COLUMN, COURSE_NAME_2);
		courseArgs.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_2);
        courseArgs.put(COURSE__SEMESTER_COLUMN, COURSE_SEMESTER_2);
		courseInsert.execute(courseArgs);

		/* Invalid student */
		Student student = studentJdbcDao.getGrades(DOCKET_INVALID);
		assertNull(student);
		/******************/

		/* Student with no grade */
		student = studentJdbcDao.getGrades(docket1);
		assertNotNull(student);
		assertEquals(docket1, student.getDocket());
		assertEquals(FIRST_NAME_1_EXPECTED, student.getFirstName());
		assertEquals(LAST_NAME_1_EXPECTED, student.getLastName());
		assertEquals(true, student.getGrades().isEmpty());
		/*************************/

		gradeArgs.put(GRADE__DOCKET_COLUMN, docket1);
		gradeArgs.put(GRADE__COURSE_ID_COLUMN, COURSE_ID_1);
		gradeArgs.put(GRADE__GRADE_COLUMN, GRADE_APPROVED);
		final Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
		gradeArgs.put(GRADE__MODIFIED_COLUMN, timestamp);
		gradeInsert.execute(gradeArgs);
		Grade[] expectedGrades = new Grade[] {
				new Grade.Builder(docket1, COURSE_ID_1, GRADE_APPROVED).courseName(COURSE_NAME_1).modified(timestamp).build()
		};

		/* Student with one grade */
		student = studentJdbcDao.getGrades(docket1);
		assertNotNull(student);
		assertEquals(docket1, student.getDocket());
		assertEquals(FIRST_NAME_1_EXPECTED, student.getFirstName());
		assertEquals(LAST_NAME_1_EXPECTED, student.getLastName());
		assertEquals(1, student.getGrades().size());
		/* Cannot compare them by equals because of the modified column */
		/**************************/

		gradeArgs.put(GRADE__DOCKET_COLUMN, docket1);
		gradeArgs.put(GRADE__COURSE_ID_COLUMN, COURSE_ID_2);
		gradeArgs.put(GRADE__GRADE_COLUMN, GRADE_DISAPPROVED);
		final Timestamp timestamp2 = Timestamp.valueOf(LocalDateTime.now());
		gradeArgs.put(GRADE__MODIFIED_COLUMN, timestamp2);
		gradeInsert.execute(gradeArgs);
		expectedGrades = new Grade[] {
				new Grade.Builder(docket1, COURSE_ID_1, GRADE_APPROVED).courseName(COURSE_NAME_1).modified(timestamp).build(),
				new Grade.Builder(docket1, COURSE_ID_2, GRADE_DISAPPROVED).courseName(COURSE_NAME_2).modified(timestamp2).build()
		};

		/* Student with more tha one grade */
		student = studentJdbcDao.getGrades(docket1);
		assertNotNull(student);
		assertEquals(docket1, student.getDocket());
		assertEquals(FIRST_NAME_1_EXPECTED, student.getFirstName());
		assertEquals(LAST_NAME_1_EXPECTED, student.getLastName());
		/*assertArrayEquals(expectedGrades, student.getGrades().toArray());*/
		assertEquals(2, student.getGrades().size());
		/* Cannot compare them by equals because of the modified column */
		/***********************************/
	}

	@Test
	public void enrolle() {
		final Map<String, Object> userArgs = new HashMap<>();
		final Map<String, Object> studentArgs = new HashMap<>();
		final Map<String, Object> courseArgs = new HashMap<>();

		userArgs.put(USER__DNI_COLUMN, DNI_1);
		userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
		userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
		userArgs.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
		userArgs.put(USER__ROLE_COLUMN, ROLE_2);
		userInsert.execute(userArgs);

		studentArgs.put(USER__DNI_COLUMN, DNI_1);
		Number key = studentInsert.executeAndReturnKey(studentArgs);
		docket1 = key.intValue();

		courseArgs.put(COURSE__ID_COLUMN, COURSE_ID_1);
		courseArgs.put(COURSE__NAME_COLUMN, COURSE_NAME_1);
		courseArgs.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_1);
		courseArgs.put(COURSE__SEMESTER_COLUMN, COURSE_SEMESTER_1);
		courseInsert.execute(courseArgs);
		courseArgs.put(COURSE__ID_COLUMN, COURSE_ID_2);
		courseArgs.put(COURSE__NAME_COLUMN, COURSE_NAME_2);
		courseArgs.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_2);
		courseArgs.put(COURSE__SEMESTER_COLUMN, COURSE_SEMESTER_2);
		courseInsert.execute(courseArgs);


		/* Invalid docket */
		Result result = studentJdbcDao.enroll(DOCKET_INVALID, COURSE_ID_VALID_LIMIT);
		assertEquals(Result.INVALID_INPUT_PARAMETERS, result);
		/******************/

		/* Invalid course id */
		result = studentJdbcDao.enroll(DOCKET_VALID_LIMIT, COURSE_ID_INVALID);
		assertEquals(Result.INVALID_INPUT_PARAMETERS, result);
		/******************/

		/* Invalid both docket & course id */
		result = studentJdbcDao.enroll(DOCKET_INVALID, COURSE_ID_INVALID);
		assertEquals(Result.INVALID_INPUT_PARAMETERS, result);
		/******************/

		/* Invalid: docket don't exists */
		result = studentJdbcDao.enroll(DOCKET_VALID, COURSE_ID_1);
		assertEquals(Result.INVALID_INPUT_PARAMETERS, result);
		/******************/

		/* Invalid: course id don't exists */
		result = studentJdbcDao.enroll(docket1, COURSE_ID_VALID);
		assertEquals(Result.INVALID_INPUT_PARAMETERS, result);
		/******************/

		/* Valid: unique docket & course id */
		result = studentJdbcDao.enroll(docket1, COURSE_ID_1);
		assertEquals(Result.OK, result);
		/******************/

		/* Invalid: repeated docket & course id*/
		result = studentJdbcDao.enroll(docket1, COURSE_ID_1);
		assertEquals(Result.ALREADY_ENROLLED, result);
		/******************/

		/* Valid: unique docket & course id */
		result = studentJdbcDao.enroll(docket1, COURSE_ID_2);
		assertEquals(Result.OK, result);
		/******************/

		/* Check if saved correctly */
		List<Course> courses = studentJdbcDao.getStudentCourses(docket1);
		final List<Matcher<? super Integer>> possibleCourses = new LinkedList<>();
		possibleCourses.add(is(COURSE_ID_1));
		possibleCourses.add(is(COURSE_ID_2));

		assertEquals(2, courses.size());
		for (Course course : courses) {
			assertThat(course.getId(), anyOf(possibleCourses));
		}
		/******************/
	}

	@Test
	public void unenrolle() {
		final Map<String, Object> userArgs = new HashMap<>();
		final Map<String, Object> studentArgs = new HashMap<>();
		final Map<String, Object> courseArgs = new HashMap<>();

		userArgs.put(USER__DNI_COLUMN, DNI_1);
		userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
		userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
		userArgs.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
		userArgs.put(USER__ROLE_COLUMN, ROLE_2);
		userInsert.execute(userArgs);

		studentArgs.put(USER__DNI_COLUMN, DNI_1);
		Number key = studentInsert.executeAndReturnKey(studentArgs);
		docket1 = key.intValue();

		courseArgs.put(COURSE__ID_COLUMN, COURSE_ID_1);
		courseArgs.put(COURSE__NAME_COLUMN, COURSE_NAME_1);
		courseArgs.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_1);
        courseArgs.put(COURSE__SEMESTER_COLUMN, COURSE_SEMESTER_1);
        courseInsert.execute(courseArgs);
		courseArgs.put(COURSE__ID_COLUMN, COURSE_ID_2);
		courseArgs.put(COURSE__NAME_COLUMN, COURSE_NAME_2);
		courseArgs.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_2);
        courseArgs.put(COURSE__SEMESTER_COLUMN, COURSE_SEMESTER_2);
		courseInsert.execute(courseArgs);


		/* Invalid docket */
		Result result = studentJdbcDao.unenroll(DOCKET_INVALID, COURSE_ID_VALID_LIMIT);
		assertEquals(Result.NOT_EXISTENT_ENROLL, result);
		/******************/

		/* Invalid course id */
		result = studentJdbcDao.unenroll(DOCKET_VALID_LIMIT, COURSE_ID_INVALID);
		assertEquals(Result.NOT_EXISTENT_ENROLL, result);
		/******************/

		/* Invalid both docket & course id */
		result = studentJdbcDao.unenroll(DOCKET_INVALID, COURSE_ID_INVALID);
		assertEquals(Result.NOT_EXISTENT_ENROLL, result);
		/******************/

		/* Invalid: docket don't exists */
		result = studentJdbcDao.unenroll(DOCKET_VALID, COURSE_ID_1);
		assertEquals(Result.NOT_EXISTENT_ENROLL, result);
		/******************/

		/* Invalid: course id don't exists */
		result = studentJdbcDao.unenroll(docket1, COURSE_ID_VALID);
		assertEquals(Result.NOT_EXISTENT_ENROLL, result);
		/******************/

		/* Invalid: valid unique docket & course id, but no existent enroll */
		result = studentJdbcDao.unenroll(docket1, COURSE_ID_1);
		assertEquals(Result.NOT_EXISTENT_ENROLL, result);
		/******************/

		/* Valid: unique docket & course id */
		result = studentJdbcDao.enroll(docket1, COURSE_ID_1);
		assertEquals(Result.OK, result);
		result = studentJdbcDao.enroll(docket1, COURSE_ID_2);
		assertEquals(Result.OK, result);

		result = studentJdbcDao.unenroll(docket1, COURSE_ID_1);
		assertEquals(Result.OK, result);
		/******************/

		/* Invalid: not enrolled anymore */
		result = studentJdbcDao.unenroll(docket1, COURSE_ID_1);
		assertEquals(Result.NOT_EXISTENT_ENROLL, result);
		/******************/

		/* Valid: unique docket & course id */
		result = studentJdbcDao.unenroll(docket1, COURSE_ID_2);
		assertEquals(Result.OK, result);
		/******************/

		/* Check if saved correctly */
		List<Course> courses = studentJdbcDao.getStudentCourses(docket1);
		assertEquals(0, courses.size());
		/******************/
	}

	@Test
	public void testGetApprovedCourses() {
		final Map<String, Object> userArgs = new HashMap<>();
		final Map<String, Object> studentArgs = new HashMap<>();
		final Map<String, Object> courseArgs = new HashMap<>();
		final Map<String, Object> gradeArgs = new HashMap<>();

		userArgs.put(USER__DNI_COLUMN, DNI_1);
		userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
		userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
		userArgs.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
		userArgs.put(USER__ROLE_COLUMN, ROLE_2);
		userInsert.execute(userArgs);

		studentArgs.put(USER__DNI_COLUMN, DNI_1);
		Number key = studentInsert.executeAndReturnKey(studentArgs);
		docket1 = key.intValue();

		courseArgs.put(COURSE__ID_COLUMN, COURSE_ID_1);
		courseArgs.put(COURSE__NAME_COLUMN, COURSE_NAME_1);
		courseArgs.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_1);
        courseArgs.put(COURSE__SEMESTER_COLUMN, COURSE_SEMESTER_1);
		courseInsert.execute(courseArgs);
		courseArgs.put(COURSE__ID_COLUMN, COURSE_ID_2);
		courseArgs.put(COURSE__NAME_COLUMN, COURSE_NAME_2);
		courseArgs.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_2);
        courseArgs.put(COURSE__SEMESTER_COLUMN, COURSE_SEMESTER_2);
        courseInsert.execute(courseArgs);

		/* Invalid student */
		Collection<Course> courses = studentJdbcDao.getApprovedCourses(DOCKET_INVALID);
		assertNotNull(courses);
		assertEquals(true, courses.isEmpty());
		/******************/

		/* Student with no approved courses */
		courses = studentJdbcDao.getApprovedCourses(docket1);
		assertNotNull(courses);
		assertEquals(true, courses.isEmpty());
		/*************************/

		gradeArgs.put(GRADE__DOCKET_COLUMN, docket1);
		gradeArgs.put(GRADE__COURSE_ID_COLUMN, COURSE_ID_1);
		gradeArgs.put(GRADE__GRADE_COLUMN, GRADE_APPROVED);
		gradeInsert.execute(gradeArgs);
		Course[] expectedApprovedCourses = new Course[] {
				new Course.Builder(COURSE_ID_1).name(COURSE_NAME_1).credits(COURSE_CREDITS_1).semester(COURSE_SEMESTER_1).build()
		};

		/* Student with one approved course */
		courses = studentJdbcDao.getApprovedCourses(docket1);
		assertNotNull(courses);
		assertArrayEquals(expectedApprovedCourses, courses.toArray());
		/**************************/

		gradeArgs.put(GRADE__DOCKET_COLUMN, docket1);
		gradeArgs.put(GRADE__COURSE_ID_COLUMN, COURSE_ID_2);
		gradeArgs.put(GRADE__GRADE_COLUMN, GRADE_DISAPPROVED);
		gradeInsert.execute(gradeArgs);

		/* Student with more than one grade, but only one course approved */
		courses = studentJdbcDao.getApprovedCourses(docket1);
		assertNotNull(courses);
		assertArrayEquals(expectedApprovedCourses, courses.toArray());
		/***********************************/

		gradeArgs.put(GRADE__DOCKET_COLUMN, docket1);
		gradeArgs.put(GRADE__COURSE_ID_COLUMN, COURSE_ID_2);
		gradeArgs.put(GRADE__GRADE_COLUMN, GRADE_DISAPPROVED_LIMIT);
		gradeInsert.execute(gradeArgs);

		/* Student with more than one grade, but only one course approved */
		courses = studentJdbcDao.getApprovedCourses(docket1);
		assertNotNull(courses);
		assertArrayEquals(expectedApprovedCourses, courses.toArray());
		/***********************************/

		gradeArgs.put(GRADE__DOCKET_COLUMN, docket1);
		gradeArgs.put(GRADE__COURSE_ID_COLUMN, COURSE_ID_2);
		gradeArgs.put(GRADE__GRADE_COLUMN, GRADE_APPROVED_LIMIT);
		gradeInsert.execute(gradeArgs);

		expectedApprovedCourses = new Course[] {
				new Course.Builder(COURSE_ID_1).name(COURSE_NAME_1).credits(COURSE_CREDITS_1).semester(COURSE_SEMESTER_1).build(),
				new Course.Builder(COURSE_ID_2).name(COURSE_NAME_2).credits(COURSE_CREDITS_2).semester(COURSE_SEMESTER_2).build()
		};

		/* Student with more than one grade, but only two course approved */
		courses = studentJdbcDao.getApprovedCourses(docket1);
		assertNotNull(courses);
		assertArrayEquals(expectedApprovedCourses, courses.toArray());
		/***********************************/
	}



	@Test
	public void createStudent() {

		Address address = new Address.Builder(ADDRESS__COUNTRY_EXPECTED, ADDRESS__CITY_EXPECTED, ADDRESS__NEIGHBORHOOD_EXPECTED,
				ADDRESS__STREET_EXPECTED, ADDRESS__NUMBER_EXPECTED).build();
		// OK insertion

		Student student = new Student.Builder(0, DNI_1).firstName(FIRST_NAME_1).lastName(LAST_NAME_1)
				.genre(User.Genre.M).build();//address(address).build();

		Result result = studentJdbcDao.create(student);
		assertEquals(Result.OK, result);

		// Existing  DNI
		student = new Student.Builder(0, DNI_1).firstName(FIRST_NAME_1).lastName(LAST_NAME_1).genre(User.Genre.M)
				.address(address).build();

		result = studentJdbcDao.create(student);
		assertEquals(Result.STUDENT_EXISTS_DNI, result);

	}
/*
	@Test
	public void editGrade(){
		Result result;
		final Map<String, Object> gradeArgs = new HashMap<>();

		gradeArgs.put(GRADE__DOCKET_COLUMN, docket1);
		gradeArgs.put(GRADE__COURSE_ID_COLUMN, COURSE_ID_1);
		gradeArgs.put(GRADE__GRADE_COLUMN, GRADE_APPROVED);
		KeyHolder keys = gradeInsert.executeAndReturnKeyHolder(gradeArgs);
		Timestamp modified = (Timestamp)keys.getKeys().get(GRADE__MODIFIED_COLUMN);
		Grade expectedGrade = new Grade.Builder(docket1, COURSE_ID_1, GRADE_APPROVED).modified(modified).build();

		result = studentJdbcDao.editGrade(expectedGrade, GRADE_NEW);
		assertEquals(Result.OK, result);

		//Check the grade actually changed
		for( Grade grade : studentJdbcDao.getGrades(docket1).getGrades()){
			if(grade.getModified() != null && grade.getModified().equals(modified)){
				assertEquals(grade.getGrade(),  GRADE_NEW);
			}
		}

	}
*/

	private List<Matcher<? super String>> possibleEmails(final int docket, final String firstName, final String lastName) {
		final List<Matcher<? super String>> matchers = new LinkedList<>();
		final String defaultEmail = "student" + docket + EMAIL_DOMAIN;

		/* In case firstName == null */
		if (firstName == null || firstName.equals("")|| lastName == null || lastName.equals("")) {
			matchers.add(is(defaultEmail));
			return matchers;
		}

		/* Add all possible emails */
		final String initChar = firstName.substring(0, 1).toLowerCase();

		final String[] lastNames = lastName.toLowerCase().split(" ");
		StringBuilder currentEmail;
		for (int i = 0 ; i < 2 && i < lastNames.length ; i++) {
			currentEmail = new StringBuilder(initChar);
			for (int j = 0 ; j <= i; j++) {
				currentEmail.append(lastNames[j]);
			}
			currentEmail.append(EMAIL_DOMAIN);
			matchers.add(is(String.valueOf(currentEmail)));
		}

		/* In case all email trials failed */
		/* This, for sure, does not exists as includes the docket, which is unique */
		matchers.add(is(defaultEmail));

		return matchers;
	}
}

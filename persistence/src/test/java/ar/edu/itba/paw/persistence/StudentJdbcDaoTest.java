package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Grade;
import ar.edu.itba.paw.models.users.Student;
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
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
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
public class StudentJdbcDaoTest {
	private static final String EMAIL_DOMAIN = "@bait.edu.ar";

	private static final String STUDENT_TABLE = "student";
	private static final String USER_TABLE = "users";
	private static final String GRADE_TABLE = "grade";
	private static final String COURSE_TABLE = "course";

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

	private static final String COURSE__ID_COLUMN = "id";
	private static final String COURSE__NAME_COLUMN = "name";
	private static final String COURSE__CREDITS_COLUMN = "credits";


	/* Data to be inserted/expected in/from the columns */
	private static final int DOCKET_INVALID = -1;

	private static final int DNI_1 = 12345678;
	private static final String FIRST_NAME_1 = "MaTías NIColas";
	private static final String FIRST_NAME_1_EXPECTED = "Matías Nicolas";
	private static final String LAST_NAME_1 = "Comercio vazquez";
	private static final String LAST_NAME_1_EXPECTED = "Comercio Vazquez";
	private static final String GENRE_1 = "M";
	private static final String GENRE_1_EXPECTED = "Male";
	private static final LocalDate BIRTHDAY_1 = LocalDate.parse("1994-08-17");
	private int docket1; /* Auto-generated field */

	private static final int DNI_2 = 87654321;
	private static final String EMAIL_2 = "mcomercio@bait.edu.ar";
	private int docket2; /* Auto-generated field */

	private static final int COURSE_ID_1 = 1;
	private static final String COURSE_NAME_1 = "Course 1";
	private static final int COURSE_CREDITS_1 = 1;

	private static final int COURSE_ID_2 = 2;
	private static final String COURSE_NAME_2 = "Course 2";
	private static final int COURSE_CREDITS_2 = 2;
	
	private static final BigDecimal GRADE_1 = BigDecimal.valueOf(9);
	private static final BigDecimal GRADE_2 = BigDecimal.valueOf(2);

	/**************************************/

	@Autowired
	private DataSource dataSource;

	@Autowired
	private StudentJdbcDao studentJdbcDao;

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert userInsert;
	private SimpleJdbcInsert studentInsert;
	private SimpleJdbcInsert courseInsert;
	private SimpleJdbcInsert gradeInsert;

	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		userInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(USER_TABLE);
		studentInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(STUDENT_TABLE).usingGeneratedKeyColumns(STUDENT__DOCKET_COLUMN);
		courseInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(COURSE_TABLE);
		gradeInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(GRADE_TABLE);

		/* Order of deletation is important so as not to remove tables referenced by others */
		JdbcTestUtils.deleteFromTables(jdbcTemplate, GRADE_TABLE);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, COURSE_TABLE);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, STUDENT_TABLE);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, USER_TABLE);
	}

	@Test
	public void getByDocket() {
		final Map<String, Object> userArgs1 = new HashMap<>();
		final Map<String, Object> studentArgs1 = new HashMap<>();
		final Map<String, Object> userArgs2 = new HashMap<>();
		final Map<String, Object> studentArgs2 = new HashMap<>();

		userArgs1.put(USER__DNI_COLUMN, DNI_1);
		userArgs1.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
		userArgs1.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
		userArgs1.put(USER__GENRE_COLUMN, GENRE_1);
		userArgs1.put(USER__BIRTHDAY_COLUMN, Date.valueOf(BIRTHDAY_1));
		userInsert.execute(userArgs1);

		studentArgs1.put(STUDENT__DNI_COLUMN, DNI_1);
		Number key = studentInsert.executeAndReturnKey(studentArgs1);
		docket1 = key.intValue();

		userArgs2.put(USER__DNI_COLUMN, DNI_2);
		userArgs2.put(USER__EMAIL_COLUMN, EMAIL_2);
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
		assertThat(student.getEmail(), anyOf(possibleEmails(DNI_1,
				FIRST_NAME_1.toLowerCase(),
				LAST_NAME_1.toLowerCase())));

		student = studentJdbcDao.getByDocket(docket2);
		assertNotNull(student);
		assertEquals(docket2, student.getDocket());
		assertEquals(DNI_2, student.getDni());
		assertEquals("", student.getFirstName());
		assertEquals("", student.getLastName());
		assertEquals("", student.getGenre());
		assertEquals(null, student.getBirthday());
		assertEquals(EMAIL_2, student.getEmail());
	}

	// +++x TODO: write getAll test

	@Test
	public void getGrades() {
		final Map<String, Object> userArgs = new HashMap<>();
		final Map<String, Object> studentArgs = new HashMap<>();
		final Map<String, Object> courseArgs = new HashMap<>();
		final Map<String, Object> gradeArgs = new HashMap<>();

		userArgs.put(USER__DNI_COLUMN, DNI_1);
		userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
		userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
		userInsert.execute(userArgs);

		studentArgs.put(USER__DNI_COLUMN, DNI_1);
		Number key = studentInsert.executeAndReturnKey(studentArgs);
		docket1 = key.intValue();

		courseArgs.put(COURSE__ID_COLUMN, COURSE_ID_1);
		courseArgs.put(COURSE__NAME_COLUMN, COURSE_NAME_1);
		courseArgs.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_1);
		courseInsert.execute(courseArgs);
		courseArgs.put(COURSE__ID_COLUMN, COURSE_ID_2);
		courseArgs.put(COURSE__NAME_COLUMN, COURSE_NAME_2);
		courseArgs.put(COURSE__CREDITS_COLUMN, COURSE_CREDITS_2);
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
		gradeArgs.put(GRADE__GRADE_COLUMN, GRADE_1);
		gradeInsert.execute(gradeArgs);
		Grade[] expectedGrades = new Grade[] {
				new Grade.Builder(docket1, COURSE_ID_1, GRADE_1).courseName(COURSE_NAME_1).build()
		};

		/* Student with one grade */
		student = studentJdbcDao.getGrades(docket1);
		assertNotNull(student);
		assertEquals(docket1, student.getDocket());
		assertEquals(FIRST_NAME_1_EXPECTED, student.getFirstName());
		assertEquals(LAST_NAME_1_EXPECTED, student.getLastName());
		assertArrayEquals(expectedGrades, student.getGrades().toArray());
		/**************************/

		gradeArgs.put(GRADE__DOCKET_COLUMN, docket1);
		gradeArgs.put(GRADE__COURSE_ID_COLUMN, COURSE_ID_2);
		gradeArgs.put(GRADE__GRADE_COLUMN, GRADE_2);
		gradeInsert.execute(gradeArgs);
		expectedGrades = new Grade[] {
				new Grade.Builder(docket1, COURSE_ID_1, GRADE_1).courseName(COURSE_NAME_1).build(),
				new Grade.Builder(docket1, COURSE_ID_2, GRADE_2).courseName(COURSE_NAME_2).build()
		};

		/* Student with more tha one grade */
		student = studentJdbcDao.getGrades(docket1);
		assertNotNull(student);
		assertEquals(docket1, student.getDocket());
		assertEquals(FIRST_NAME_1_EXPECTED, student.getFirstName());
		assertEquals(LAST_NAME_1_EXPECTED, student.getLastName());
		assertArrayEquals(expectedGrades, student.getGrades().toArray());
		/***********************************/
	}

	private List<Matcher<? super String>> possibleEmails(final int dni, final String firstName, final String lastName) {
		final List<Matcher<? super String>> matchers = new LinkedList<>();
		final String defaultEmail = "student" + dni + EMAIL_DOMAIN;

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
		/* This, for sure, does not exists as includes the dni, which is unique */
		matchers.add(is(defaultEmail));

		return matchers;
	}
}

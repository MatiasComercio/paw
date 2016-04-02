package ar.edu.itba.paw.persistence;

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

	private static final String STUDENT_TABLE = "student";
	private static final String USER_TABLE = "users";
	private static final String DOCKET_COLUMN = "docket";
	private static final String DNI_COLUMN = "dni";
	private static final String FIRST_NAME_COLUMN = "first_name";
	private static final String LAST_NAME_COLUMN = "last_name";
	private static final String GENRE_COLUMN = "genre";
	private static final String BIRTHDAY_COLUMN = "birthday";
	private static final String EMAIL_COLUMN = "email";

	private static final String EMAIL_DOMAIN = "@bait.edu.ar";

	/* Data to be inserted/expected in/from the columns */
	private static final int DNI_1 = 12345678;
	private static final String FIRST_NAME_1 = "MaTías NIColas";
	private static final String FIRST_NAME_1_EXPECTED = "Matías Nicolas";
	private static final String LAST_NAME_1 = "Comercio vazquez";
	private static final String LAST_NAME_1_EXPECTED = "Comercio Vazquez";
	private static final String GENRE_1 = "M";
	private static final String GENRE_1_EXPECTED = "Male";
	private static final LocalDate BIRTHDAY_1 = LocalDate.parse("1994-08-17");
//	private static final String EMAIL_1 = "mcomercio@bait.edu.ar";
	private int docket1; /* Auto-generated field */

	private static final int DNI_2 = 87654321;
	private int docket2; /* Auto-generated field */
	/**************************************/

	@Autowired
	private DataSource dataSource;

	@Autowired
	private StudentJdbcDao studentJdbcDao;

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert userInsert;
	private SimpleJdbcInsert studentInsert;

/*	+++x todo: try to implement what it is at the setUp method, but here, so as it is done only once.
	@BeforeClass
	public static void setUpClass() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		userInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(USER_TABLE);
		studentInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(STUDENT_TABLE);

		final Map<String, Object> userArgs = new HashMap<>();
		final Map<String, Object> studentArgs = new HashMap<>();

		userArgs.put(DNI_COLUMN, DNI_1);
		userArgs.put(FIRST_NAME_COLUMN, FIRST_NAME_1);
		userArgs.put(LAST_NAME_COLUMN, LAST_NAME_1);
		userArgs.put(GENRE_COLUMN, GENRE_1);
		userArgs.put(BIRTHDAY_COLUMN, BIRTHDAY_1);
		userArgs.put(EMAIL_COLUMN, EMAIL_1);
		userInsert.execute(userArgs);

		studentArgs.put(DOCKET_COLUMN, DOCKET_1);
		studentArgs.put(DNI_COLUMN, DNI_1);
		studentInsert.execute(studentArgs);

		userArgs.put(DNI_COLUMN, DNI_2);
		userInsert.execute(userArgs);

		studentArgs.put(DOCKET_COLUMN, DOCKET_2);
		studentArgs.put(DNI_COLUMN, DNI_2);
		studentInsert.execute(studentArgs);
	}*/

	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		userInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(USER_TABLE);
		studentInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(STUDENT_TABLE).usingGeneratedKeyColumns(DOCKET_COLUMN);

		JdbcTestUtils.deleteFromTables(jdbcTemplate, USER_TABLE);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, STUDENT_TABLE);
		final Map<String, Object> userArgs1 = new HashMap<>();
		final Map<String, Object> studentArgs1 = new HashMap<>();
		final Map<String, Object> userArgs2 = new HashMap<>();
		final Map<String, Object> studentArgs2 = new HashMap<>();

		userArgs1.put(DNI_COLUMN, DNI_1);
		userArgs1.put(FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
		userArgs1.put(LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
		userArgs1.put(GENRE_COLUMN, GENRE_1);
		userArgs1.put(BIRTHDAY_COLUMN, Date.valueOf(BIRTHDAY_1));
//		userArgs1.put(EMAIL_COLUMN, EMAIL_1);
		userInsert.execute(userArgs1);

		studentArgs1.put(DNI_COLUMN, DNI_1);
		Number key = studentInsert.executeAndReturnKey(studentArgs1);
		docket1 = key.intValue();

		userArgs2.put(DNI_COLUMN, DNI_2);
		userInsert.execute(userArgs2);

		studentArgs2.put(DNI_COLUMN, DNI_2);
		key = studentInsert.executeAndReturnKey(studentArgs2);
		docket2 = key.intValue();
	}

	@Test
	public void getByDocket() {
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
		System.out.println(student);
		assertEquals(docket2, student.getDocket());
		assertEquals(DNI_2, student.getDni());
		assertEquals("", student.getFirstName());
		assertEquals("", student.getLastName());
		assertEquals("", student.getGenre());
		assertEquals(null, student.getBirthday());
		assertEquals(getDefaultEmail(DNI_2), student.getEmail());
	}

	private String getDefaultEmail(final int dni) {
		return "student" + dni + EMAIL_DOMAIN;
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

		final StringBuilder email = new StringBuilder(initChar);

		final String[] lastNames = lastName.toLowerCase().split(" ");
		StringBuilder currentEmail;
		for (int i = 0 ; i < 2 && i < lastNames.length ; i++) {
			currentEmail = email;
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

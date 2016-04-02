package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.users.Student;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

	/* Data to be inserted in the columns */
	private static final int DNI_1 = 12345678;
	private static final String FIRST_NAME_1 = "MaTías NIColas";
	private static final String FIRST_NAME_1_EXPECTED = "Matías Nicolas";
	private static final String LAST_NAME_1 = "Comercio vazquez";
	private static final String LAST_NAME_1_EXPECTED = "Comercio Vazquez";
	private static final String GENRE_1 = "M";
	private static final String GENRE_1_EXPECTED = "Male";
	private static final LocalDate BIRTHDAY_1 = LocalDate.parse("1994-08-17");
	private static final String EMAIL_1 = "mcomercio@bait.edu.ar";
	private static final int DOCKET_1 = 12345;
	private int docket1;

	private static final int DNI_2 = 87654321;
	private static final int DOCKET_2 = 12345;
	private int docket2;


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
		final Map<String, Object> userArgs = new HashMap<>();
		final Map<String, Object> studentArgs = new HashMap<>();

		userArgs.put(DNI_COLUMN, DNI_1);
		userArgs.put(FIRST_NAME_COLUMN, FIRST_NAME_1);
		userArgs.put(LAST_NAME_COLUMN, LAST_NAME_1);
		userArgs.put(GENRE_COLUMN, GENRE_1);
		userArgs.put(BIRTHDAY_COLUMN, Date.valueOf(BIRTHDAY_1));
		userArgs.put(EMAIL_COLUMN, EMAIL_1);
		userInsert.execute(userArgs);

		studentArgs.put(DNI_COLUMN, DNI_1);
		Number key = studentInsert.executeAndReturnKey(studentArgs);
		docket1 = key.intValue();

		userArgs.put(DNI_COLUMN, DNI_2);
		userInsert.execute(userArgs);

		studentArgs.put(DNI_COLUMN, DNI_2);
		key = studentInsert.executeAndReturnKey(studentArgs);
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
		assertEquals(EMAIL_1, student.getEmail());

		student = studentJdbcDao.getByDocket(docket2);
		assertNotNull(student);
		assertEquals(docket2, student.getDocket());
		assertEquals(DNI_2, student.getDni());
		assertEquals("", student.getFirstName());
		assertEquals("", student.getLastName());
		assertEquals("", student.getGenre());
		assertEquals(null, student.getBirthday());
		assertEquals(getDefaultEmail(DNI_2), student.getEmail());
	}

	private String getDefaultEmail(final int dni) {
		return "student" + dni + "@bait.edu.ar";
	}

}

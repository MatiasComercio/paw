package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;


import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("/schema.sql")
public class UserDaoJdbcTest {

	private static final String PASSWORD = "Password";
	private static final String USERNAME = "Username";

	@Autowired
	private DataSource dataSource;

	@Autowired
	private UserDaoJdbc userDaoJdbc;

	private JdbcTemplate jdbcTemplate;

	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		/* Si comento esto --> deberia fallar */
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
	}

	@Repeat(10)
	@Test
	public void testCreate() {
		final User user = userDaoJdbc.create(USERNAME, PASSWORD);

		assertNotNull(user);
		assertEquals(USERNAME, user.getUsername());
		assertEquals(PASSWORD, user.getPassword());

		assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
	}


}

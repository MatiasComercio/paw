package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoJdbc implements UserDao {

	private static final String TABLE_NAME = "users";
	private static final String USERNAME_COLUMN = "username";
	private static final String PASSWORD_COLUMN = "password";

	private final UserRowMapper userRowMapper;
	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	@Autowired
	public UserDaoJdbc(final DataSource dataSource) {

		this.userRowMapper = new UserRowMapper();
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		/* TODO: export table name as a private final String */
		this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(TABLE_NAME);

		/* TODO: export table creation as a private final String */
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (username varchar (100), password varchar (100))");
	}

	public User create(final String username, final String password) {
		final Map<String, Object> args = new HashMap<>();
		args.put(USERNAME_COLUMN, username);
		args.put(PASSWORD_COLUMN, password);

		jdbcInsert.execute(args);

		return new User(username, password);
	}

	@Override
	public User getByUsername(final String username) {
		List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE username = ? LIMIT 1", userRowMapper, username);

		return users.isEmpty() ? null : users.get(0);
	}

	private static class UserRowMapper implements RowMapper<User> {
		@Override
		public User mapRow(final ResultSet resultSet, final int rowNumber) throws SQLException {
			return new User(resultSet.getString(USERNAME_COLUMN), resultSet.getString(PASSWORD_COLUMN));
		}
	}
}

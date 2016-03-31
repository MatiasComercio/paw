package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoJdbc implements UserDao { /* TODO: Change for UserJdbcDao */

	private static final String TABLE_NAME = "users";
	private static final String USER_ID_COLUMN = "userid";
	private static final String USERNAME_COLUMN = "username";
	private static final String PASSWORD_COLUMN = "password";

	private final JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;

	/* Estoy creando una funcion anonima */
	private final RowMapper<User> userRowMapper = (resultSet, rowNum) ->
			new User(resultSet.getInt(USER_ID_COLUMN),
					resultSet.getString(USERNAME_COLUMN),
					resultSet.getString(PASSWORD_COLUMN));

	@Autowired
	public UserDaoJdbc(final DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		/* TODO: export table name as a private final String */
		this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(TABLE_NAME).usingGeneratedKeyColumns(USER_ID_COLUMN);

/*		*//* TODO: export table creation as a private final String *//*
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (" +
				"userId integer SERIAL PRIMARY KEY, " +
				"username varchar (100), " +
				"password varchar (100))");*/
	}

	public User create(final String username, final String password) {
		final Map<String, Object> args = new HashMap<>();
		args.put(USERNAME_COLUMN, username);
		args.put(PASSWORD_COLUMN, password);
		final Number key = jdbcInsert.executeAndReturnKey(args);

		return new User(key.intValue(), username, password);
	}

	@Override
	public User getByUsername(final String username) {
		List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE username = ? LIMIT 1", userRowMapper, username);

		return users.isEmpty() ? null : users.get(0);
	}
}

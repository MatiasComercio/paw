package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserJdbcDao implements UserDao {

	private static final String ROLES_TABLE = "roles";

	private static final String ROLES__DNI_COLUMN = "dni";
	private static final String ROLES__ROLE_COLUMN = "role";

	private static final String EVERYTHING = "*";
	private static final String EQUALS = "=";
	private static final String GIVEN_PARAMETER = "?";

	private static final String GET_ROLES;

	static {
/*	Usage example:
		GET_BY_DNI =
				select(EVERYTHING) +
						from(join(USER_TABLE, ROLES_TABLE, USER__DNI_COLUMN, ROLES__DNI_COLUMN)) +
						where(tableCol(USER_TABLE, USER__DNI_COLUMN), EQUALS, GIVEN_PARAMETER);*/
		GET_ROLES =
				select(ROLES__ROLE_COLUMN) + from(ROLES_TABLE) + where(ROLES__DNI_COLUMN, EQUALS, GIVEN_PARAMETER);

	}

	private final JdbcTemplate jdbcTemplate;

	/* Constructors */
	@Autowired
	public UserJdbcDao(final DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	/* /Constructors */


	/* Public Methods */
	@Override
	public List<Role> getRoles(final int requestedDni) {
		final RowMapper<Role> getRolesRowMapper = ((rs, rowNum) ->  {
			final String roleString = rs.getString(ROLES__ROLE_COLUMN);

			final Role role;
			if (roleString != null) {
				role = Role.valueOf(roleString);
			} else {
				role = null;
			}

			return role;
		});

		/* This method should return 0 or 1 student. */
		/* Grab student's data */
		final List<Role> roles = jdbcTemplate.query(GET_ROLES, getRolesRowMapper, requestedDni);

		return roles;
	}



	/* /Public Methods */





	/* Private Static Methods */
	private static String select(final String... cols) {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT ");
		buildSentence(stringBuilder, cols);
		return stringBuilder.toString();
	}

	private static String from(final String... cols) {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("FROM ");
		buildSentence(stringBuilder, cols);
		return stringBuilder.toString();
	}

	private static String where(final String... cols) {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("WHERE ");
		for (String col : cols) {
			stringBuilder.append(col);
		}
		stringBuilder.append(" ");
		return stringBuilder.toString();
	}

	private static void buildSentence(final StringBuilder stringBuilder, final String... cols) {
		int i = 0;
		final int lCols = cols.length;
		for (String col : cols) {
			i++;
			stringBuilder.append(col);
			if (i < lCols) {
				stringBuilder.append(" ,");
			}
		}
		stringBuilder.append(" ");
	}

	private static String join(final String t1, final String t2, final String c1, final String c2) {
		return t1 + " JOIN " + t2 + " ON " + t1 + "." + c1 + " = " + t2 + "." + c2;
	}

	private static String tableCol(final String table, final String column) {
		return table + "." + column;
	}
}

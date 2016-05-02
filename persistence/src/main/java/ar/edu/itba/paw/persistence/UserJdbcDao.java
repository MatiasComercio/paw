package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.models.users.User;
import ar.edu.itba.paw.shared.Result;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserJdbcDao implements UserDao {
	private static final String EMAIL_DOMAIN = "@bait.edu.ar";

	private static final String ROLES_TABLE = "roles";
	private static final String USERS_TABLE = "users";
	private static final String ADDRESS_TABLE = "address";

	private static final String ROLES__DNI_COLUMN = "dni";
	private static final String ROLES__ROLE_COLUMN = "role";

	private static final String USER__DNI_COLUMN = "dni";
	private static final String USER__FIRST_NAME_COLUMN = "first_name";
	private static final String USER__LAST_NAME_COLUMN = "last_name";
	private static final String USER__GENRE_COLUMN = "genre";
	private static final String USER__BIRTHDAY_COLUMN = "birthday";
	private static final String USER__EMAIL_COLUMN = "email";
	private static final String USER__PASSWORD_COLUMN = "password";

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


	private static final String EVERYTHING = "*";
	private static final String EQUALS = "=";
	private static final String GIVEN_PARAMETER = "?";

	private static final String GET_ROLES;
	private static final String GET_EMAILS;
	private static final String DELETE_USER;

	static {
/*	Usage example:
		GET_BY_DNI =
				select(EVERYTHING) +
						from(join(USER_TABLE, ROLES_TABLE, USER__DNI_COLUMN, ROLES__DNI_COLUMN)) +
						where(tableCol(USER_TABLE, USER__DNI_COLUMN), EQUALS, GIVEN_PARAMETER);*/
		GET_ROLES =
				select(ROLES__ROLE_COLUMN) + from(ROLES_TABLE) + where(ROLES__DNI_COLUMN, EQUALS, GIVEN_PARAMETER);
		GET_EMAILS =
				select(USER__EMAIL_COLUMN)
				+ from(USERS_TABLE);

		DELETE_USER =
				deleteFrom(USERS_TABLE)
						+ where(USER__DNI_COLUMN, EQUALS, GIVEN_PARAMETER);


	}

	private final RowMapper<String> emailRowMapper = (resultSet, rowNumber) -> resultSet.getString(USER__EMAIL_COLUMN);

	private final JdbcTemplate jdbcTemplate;

	private final SimpleJdbcInsert userInsert;
	private final SimpleJdbcInsert addressInsert;

	/* Constructors */
	@Autowired
	public UserJdbcDao(final DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.userInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(USERS_TABLE)
				.usingColumns(USER__DNI_COLUMN, USER__FIRST_NAME_COLUMN, USER__LAST_NAME_COLUMN,
						USER__GENRE_COLUMN, USER__BIRTHDAY_COLUMN, USER__EMAIL_COLUMN);
		this.addressInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(ADDRESS_TABLE);
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

	@Override
	public Result create(User user) {
		final Map<String, Object> userArgs = new HashMap<>();

		userArgs.put(USER__DNI_COLUMN, user.getDni());
		userArgs.put(USER__FIRST_NAME_COLUMN, user.getFirstName());
		userArgs.put(USER__LAST_NAME_COLUMN, user.getLastName());

		userArgs.put(USER__GENRE_COLUMN, user.getGenre().name());
		userArgs.put(USER__BIRTHDAY_COLUMN, user.getBirthday());
		userArgs.put(USER__EMAIL_COLUMN, createEmail(user.getDni(), user.getFirstName(),
				user.getLastName()));
		try {
			userInsert.execute(userArgs);
		}catch(DuplicateKeyException e){
			return Result.STUDENT_EXISTS_DNI;
		} catch (DataAccessException e) {
			return Result.ERROR_UNKNOWN;
		}

		createAddress(user);
		return Result.OK;
	}

	@Override
	public Result delete(Integer dni) {
		int rowsAffected = jdbcTemplate.update();
	}



	/* /Public Methods */


	public void createAddress(final User user) {

		Map<String, Object> addressArgs = new HashMap<>();

		Address addr = user.getAddress();


		addressArgs.put(ADDRESS__DNI_COLUMN, user.getDni());
		addressArgs.put(ADDRESS__COUNTRY_COLUMN, WordUtils.capitalize(addr.getCountry().toLowerCase()));
		addressArgs.put(ADDRESS__CITY_COLUMN, WordUtils.capitalize(addr.getCity()).toLowerCase());
		addressArgs.put(ADDRESS__NEIGHBORHOOD_COLUMN, WordUtils.capitalize(addr.getNeighborhood()).toLowerCase());
		addressArgs.put(ADDRESS__STREET_COLUMN, WordUtils.capitalize(addr.getStreet()).toLowerCase());
		addressArgs.put(ADDRESS__NUMBER_COLUMN,addr.getNumber());
		addressArgs.put(ADDRESS__FLOOR_COLUMN, addr.getFloor());
		addressArgs.put(ADDRESS__DOOR_COLUMN, addr.getDoor());
		addressArgs.put(ADDRESS__TELEPHONE_COLUMN, addr.getTelephone());
		addressArgs.put(ADDRESS__ZIP_CODE_COLUMN, addr.getZipCode());
		addressInsert.execute(addressArgs);
	}

	private String createEmail(final int dni, final String firstName, final String lastName) {
		final String defaultEmail = "admin" + dni + EMAIL_DOMAIN;

		if (firstName == null || firstName.equals("")|| lastName == null || lastName.equals("")) {
			return defaultEmail;
		}

		final String initChar = firstName.substring(0, 1).toLowerCase();

		final String[] lastNames = lastName.toLowerCase().split(" ");
		StringBuilder currentEmail;
		for (int i = 0 ; i < 2 && i < lastNames.length ; i++) {
			currentEmail = new StringBuilder(initChar);
			for (int j = 0 ; j <= i; j++) {
				currentEmail.append(lastNames[j]);
			}
			currentEmail.append(EMAIL_DOMAIN);
			if (!exists(currentEmail)) { // +++ximprove: should return existent email
				return String.valueOf(currentEmail);
			}
		}

		/* This is in case all email trials failed */
		/* This, for sure, does not exists as includes the docket, which is unique */
		return defaultEmail;
	}

	private boolean exists(final StringBuilder email) {
		List<String> emails = jdbcTemplate.query(GET_EMAILS, emailRowMapper);
		return emails.contains(String.valueOf(email));
	}




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

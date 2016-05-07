package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.Authority;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.models.users.User;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Repository
public class UserJdbcDao implements UserDao {
	private static final String EMAIL_DOMAIN = "@bait.edu.ar";

	private static final String USERS_TABLE = "users";
	private static final String ADDRESS_TABLE = "address";
	private static final String ROLE_AUTHORITIES_TABLE = "role_authorities";

	private static final String USER__DNI_COLUMN = "dni";
	private static final String USER__FIRST_NAME_COLUMN = "first_name";
	private static final String USER__LAST_NAME_COLUMN = "last_name";
	private static final String USER__GENRE_COLUMN = "genre";
	private static final String USER__BIRTHDAY_COLUMN = "birthday";
	private static final String USER__EMAIL_COLUMN = "email";
	private static final String USER__PWD_COLUMN = "password";
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

	private static final String ROLE_AUTHORITIES__ROLE_COLUMN = "role";
	private static final String ROLE_AUTHORITIES__AUTHORITY_COLUMN = "authority";

	private static final String AND = "AND";
	private static final String EVERYTHING = "*";
	private static final String EQUALS = "=";
	private static final String GIVEN_PARAMETER = "?";
	private static final String LIMIT_1 = "LIMIT 1";

	private static final String GET_BY_DNI;
	private static final String GET_ROLE;
	private static final String UPDATE_PASSWORD;
	private static final String RESET_PASSWORD;
	private static final String GET_EMAILS;
	private static final String DELETE_USER;
	private static final String GET_ROLE_AUTHORITIES;
	private static final String UPDATE_USER;

	static {
/*		GET_BY_DNI =
				select(EVERYTHING) +
						from(USERS_TABLE) +
						where(USER__DNI_COLUMN, EQUALS, GIVEN_PARAMETER);*/
		GET_BY_DNI =
				select(EVERYTHING) +
						from(leftJoin(USERS_TABLE, ADDRESS_TABLE, USER__DNI_COLUMN, ADDRESS__DNI_COLUMN)) +
						where (tableCol(USERS_TABLE, USER__DNI_COLUMN), EQUALS, GIVEN_PARAMETER, LIMIT_1);

		GET_ROLE =
				select(USER__ROLE_COLUMN) +
						from(USERS_TABLE) +
						where(USER__DNI_COLUMN, EQUALS, GIVEN_PARAMETER);
		GET_EMAILS =
				select(USER__EMAIL_COLUMN)
				+ from(USERS_TABLE);

		DELETE_USER =
				deleteFrom(USERS_TABLE)
						+ where(USER__DNI_COLUMN, EQUALS, GIVEN_PARAMETER);

		UPDATE_PASSWORD =
				update(USERS_TABLE) +
						set(USER__PWD_COLUMN, GIVEN_PARAMETER) +
						where(tableCol(USERS_TABLE, USER__DNI_COLUMN), EQUALS, GIVEN_PARAMETER
						,AND, tableCol(USERS_TABLE, USER__PWD_COLUMN), EQUALS, GIVEN_PARAMETER);

		RESET_PASSWORD =
				update(USERS_TABLE) +
						set(USER__PWD_COLUMN, DEFAULT) +
						where(tableCol(USERS_TABLE, USER__DNI_COLUMN), EQUALS, GIVEN_PARAMETER);


		UPDATE_USER =
				update(USERS_TABLE) +
						set(USER__FIRST_NAME_COLUMN, GIVEN_PARAMETER
						, 	USER__LAST_NAME_COLUMN, GIVEN_PARAMETER
						,	USER__EMAIL_COLUMN,	GIVEN_PARAMETER
						,	USER__BIRTHDAY_COLUMN, GIVEN_PARAMETER
						,	USER__GENRE_COLUMN, GIVEN_PARAMETER
						) +
						where(USER__DNI_COLUMN, EQUALS, GIVEN_PARAMETER);

		GET_ROLE_AUTHORITIES =
				select(ROLE_AUTHORITIES__AUTHORITY_COLUMN) +
						from(ROLE_AUTHORITIES_TABLE) +
						where(ROLE_AUTHORITIES__ROLE_COLUMN, EQUALS, GIVEN_PARAMETER);

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
						USER__GENRE_COLUMN, USER__BIRTHDAY_COLUMN, USER__EMAIL_COLUMN, USER__ROLE_COLUMN);
		this.addressInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(ADDRESS_TABLE);
	}
	/* /Constructors */


	/* Public Methods */
	@Override
	public <V extends User, T extends User.Builder<V,T>> V getByDni(final int dni, final User.Builder<V, T> builder) {
		final BuilderRowMapper<V, T> builderRowMapper = new BuilderRowMapper<V, T>(Objects.requireNonNull(builder));

		final List<User.Builder<V, T>> userBuilders = jdbcTemplate.query(GET_BY_DNI, builderRowMapper, dni);
		if (userBuilders == null || userBuilders.isEmpty()) {
			return null;
		}

		final User.Builder<V, T> userBuilder = userBuilders.get(0);

		final List<Role> roles = getRole(dni);
		if (roles != null && !roles.isEmpty()) {
			final Role role = roles.get(0);
			final List<Authority> authorities = getAuthorities(role);
			userBuilder.role(role).authorities(authorities);
		}

		return userBuilder.build();
	}

	private List<Authority> getAuthorities(final Role role) {
		final RowMapper<Authority> authoritiesRowMapper = ((rs, rowNum) -> new Authority(rs.getString(ROLE_AUTHORITIES__AUTHORITY_COLUMN)));

		assert (role != null);
		final List<Authority> authorities =
				jdbcTemplate.query(GET_ROLE_AUTHORITIES, authoritiesRowMapper, role.originalString());

		return authorities == null ? new LinkedList<>() : authorities;
	}

	@Override
	public List<Role> getRole(final int requestedDni) {
		final RowMapper<Role> getRoleRowMapper = ((rs, rowNum) ->  {
			final String roleString = rs.getString(USER__ROLE_COLUMN);

			final Role role;
			if (roleString != null) {
				role = Role.valueOf(roleString);
			} else {
				role = null;
			}

			return role;
		});

		final List<Role> roles = jdbcTemplate.query(GET_ROLE, getRoleRowMapper, requestedDni);

		return roles;
	}

	@Override
	public Result create(User user, final Role role) {
		final Map<String, Object> userArgs = new HashMap<>();

		userArgs.put(USER__DNI_COLUMN, user.getDni());
		userArgs.put(USER__FIRST_NAME_COLUMN, user.getFirstName());
		userArgs.put(USER__LAST_NAME_COLUMN, user.getLastName());

		userArgs.put(USER__GENRE_COLUMN, user.getGenre().name());
		userArgs.put(USER__BIRTHDAY_COLUMN, user.getBirthday());
		userArgs.put(USER__EMAIL_COLUMN, createEmail(user.getDni(), user.getFirstName(),
				user.getLastName()));
		if (role == null) {
			return null;
		}
		userArgs.put(USER__ROLE_COLUMN, role.originalString());
		try {
			userInsert.execute(userArgs);
		}catch(DuplicateKeyException e){
			return Result.USER_EXISTS_DNI;
		} catch (DataAccessException e) {
			return Result.ERROR_UNKNOWN;
		}

		createAddress(user);
		return Result.OK;
	}

	@Override
	public Result delete(final Integer dni) {
		int rowsAffected;

		try {
			rowsAffected = jdbcTemplate.update(DELETE_USER, dni);
		} catch(DataAccessException e) {
			return Result.ERROR_UNKNOWN;
		}

		return rowsAffected == 1 ? Result.OK : Result.USER_NOT_EXISTS;
	}

	@Override
	public Result changePassword(final int dni, final String prevPassword, final String newPassword) {
		final int rowsAffected;
		try {
			rowsAffected = jdbcTemplate.update(UPDATE_PASSWORD, newPassword, dni, prevPassword);
		} catch (DataAccessException e) {
			return Result.ERROR_UNKNOWN;
		}

		return rowsAffected == 1 ? Result.OK : Result.INVALID_INPUT_PARAMETERS;
	}

	@Override
	public Result update(Integer dni, User user) {
		int rowsAffected;
		Date birthday = null;
		final String genre = user.getGenre().name();

		if(user.getBirthday() != null) {
			birthday = Date.valueOf(user.getBirthday());
		}

		try {
			rowsAffected = jdbcTemplate.update(UPDATE_USER,
					user.getFirstName(),
					user.getLastName(),
					user.getEmail(),
					birthday,
					genre,
					dni);
		} catch (final DataIntegrityViolationException e) {
			return Result.INVALID_INPUT_PARAMETERS;
		} catch(final DataAccessException e) {
			return Result.ERROR_UNKNOWN;
		}

		return rowsAffected == 1 ? Result.OK : Result.ERROR_UNKNOWN;
	}

	@Override
	public Result resetPassword(final Integer dni) {
		final int rowsAffected;

		try {
			rowsAffected = jdbcTemplate.update(RESET_PASSWORD, dni);
		} catch (DataAccessException e) {
			return Result.ERROR_UNKNOWN;
		}
		return rowsAffected == 1 ? Result.OK : Result.DNI_NOT_EXISTS;
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

	private static class BuilderRowMapper<V extends User, T extends User.Builder<V,T>> implements RowMapper<User.Builder<V, T>> {
		private final User.Builder<V, T> builder;

		private BuilderRowMapper(final User.Builder<V, T> builder) {
			this.builder = builder;
		}

		@Override
		public User.Builder<V, T> mapRow(final ResultSet resultSet, final int rowNum) throws SQLException {
			final String firstName = WordUtils.capitalizeFully(resultSet.getString(USER__FIRST_NAME_COLUMN));
			final String lastName = WordUtils.capitalizeFully(resultSet.getString(USER__LAST_NAME_COLUMN));
			final Date birthdayDate = resultSet.getDate(USER__BIRTHDAY_COLUMN);
			final LocalDate birthday;

			if (birthdayDate != null) {
				birthday = birthdayDate.toLocalDate();
			} else {
				birthday = null;
			}

			final String genreString = resultSet.getString(USER__GENRE_COLUMN);
			final User.Genre genre;
			if (genreString != null) {
				genre = User.Genre.valueOf(resultSet.getString(USER__GENRE_COLUMN));
			} else {
				genre = null;
			}

			String email = resultSet.getString(USER__EMAIL_COLUMN);

			final String country = WordUtils.capitalizeFully(resultSet.getString(ADDRESS__COUNTRY_COLUMN));
			final String city = WordUtils.capitalizeFully(resultSet.getString(ADDRESS__CITY_COLUMN));
			final String neighborhood = WordUtils.capitalizeFully(resultSet.getString(ADDRESS__NEIGHBORHOOD_COLUMN));
			final String street = WordUtils.capitalizeFully(resultSet.getString(ADDRESS__STREET_COLUMN));
			Integer number = resultSet.getInt(ADDRESS__NUMBER_COLUMN);
			if (resultSet.wasNull()) {
				number = null;
			}
			Integer floor = resultSet.getInt(ADDRESS__FLOOR_COLUMN);
			if (resultSet.wasNull()) {
				floor = null;
			}
			String door = resultSet.getString(ADDRESS__DOOR_COLUMN);
			if (door != null) {
				door = door.toUpperCase();
			}
			Long telephone = resultSet.getLong(ADDRESS__TELEPHONE_COLUMN);
			if (resultSet.wasNull()) {
				telephone = null;
			}
			Integer zipCode = resultSet.getInt(ADDRESS__ZIP_CODE_COLUMN);
			if (resultSet.wasNull()) {
				zipCode = null;
			}
			final Address.Builder addressBuilder = new Address.Builder(country, city, neighborhood, street, number);
			addressBuilder.floor(floor).door(door).telephone(telephone).zipCode(zipCode);
			final Address address = addressBuilder.build();

			final String password = resultSet.getString(USER__PWD_COLUMN);

			builder.firstName(firstName)
					.lastName(lastName)
					.genre(genre)
					.birthday(birthday)
					.email(email)
					.address(address)
					.password(password);
			return builder;
		}
	}

	private static String deleteFrom(String tableName) {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("DELETE FROM ");
		stringBuilder.append(tableName);
		return stringBuilder.toString();
	}

	private static String select(final String... cols) {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SELECT ");
		buildSentence(stringBuilder, cols);
		return stringBuilder.toString();
	}

	private static String update(final String table) {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("UPDATE ");
		buildSentence(stringBuilder, table);
		return stringBuilder.toString();
	}

	private static String from(final String... cols) {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("FROM ");
		buildSentence(stringBuilder, cols);
		return stringBuilder.toString();
	}


	private static String set(final String... cols) {
		int i = 0;
		final int lCols = cols.length;
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("SET ");
		for(String col : cols) {
			stringBuilder.append(col);
			if(i % 2 == 0) {
				stringBuilder.append(EQUALS);
			} else if(i < lCols -1) {
				stringBuilder.append(" ,");
			}
			i++;
		}
		stringBuilder.append(" ");
		return stringBuilder.toString();
	}

	private static String where(final String... cols) {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("WHERE ");
		for (String col : cols) {
			stringBuilder.append(col);
			stringBuilder.append(" ");
		}
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

	private static String leftJoin(final String t1, final String t2, final String c1, final String c2) {
		return t1 + " LEFT JOIN " + t2 + " ON " + t1 + "." + c1 + " = " + t2 + "." + c2;
	}

	private static String tableCol(final String table, final String column) {
		return table + "." + column;
	}
}

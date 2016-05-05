package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AdminDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.shared.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* +++xcheck: all methods */
@Repository
public class AdminJdbcDao implements AdminDao {

	/* TABLE NAMES */
	private static final String ADMIN_TABLE = "admin";

	/* COLS NAMES */
	private static final String ADMIN__DNI_COLUMN = "dni";

	/* POSTGRESQL WILDCARDS */
	private static final String AND = "AND";
	private static final String EVERYTHING = "*";
	private static final String EQUALS = "=";
	private static final String GIVEN_PARAMETER = "?";

//    private static final String GET_ADMINS;
	private static final String GET_BY_DNI;
	private static final String GET_ADMIN_DNI_LIST;

	static {
		GET_BY_DNI =
				select(ADMIN__DNI_COLUMN)
						+ from(ADMIN_TABLE)
						+ where(ADMIN__DNI_COLUMN, EQUALS, GIVEN_PARAMETER);

		GET_ADMIN_DNI_LIST =
				select(ADMIN__DNI_COLUMN) +
						from(ADMIN_TABLE);

	}

	private final JdbcTemplate jdbcTemplate;

	private final RowMapper<Admin> adminRowMapper = (resultSet, rowNumber) -> {
		final int dni = resultSet.getInt(ADMIN__DNI_COLUMN);

		return new Admin.Builder(dni).build();
	};

	private SimpleJdbcInsert adminInsert;

	@Autowired
	private UserDao userDao;

	@Autowired
	public AdminJdbcDao(final DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		adminInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(ADMIN_TABLE);
	}


	@Override
	public List<Admin> getAllAdmins() {
		final RowMapper<Integer> adminDniListRowMapper = ((rs, rowNum) -> rs.getInt(ADMIN__DNI_COLUMN));

		final List<Integer> adminDniList = jdbcTemplate.query(GET_ADMIN_DNI_LIST, adminDniListRowMapper);
		final List<Admin> admins = new LinkedList<>();
		Admin admin;
		Admin.Builder adminBuilder;
		for (Integer dni : adminDniList) {
			adminBuilder = new Admin.Builder(dni);
			admin = userDao.getByDni(dni, adminBuilder);
			if (admin != null) {
				admins.add(admin);
			}
		}

		return admins;
	}

	@Override
	public Result create(Admin admin) {
		userDao.create(admin, Role.ADMIN);

		final int rowsAffected;
		final Map<String, Object> adminArgs = new HashMap<>();

		adminArgs.put(ADMIN__DNI_COLUMN, admin.getDni());

		try {
			rowsAffected = adminInsert.execute(adminArgs);
		} catch (DuplicateKeyException e) {
			return Result.ADMIN_EXISTS_DNI;
		} catch (DataAccessException e) {
			return Result.ERROR_UNKNOWN;
		}
		return rowsAffected == 1 ? Result.OK : Result.ERROR_UNKNOWN;
	}

	@Override
	public Admin getByDni(int dni) {
		if (!isAdmin(dni)) {
			return null;
		}
		final Admin.Builder adminBuilder = new Admin.Builder(dni);
		return userDao.getByDni(dni, adminBuilder);
	}

    /* Private Methods */
	private boolean isAdmin(final int dni) {
		final List<Admin> admins = jdbcTemplate.query(GET_BY_DNI, adminRowMapper, dni);
		return !(admins == null || admins.isEmpty());
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

	private static String leftJoin(final String t1, final String t2, final String c1, final String c2) {
		return t1 + " LEFT JOIN " + t2 + " ON " + t1 + "." + c1 + " = " + t2 + "." + c2;
	}

	private static String leftJoinSpecial(final String concat, final String t1, final String t2, final String c1, final String c2) {
		return concat + " LEFT JOIN " + t2 + " ON " + t1 + "." + c1 + " = " + t2 + "." + c2;
	}

	private static String join(final String t1, final String t2, final String c1, final String c2) {
		return t1 + " JOIN " + t2 + " ON " + t1 + "." + c1 + " = " + t2 + "." + c2;
	}

	private static String tableCol(final String table, final String column) {
		return table + "." + column;
	}
}

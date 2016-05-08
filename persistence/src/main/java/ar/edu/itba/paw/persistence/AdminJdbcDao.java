package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AdminDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.models.users.User;
import ar.edu.itba.paw.shared.AdminFilter;
import ar.edu.itba.paw.shared.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
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
	private static final String ROLE_AUTHORITIES_TABLE = "role_authorities";

	/* COLS NAMES */
	private static final String ADMIN__DNI_COLUMN = "dni";
	private static final String ROLE_AUTHORITIES__ROLE_COLUMN = "role";
	private static final String ROLE_AUTHORITIES__AUTHORITY_COLUMN = "authority";

	/* POSTGRESQL WILDCARDS */
	private static final String AND = "AND";
	private static final String EVERYTHING = "*";
	private static final String EQUALS = "=";
	private static final String GIVEN_PARAMETER = "?";

//    private static final String GET_ADMINS;
	private static final String GET_BY_DNI;
	private static final String GET_ADMIN_DNI_LIST;
	private static final String DELETE_ADMIN;
	private static final String DISABLE_ADD_INSCRIPTION;
	private static final String DISABLE_DELETE_INSCRIPTION;

	static {
		GET_BY_DNI =
				select(ADMIN__DNI_COLUMN)
						+ from(ADMIN_TABLE)
						+ where(ADMIN__DNI_COLUMN, EQUALS, GIVEN_PARAMETER);

		GET_ADMIN_DNI_LIST =
				select(ADMIN__DNI_COLUMN) +
						from(ADMIN_TABLE);

        DELETE_ADMIN =
                deleteFrom(ADMIN_TABLE)
                        + where(ADMIN__DNI_COLUMN, EQUALS, GIVEN_PARAMETER);

		DISABLE_ADD_INSCRIPTION =
				deleteFrom(ROLE_AUTHORITIES_TABLE) + " " + where(ROLE_AUTHORITIES__ROLE_COLUMN, EQUALS, "'STUDENT'") + AND +
						" " + ROLE_AUTHORITIES__AUTHORITY_COLUMN + EQUALS + "'ADD_INSCRIPTION'";

		DISABLE_DELETE_INSCRIPTION =
				deleteFrom(ROLE_AUTHORITIES_TABLE) + " " + where(ROLE_AUTHORITIES__ROLE_COLUMN, EQUALS, "'STUDENT'") + AND +
						" " + ROLE_AUTHORITIES__AUTHORITY_COLUMN + EQUALS + "'DELETE_INSCRIPTION'";
	}

	private final JdbcTemplate jdbcTemplate;

	private final RowMapper<Admin> adminRowMapper = (resultSet, rowNumber) -> {
		final int dni = resultSet.getInt(ADMIN__DNI_COLUMN);

		return new Admin.Builder(dni).build();
	};

	private SimpleJdbcInsert adminInsert;
	private SimpleJdbcInsert roleAuthoritiesInsert;

	@Autowired
	private UserDao userDao;

	@Autowired
	public AdminJdbcDao(final DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		adminInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(ADMIN_TABLE);
		roleAuthoritiesInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(ROLE_AUTHORITIES_TABLE).
				usingColumns(ROLE_AUTHORITIES__ROLE_COLUMN, ROLE_AUTHORITIES__AUTHORITY_COLUMN);
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

	/* +++xcheck: try to remove */
	private static final String USERS_TABLE = "users";
	private static final String USER__DNI_COLUMN = "dni";
	private static final String USER__FIRST_NAME_COLUMN = "first_name";
	private static final String USER__LAST_NAME_COLUMN = "last_name";
	private static final String USER__GENRE_COLUMN = "genre";

    @Override
    public List<Admin> getByFilter(AdminFilter adminFilter) {
        QueryFilter queryFilter = new QueryFilter();

        if (adminFilter != null) {
            queryFilter.filterByDni(adminFilter);
            queryFilter.filterByFirstName(adminFilter);
            queryFilter.filterByLastName(adminFilter);
            queryFilter.filterByGenre(adminFilter);
        }

	    final RowMapper<Admin> getByFilterRowMapper = ((rs, rowNum) -> {
		    final Admin.Builder adminBuilder = new Admin.Builder(rs.getInt(USER__DNI_COLUMN));
		    adminBuilder
				    .firstName(rs.getString(USER__FIRST_NAME_COLUMN))
				    .lastName(rs.getString(USER__LAST_NAME_COLUMN));
		    return adminBuilder.build();
	    });
	    return jdbcTemplate.query(queryFilter.getQuery(), getByFilterRowMapper, queryFilter.getFilters().toArray());
    }

    @Override
    public Result deleteAdmin(Integer dni) {
        int adminRowsAffected;

        try {
            adminRowsAffected = jdbcTemplate.update(DELETE_ADMIN, dni);
        } catch (DataAccessException dae) {
			dae.printStackTrace();
			return Result.ERROR_UNKNOWN;
        }
        return adminRowsAffected == 1 ? Result.OK : Result.ERROR_UNKNOWN;
    }

	@Override
	public Result disableAddInscriptions() {
		int rowsAffected;

		try {
			rowsAffected = jdbcTemplate.update(DISABLE_ADD_INSCRIPTION);
		} catch (DataAccessException dae) {
			return Result.ERROR_UNKNOWN;
		}
		return rowsAffected == 1 ? Result.OK : Result.ERROR_UNKNOWN;
	}

	@Override
	public Result disableDeleteInscriptions() {
		int rowsAffected;

		try {
			rowsAffected = jdbcTemplate.update(DISABLE_DELETE_INSCRIPTION);
		} catch (DataAccessException dae) {
			return Result.ERROR_UNKNOWN;
		}
		return rowsAffected == 1 ? Result.OK : Result.ERROR_UNKNOWN;
	}

	@Override
	public Result enableAddInscriptions() {
		final int rowsAffected;
		final Map<String, Object> roleAuthoritiesArgs = new HashMap<>();

		roleAuthoritiesArgs.put(ROLE_AUTHORITIES__ROLE_COLUMN, "STUDENT");
		roleAuthoritiesArgs.put(ROLE_AUTHORITIES__AUTHORITY_COLUMN, "ADD_INSCRIPTION");

		try {
			rowsAffected = roleAuthoritiesInsert.execute(roleAuthoritiesArgs);
		} catch (DuplicateKeyException e) {
			return Result.ADMIN_ALREADY_ENABLED_INSCRIPTIONS;
		} catch (DataAccessException e) {
			return Result.ERROR_UNKNOWN;
		}
		return rowsAffected == 1 ? Result.OK : Result.ERROR_UNKNOWN;
	}

	@Override
	public Result enableDeleteInscriptions() {
		final int rowsAffected;
		final Map<String, Object> roleAuthoritiesArgs = new HashMap<>();

		roleAuthoritiesArgs.put(ROLE_AUTHORITIES__ROLE_COLUMN, "STUDENT");
		roleAuthoritiesArgs.put(ROLE_AUTHORITIES__AUTHORITY_COLUMN, "DELETE_INSCRIPTION");

		try {
			rowsAffected = roleAuthoritiesInsert.execute(roleAuthoritiesArgs);
		} catch (DuplicateKeyException e) {
			return Result.ADMIN_ALREADY_ENABLED_INSCRIPTIONS;
		} catch (DataAccessException e) {
			return Result.ERROR_UNKNOWN;
		}
		return rowsAffected == 1 ? Result.OK : Result.ERROR_UNKNOWN;
	}

	@Override
	public boolean isInscriptionEnabled() {
		Integer addInscriptionEnabled = jdbcTemplate.queryForObject("SELECT count (*) FROM " + ROLE_AUTHORITIES_TABLE + " WHERE " +
				ROLE_AUTHORITIES__ROLE_COLUMN + " = 'STUDENT' AND " + ROLE_AUTHORITIES__AUTHORITY_COLUMN + " = "
				+ "'ADD_INSCRIPTION'", Integer.class);

		Integer deleteInscriptionEnabled = jdbcTemplate.queryForObject("SELECT count (*) FROM " + ROLE_AUTHORITIES_TABLE + " WHERE " +
				ROLE_AUTHORITIES__ROLE_COLUMN + " = 'STUDENT' AND " + ROLE_AUTHORITIES__AUTHORITY_COLUMN + " = "
				+ "'DELETE_INSCRIPTION'", Integer.class);

		return (addInscriptionEnabled == 1 && deleteInscriptionEnabled == 1);
	}

	/* Private Static Methods */
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

	private static String from(final String... cols) {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("FROM ");
		buildSentence(stringBuilder, cols);
		return stringBuilder.toString();
	}

	private static String where(final String... cols) {
		final StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" WHERE ");
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

	/*  +++xcheck: @Gonza: try to generalize this and use a User Filter
		+++xremove: Genre filter
	 */
    private static class QueryFilter {
		private static final String FILTER_QUERY;
		static {
			FILTER_QUERY =
					select(EVERYTHING) +
							from(join(USERS_TABLE, ADMIN_TABLE, USER__DNI_COLUMN, ADMIN__DNI_COLUMN));
		}

        private static final String WHERE = " WHERE ";
        private static final String AND = " AND ";
        private static final String ILIKE = " ILIKE ? ";
        private static final String EQUAL = " = ? ";

        private static final String FILTER_DNI = "CAST(" + ADMIN_TABLE + "." + ADMIN__DNI_COLUMN + " AS TEXT) ";
        private static final String FILTER_NAME_FIRST = USER__FIRST_NAME_COLUMN;
        private static final String FILTER_NAME_LAST = USER__LAST_NAME_COLUMN;
        private static final String FILTER_GENRE = USER__GENRE_COLUMN;

        private final StringBuffer query = new StringBuffer(FILTER_QUERY);
        private boolean filterApplied = false;
        private final List<String> filters;

        private final FilterQueryMapper filterBySubWord = (filter, filterName) -> {
            if(filter != null && !filter.toString().equals("")) {
                String escapedFilter = escapeFilter(filter);
                String stringFilter = "%" + escapedFilter + "%";
                appendFilter(filterName, stringFilter);
            }
        };

        private final FilterQueryMapper filterByExactWord = (filter, filterName) -> {
            if(filter != null && !filter.toString().equals("")) {
                String escapedFilter = escapeFilter(filter);
                String stringFilter = escapedFilter;
                appendFilter(filterName, stringFilter);
            }
        };

        private QueryFilter() {
            filters = new LinkedList<>();
        }

        public void filterByDni(final AdminFilter adminFilter) {
            filterBySubWord.filter(adminFilter.getDni(), FILTER_DNI + ILIKE);
        }

        public void filterByFirstName(final AdminFilter adminFilter) {
            filterBySubWord.filter(adminFilter.getFirstName(), FILTER_NAME_FIRST + ILIKE);
        }

        public void filterByLastName(final AdminFilter adminFilter) {
            filterBySubWord.filter(adminFilter.getLastName(), FILTER_NAME_LAST + ILIKE);
        }

        public void filterByGenre(AdminFilter studentFilter) {
            filterByExactWord.filter(studentFilter.getGenre(), FILTER_GENRE + EQUAL);
        }

        public List<String> getFilters() {
            return filters;
        }

        public String getQuery() {
            return query.toString();
        }

        private void appendFilterConcatenation() {
            if(!filterApplied) {
                filterApplied = true;
                query.append(WHERE);
            } else {
                query.append(AND);
            }
        }

        private String escapeFilter(final Object filter) {
            return filter.toString().replace("%", "\\%").replace("_", "\\_");
        }

        private void appendFilter(final String filter, final String stringFilter) {
            appendFilterConcatenation();
            query.append(filter);
            filters.add(stringFilter);
        }

        private interface FilterQueryMapper {
            void filter(final Object filter, final String filterName);
        }
    }
}

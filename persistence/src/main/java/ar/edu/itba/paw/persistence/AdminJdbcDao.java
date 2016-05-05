package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AdminDao;
import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.models.users.User;
import ar.edu.itba.paw.shared.AdminFilter;
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
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class AdminJdbcDao implements AdminDao {

    /* TABLE NAMES */
    private static final String ADMIN_TABLE = "admin";
    private static final String USER_TABLE = "users";
    private static final String ADDRESS_TABLE = "address";

    /* /TABLE NAMES */

    /* COLS NAMES */
    private static final String ADMIN__DNI_COLUMN = "dni";

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
    /* COLS NAMES */

    /* POSTGRESQL WILDCARDS */
    private static final String AND = "AND";
    private static final String EVERYTHING = "*";
    private static final String EQUALS = "=";
    private static final String GIVEN_PARAMETER = "?";
    /* /POSTGRESQL WILDCARDS */

    private static final String GET_ADMINS_WITH_ADDRESS;
    private static final String GET_BY_DNI;
    private static final String GET_ADMINS;

    static {
        GET_ADMINS =
                select(EVERYTHING)
                + from(join(USER_TABLE, ADMIN_TABLE, USER__DNI_COLUMN, ADMIN__DNI_COLUMN));

        GET_ADMINS_WITH_ADDRESS =
                select(EVERYTHING)
                + from(ADMIN_TABLE, USER_TABLE, ADDRESS_TABLE)
                + where(tableCol(ADMIN_TABLE, ADMIN__DNI_COLUMN), EQUALS, tableCol(USER_TABLE, USER__DNI_COLUMN)
                        ,AND,
                        tableCol(USER_TABLE, USER__DNI_COLUMN), EQUALS, tableCol(ADDRESS_TABLE, ADDRESS__DNI_COLUMN));
        GET_BY_DNI =
                select(EVERYTHING)
                        + from(
                            leftJoinSpecial(
                                    join(ADMIN_TABLE, USER_TABLE, ADMIN__DNI_COLUMN, USER__DNI_COLUMN),
                                    ADMIN_TABLE, ADDRESS_TABLE, ADMIN__DNI_COLUMN, ADDRESS__DNI_COLUMN)
                        )
                        + where(tableCol(ADMIN_TABLE, ADMIN__DNI_COLUMN), EQUALS, GIVEN_PARAMETER);

    }

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Admin> adminRowMapper = (resultSet, rowNumber) -> {
        final int dni = resultSet.getInt(ADMIN__DNI_COLUMN);
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

        final String password = resultSet.getString(USER__PASSWORD_COLUMN);

        final Admin.Builder studentBuilder = new Admin.Builder(dni);
        studentBuilder.firstName(firstName).lastName(lastName).
                genre(genre).birthday(birthday).email(email).address(address).password(password);

        return studentBuilder.build();
    };

    private SimpleJdbcInsert adminInsert;

    @Autowired
    public AdminJdbcDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        adminInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(ADMIN_TABLE);
    }


    @Override
    public List<Admin> getAllAdmins() {
        List<Admin> admins = jdbcTemplate.query(GET_ADMINS_WITH_ADDRESS, adminRowMapper);

        return admins;
    }

    @Override
    public Result create(Admin admin) {
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
        final List<Admin> admin = jdbcTemplate.query(GET_BY_DNI, adminRowMapper, dni);

        return admin.isEmpty() ? null : admin.get(0);
    }

    @Override
    public List<Admin> getByFilter(AdminFilter adminFilter) {
        QueryFilter queryFilter = new QueryFilter();

        if (queryFilter != null) {
            queryFilter.filterByDni(adminFilter);
            queryFilter.filterByFirstName(adminFilter);
            queryFilter.filterByLastName(adminFilter);
            queryFilter.filterByGenre(adminFilter);
        }

        return jdbcTemplate.query(queryFilter.getQuery(), adminRowMapper, queryFilter.getFilters().toArray());
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

    private static class QueryFilter {
        private static final String WHERE = " WHERE ";
        private static final String AND = " AND ";
        private static final String ILIKE = " ILIKE ? ";
        private static final String EQUAL = " = ? ";

        private static final String FILTER_DNI = "CAST(" + ADMIN__DNI_COLUMN + " AS TEXT) ";
        private static final String FILTER_NAME_FIRST = USER__FIRST_NAME_COLUMN;
        private static final String FILTER_NAME_LAST = USER__LAST_NAME_COLUMN;
        private static final String FILTER_GENRE = USER__GENRE_COLUMN;

        private final StringBuffer query = new StringBuffer(GET_ADMINS);
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

        public void filterByDni(final AdminFilter courseFilter) {
            filterBySubWord.filter(courseFilter.getDni(), FILTER_DNI + ILIKE);
        }

        public void filterByFirstName(final AdminFilter courseFilter) {
            filterBySubWord.filter(courseFilter.getFirstName(), FILTER_NAME_FIRST + ILIKE);
        }

        public void filterByLastName(final AdminFilter courseFilter) {
            filterBySubWord.filter(courseFilter.getLastName(), FILTER_NAME_LAST + ILIKE);
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

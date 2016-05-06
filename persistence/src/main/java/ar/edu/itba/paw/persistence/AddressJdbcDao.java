package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AddressDao;
import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.shared.Result;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class AddressJdbcDao implements AddressDao {
    private static final String ADDRESS_TABLE = "address";



    /* POSTGRESQL WILDCARDS */
    private static final String AND = "AND";
    private static final String EVERYTHING = "*";
    private static final String EQUALS = "=";
    private static final String GIVEN_PARAMETER = "?";
    /* /POSTGRESQL WILDCARDS */

    private static final String COUNT_ADDRESS;

    static {
        COUNT_ADDRESS =

    }

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert addressInsert;

    public AddressJdbcDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.addressInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(ADDRESS_TABLE);
    }

    @Override
    public boolean hasAddress(Integer dni) {

    }

    @Override
    public Result createAddress(Integer dni, Address address) {
        return null;
    }

    @Override
    public Result updateAddress(Integer dni, Address address) {
        return null;
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
}

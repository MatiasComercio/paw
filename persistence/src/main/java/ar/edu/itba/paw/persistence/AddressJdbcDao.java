package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.AddressDao;
import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.shared.Result;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AddressJdbcDao implements AddressDao {

    private static final String ADDRESS_TABLE = "address";

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

    /* POSTGRESQL WILDCARDS */
    private static final String AND = "AND";
    private static final String EVERYTHING = "*";
    private static final String EQUALS = "=";
    private static final String GIVEN_PARAMETER = "?";
    /* /POSTGRESQL WILDCARDS */

    private static final String COUNT_ADDRESS;

    static {
        COUNT_ADDRESS =
            select(count(EVERYTHING))
                + from(ADDRESS_TABLE)
                + where(ADDRESS__DNI_COLUMN, EQUALS, GIVEN_PARAMETER);
    }

    private final JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert addressInsert;

    public AddressJdbcDao(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.addressInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(ADDRESS_TABLE);
    }

    @Override
    public boolean hasAddress(Integer dni) {
        final Object[] dniObject = new Object[]{dni};
        final int count = jdbcTemplate.queryForObject(COUNT_ADDRESS, dniObject, Integer.class);

        return count == 1;
    }

    @Override
    public Result createAddress(Integer dni, Address address) {
        Map<String, Object> addressArgs = new HashMap<>();

        addressArgs.put(ADDRESS__DNI_COLUMN, dni);
        addressArgs.put(ADDRESS__COUNTRY_COLUMN, WordUtils.capitalize(address.getCountry().toLowerCase()));
        addressArgs.put(ADDRESS__CITY_COLUMN, WordUtils.capitalize(address.getCity()).toLowerCase());
        addressArgs.put(ADDRESS__NEIGHBORHOOD_COLUMN, WordUtils.capitalize(address.getNeighborhood()).toLowerCase());
        addressArgs.put(ADDRESS__STREET_COLUMN, WordUtils.capitalize(address.getStreet()).toLowerCase());
        addressArgs.put(ADDRESS__NUMBER_COLUMN,address.getNumber());
        addressArgs.put(ADDRESS__FLOOR_COLUMN, address.getFloor());
        addressArgs.put(ADDRESS__DOOR_COLUMN, address.getDoor());
        addressArgs.put(ADDRESS__TELEPHONE_COLUMN, address.getTelephone());
        addressArgs.put(ADDRESS__ZIP_CODE_COLUMN, address.getZipCode());

        /**
         * +++xcheck not checking if the address was correctly inserted
         */
        addressInsert.executeAndReturnKey(addressArgs);

        return Result.OK;
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

    private static String count(final String col) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("COUNT( ");
        stringBuilder.append(col);
        stringBuilder.append(")");
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

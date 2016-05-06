package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.shared.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class AddressJdbcDaoTest {
    private static final String ADMIN_TABLE = "admin";
    private static final String USER_TABLE = "users";
    private static final String ADDRESS_TABLE = "address";
    private static final String ROLE_TABLE = "role";

    private static final String USER__DNI_COLUMN = "dni";
    private static final String USER__FIRST_NAME_COLUMN = "first_name";
    private static final String USER__LAST_NAME_COLUMN = "last_name";
    private static final String USER__GENRE_COLUMN = "genre";
    private static final String USER__BIRTHDAY_COLUMN = "birthday";
    private static final String USER__EMAIL_COLUMN = "email";
    private static final String USER__PASSWORD_COLUMN = "password";
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

    private static final String ROLE__ROLE_COLUMN = "role";


    private static final int DNI_1 = 12345678;
    private static final String FIRST_NAME_1 = "MaTías NIColas";
    private static final String LAST_NAME_1 = "Comercio vazquez";
    private static final String EMAIL_1 = "mcomercio@bait.edu.ar";
    private static final String PASSWORD_1 = "pass1";

    private static final int DNI_2 = 87654321;
    private static final String FIRST_NAME_2 = "BreNda LiHuéN ";
    private static final String LAST_NAME_2 = "MaYan";
    private static final String EMAIL_2 = "blihuen@bait.edu.ar";
    private static final String PASSWORD_2 = "pass2";

    private static final String ADDRESS__COUNTRY_VALUE = "ArgEnTINA";
    private static final String ADDRESS__CITY_VALUE = "BS. as.";
    private static final String ADDRESS__NEIGHBORHOOD_VALUE = "MonTECasTro";
    private static final String ADDRESS__STREET_VALUE = "santo TOME";
    private static final int ADDRESS__NUMBER_VALUE = 0;
    private static final int ADDRESS__FLOOR_VALUE = 15;
    private static final String ADDRESS__DOOR_VALUE = "Zav";
    private static final long ADDRESS__TELEPHONE_VALUE = 45666666;
    private static final int ADDRESS__ZIP_CODE_VALUE = 1418;

    private static final String ADDRESS__COUNTRY_EXPECTED = "Argentina";
    private static final String ADDRESS__CITY_EXPECTED = "Bs. As.";
    private static final String ADDRESS__NEIGHBORHOOD_EXPECTED = "Montecastro";
    private static final String ADDRESS__STREET_EXPECTED = "Santo Tome";
    private static final Integer ADDRESS__NUMBER_EXPECTED = 0;
    private static final Integer ADDRESS__FLOOR_EXPECTED = 15;
    private static final String ADDRESS__DOOR_EXPECTED = "ZAV";
    private static final Long ADDRESS__TELEPHONE_EXPECTED = 45666666L;
    private static final Integer ADDRESS__ZIP_CODE_EXPECTED = 1418;

    private static final String ROLE_1 = "ADMIN";
    private static final String ROLE_2 = "STUDENT";

    /* POSTGRESQL WILDCARDS */
    private static final String AND = "AND";
    private static final String EVERYTHING = "*";
    private static final String EQUALS = "=";
    private static final String GIVEN_PARAMETER = "?";
    /* /POSTGRESQL WILDCARDS */

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AddressJdbcDao addressJdbcDao;

    private SimpleJdbcInsert roleInsert;
    private SimpleJdbcInsert userInsert;
    private SimpleJdbcInsert addressInsert;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(dataSource);

        roleInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(ROLE_TABLE);
        userInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(USER_TABLE);
        addressInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(ADDRESS_TABLE);

        JdbcTestUtils.deleteFromTables(jdbcTemplate, ADDRESS_TABLE);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, USER_TABLE);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, ROLE_TABLE);


        final Map<String, Object> roleArgs = new HashMap<>();
        final Map<String, Object> userArgs = new HashMap<>();

        roleArgs.put(ROLE__ROLE_COLUMN, ROLE_1);
        roleInsert.execute(roleArgs);
        roleArgs.put(ROLE__ROLE_COLUMN, ROLE_2);
        roleInsert.execute(roleArgs);

        /* Insertion of User */
        userArgs.put(USER__DNI_COLUMN, DNI_1);
        userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
        userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
        userArgs.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
        userArgs.put(USER__PASSWORD_COLUMN, PASSWORD_1);
        userArgs.put(USER__ROLE_COLUMN, ROLE_1);
        userInsert.execute(userArgs);
    }

    @Test
    public void hasAddress() {
        Map<String, Object> addressArgs = new HashMap<>();
        Boolean hasAddress;

        /**
         * User that does not have an address
         */
        hasAddress = addressJdbcDao.hasAddress(DNI_1);
        assertFalse(hasAddress);

        addressArgs.put(ADDRESS__DNI_COLUMN, DNI_1);
        addressArgs.put(ADDRESS__CITY_COLUMN, ADDRESS__CITY_VALUE);
        addressArgs.put(ADDRESS__COUNTRY_COLUMN, ADDRESS__COUNTRY_VALUE);
        addressArgs.put(ADDRESS__DNI_COLUMN, DNI_1);
        addressArgs.put(ADDRESS__NEIGHBORHOOD_COLUMN, ADDRESS__NEIGHBORHOOD_VALUE);
        addressArgs.put(ADDRESS__STREET_COLUMN, ADDRESS__STREET_VALUE);
        addressArgs.put(ADDRESS__NUMBER_COLUMN, ADDRESS__NUMBER_VALUE);
        addressInsert.execute(addressArgs);

        hasAddress = addressJdbcDao.hasAddress(DNI_1);
        assertTrue(hasAddress);
    }

    @Test
    public void createAddress() {
        Result result;
        Address address = new Address.Builder(
                ADDRESS__COUNTRY_VALUE,
                ADDRESS__CITY_VALUE,
                ADDRESS__NEIGHBORHOOD_VALUE,
                ADDRESS__STREET_VALUE,
                ADDRESS__NUMBER_VALUE
                ).build();

        /**
         * Insert address for non existent user
         */
        result = addressJdbcDao.createAddress(DNI_2, address);
        assertEquals(Result.DNI_NOT_EXISTS, result);

        /**
         * Insert address for existent user
         */
        result = addressJdbcDao.createAddress(DNI_1, address);
        assertEquals(Result.OK, result);
    }

    public void updateAddress() {

    }
}

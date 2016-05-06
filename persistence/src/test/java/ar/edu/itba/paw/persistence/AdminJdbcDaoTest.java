package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.users.Admin;
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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class AdminJdbcDaoTest {
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

    private static final int DNI_1 = 12345678;
    private static final String FIRST_NAME_1 = "MaTías NIColas";
    private static final String FIRST_NAME_1_EXPECTED = "Matías Nicolas";
    private static final String LAST_NAME_1 = "Comercio vazquez";
    private static final String LAST_NAME_1_EXPECTED = "Comercio Vazquez";
    private static final String GENRE_1 = "M";
    private static final String GENRE_1_EXPECTED = "Male";
    private static final LocalDate BIRTHDAY_1 = LocalDate.parse("1994-08-17");
    private static final String EMAIL_1 = "mcomercio@bait.edu.ar";

    private static final int DNI_2 = 87654321;
    private static final String FIRST_NAME_2 = "BreNda LiHuéN ";
    private static final String FIRST_NAME_2_EXPECTED = "Brenda Lihuén";
    private static final String LAST_NAME_2 = "MaYan";
    private static final String LAST_NAME_2_EXPECTED = "Mayan";
    private static final String EMAIL_2 = "blihuen@bait.edu.ar";

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AdminJdbcDao adminJdbcDao;

    private SimpleJdbcInsert userInsert;
    private SimpleJdbcInsert adminInsert;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(dataSource);

        userInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(USER_TABLE);
        adminInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(ADMIN_TABLE);

        JdbcTestUtils.deleteFromTables(jdbcTemplate, ADMIN_TABLE);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, USER_TABLE);
    }

    @Test
    public void getAllAdmins() {
        final Map<String, Object> adminArgs = new HashMap<>();
        final Map<String, Object> userArgs = new HashMap<>();
        List<Admin> admins;

        /**
         *  Table admin with no admin
         */
        admins = adminJdbcDao.getAllAdmins();
        assertNotNull(admins);
        assertTrue(admins.isEmpty());

        /**
         *  Table with one admin
         */
        /* Insertion of User */
        userArgs.put(USER__DNI_COLUMN, DNI_1);
        userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
        userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
        userArgs.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
        userInsert.execute(userArgs);

        /* Insertion of Admin */
        adminArgs.put(ADMIN__DNI_COLUMN, DNI_1);
        adminInsert.execute(adminArgs);

        admins = adminJdbcDao.getAllAdmins();
        assertNotNull(admins);
        assertFalse(admins.isEmpty()); /* +++xtofinish This depends on whether there is an address associated */
        assertEquals(1, admins.size());
    }

    @Test
    public void create() {
        final Map<String, Object> userArgs = new HashMap<>();
        Result result;
        Admin admin;

        userArgs.put(USER__DNI_COLUMN, DNI_1);
        userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
        userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
        userArgs.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
        userInsert.execute(userArgs);

        /** 
         * Insert non existant admin 
         */
        admin = new Admin.Builder(DNI_1)
                .build();

        result = adminJdbcDao.create(admin);
        assertEquals(Result.OK, result);

        /** 
         * Insert duplicate admin
         */
        result = adminJdbcDao.create(admin);
        assertEquals(Result.ADMIN_EXISTS_DNI, result);
    }

    @Test
    public void getByDni() {

    }

    @Test
    public void getByFilter() {

    }

    @Test
    public void deleteAdmin() {

    }
}
package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.shared.AdminFilter;
import ar.edu.itba.paw.shared.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Filter;
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
    private static final String ROLE_TABLE = "role";
    private static final String AUTHORITY_TABLE = "authority";
    private static final String ROLE_AUTHORITIES_TABLE = "role_authorities";


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
    private static final String AUTHORITY__AUTHORITY_COLUMN = "authority";
    private static final String ROLE_AUTHORITIES__ROLE_COLUMN = "role";
    private static final String ROLE_AUTHORITIES__AUTHORITY_COLUMN = "authority";

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
    private static final String PASSWORD_1 = "pass1";

    private static final Integer DNI_INVALID = -1;


    private static final int DNI_2 = 87654321;
    private static final String FIRST_NAME_2 = "BreNda LiHuéN ";
    private static final String FIRST_NAME_2_EXPECTED = "Brenda Lihuén";
    private static final String LAST_NAME_2 = "MaYan";
    private static final String LAST_NAME_2_EXPECTED = "Mayan";
    private static final String EMAIL_2 = "blihuen@bait.edu.ar";
    private static final String PASSWORD_2 = "pass2";

    private static final String ROLE_1 = "ADMIN";
    private static final String ROLE_2 = "STUDENT";

    private static final String AUTHORITY_1 = "ADD_INSCRIPTION";
    private static final String AUTHORITY_2 = "DELETE_INSCRIPTION";

    private static final String DISABLE_ADD_INSCRIPTION = "DELETE FROM " + ROLE_AUTHORITIES_TABLE + " WHERE " +
    ROLE_AUTHORITIES__ROLE_COLUMN + " = " + "'STUDENT'" + " AND " + ROLE_AUTHORITIES__AUTHORITY_COLUMN
    + " = " + "'ADD_INSCRIPTION'";

    private static final String DISABLE_DELETE_INSCRIPTION = "DELETE FROM " + ROLE_AUTHORITIES_TABLE + " WHERE " +
    ROLE_AUTHORITIES__ROLE_COLUMN + " = " + "'STUDENT'" + " AND " + ROLE_AUTHORITIES__AUTHORITY_COLUMN
    + " = " + "'DELETE_INSCRIPTION'";

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AdminJdbcDao adminJdbcDao;

    private SimpleJdbcInsert userInsert;
    private SimpleJdbcInsert adminInsert;
    private SimpleJdbcInsert roleInsert;
    private SimpleJdbcInsert authorityInsert;
    private SimpleJdbcInsert roleAuthoritiesInsert;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(dataSource);

        roleInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(ROLE_TABLE);
        authorityInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(AUTHORITY_TABLE)
            .usingColumns(AUTHORITY__AUTHORITY_COLUMN);
        userInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(USER_TABLE);
        adminInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(ADMIN_TABLE);
        roleAuthoritiesInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(ROLE_AUTHORITIES_TABLE).
                usingColumns(ROLE_AUTHORITIES__ROLE_COLUMN, ROLE_AUTHORITIES__AUTHORITY_COLUMN);


        JdbcTestUtils.deleteFromTables(jdbcTemplate, ADMIN_TABLE);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, USER_TABLE);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, ROLE_TABLE);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, AUTHORITY_TABLE);

        final Map<String, Object> roleArgs = new HashMap<>();
        final Map<String, Object> authorityArgs = new HashMap<>();
        final Map<String, Object> userArgs = new HashMap<>();
        final Map<String, Object> adminArgs = new HashMap<>();

        roleArgs.put(ROLE__ROLE_COLUMN, ROLE_1);
        roleInsert.execute(roleArgs);
        roleArgs.put(ROLE__ROLE_COLUMN, ROLE_2);
        roleInsert.execute(roleArgs);

        authorityArgs.put(AUTHORITY__AUTHORITY_COLUMN, AUTHORITY_1);
        authorityInsert.execute(authorityArgs);
        authorityArgs.put(AUTHORITY__AUTHORITY_COLUMN, AUTHORITY_2);
        authorityInsert.execute(authorityArgs);

        /* Insertion of User */
        userArgs.put(USER__DNI_COLUMN, DNI_1);
        userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
        userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
        userArgs.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
        userArgs.put(USER__PASSWORD_COLUMN, PASSWORD_1);
        userArgs.put(USER__ROLE_COLUMN, ROLE_1);
        userInsert.execute(userArgs);
        userArgs.put(USER__DNI_COLUMN, DNI_2);
        userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_2.toLowerCase());
        userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_2.toLowerCase());
        userArgs.put(USER__EMAIL_COLUMN, EMAIL_2.toLowerCase());
        userArgs.put(USER__PASSWORD_COLUMN, PASSWORD_2);
        userArgs.put(USER__ROLE_COLUMN, ROLE_2);
        userInsert.execute(userArgs);
    }

    @Test
    public void getAllAdmins() {
        final Map<String, Object> adminArgs = new HashMap<>();
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
        /* Insertion of Admin */
        adminArgs.put(ADMIN__DNI_COLUMN, DNI_1);
        adminInsert.execute(adminArgs);

        admins = adminJdbcDao.getAllAdmins();
        assertNotNull(admins);
        assertFalse(admins.isEmpty());
        assertEquals(1, admins.size());
    }

    @Test
    public void create() {
        Result result;
        Admin admin;

        /** 
         * Insert non existant admin 
         */
        admin = new Admin.Builder(DNI_1)
                .firstName(FIRST_NAME_1)
                .lastName(LAST_NAME_1)
                .email(EMAIL_1)
                .build();

        result = adminJdbcDao.create(admin);
        assertEquals(Result.OK, result);

        Admin adminCreated = adminJdbcDao.getByDni(DNI_1);
        assertEquals(DNI_1, adminCreated.getDni());
        assertEquals(FIRST_NAME_1_EXPECTED, adminCreated.getFirstName());
        assertEquals(LAST_NAME_1_EXPECTED, adminCreated.getLastName());
        assertEquals(EMAIL_1, adminCreated.getEmail());

        /** 
         * Insert duplicate admin
         */
        result = adminJdbcDao.create(admin);
        assertEquals(Result.ADMIN_EXISTS_DNI, result);

        /**
         * Insert admin with invalid DNI
         */
        admin = new Admin.Builder(DNI_INVALID)
                .build();

        result = adminJdbcDao.create(admin);
        assertEquals(Result.ERROR_UNKNOWN, result);
    }

    @Test
    public void getByDni() {
        final Map<String, Object> adminArgs = new HashMap<>();
        Admin admin;

        /**
         * Get non existant admin
         */
        admin = adminJdbcDao.getByDni(DNI_1);
        assertNull(admin);

        /**
         * Get existant admin
         */
        adminArgs.put(ADMIN__DNI_COLUMN, DNI_1);
        adminInsert.execute(adminArgs);

        admin = adminJdbcDao.getByDni(DNI_1);
        assertNotNull(admin);
        assertEquals(DNI_1, admin.getDni());
        assertEquals(FIRST_NAME_1_EXPECTED, admin.getFirstName());
        assertEquals(LAST_NAME_1_EXPECTED, admin.getLastName());
        assertEquals(EMAIL_1, admin.getEmail());
    }

    /**
     * +++xcheck BIG PROBLEM:
     * HSQLDB doesn't recognize TEXT type, so I can't cast
     * the DNI
     * HSQLDB doesn't recognize ILIKE statement
     */
    @Test
    public void getByFilter() {
        final Map<String, Object> adminArgs = new HashMap<>();
        AdminFilter adminFilter;
        List<Admin> admins;

        adminArgs.put(ADMIN__DNI_COLUMN, DNI_1);
        adminInsert.execute(adminArgs);

        adminArgs.put(ADMIN__DNI_COLUMN, DNI_2);
        adminInsert.execute(adminArgs);

        /**
         * Filter with no params (2 matches)
         */
        adminFilter = new AdminFilter.AdminFilterBuilder()
                .build();

        admins = adminJdbcDao.getByFilter(adminFilter);
        assertNotNull(admins);
        assertEquals(2, admins.size());

//        /**
//         * Filter by DNI (1 match)
//         */
//        adminFilter = new AdminFilter.AdminFilterBuilder()
//                .dni(DNI_1)
//                .build();
//
//        admins = adminJdbcDao.getByFilter(adminFilter);
//        assertNotNull(admins);
//        assertEquals(1, admins.size());
//
//        /**
//         * Filter by First Name (1 match)
//         */
//        adminFilter = new AdminFilter.AdminFilterBuilder()
//                .firstName(FIRST_NAME_1)
//                .build();
//        admins = adminJdbcDao.getByFilter(adminFilter);
//        assertNotNull(admins);
//        assertEquals(1, admins.size());
//
//        /**
//         * Filter by First Name and DNI (0 matches)
//         */
//        adminFilter = new AdminFilter.AdminFilterBuilder()
//                .dni(DNI_1)
//                .firstName(FIRST_NAME_2)
//                .build();
//        admins = adminJdbcDao.getByFilter(adminFilter);
//        assertNotNull(admins);
//        assertEquals(0, admins.size());
//
//        /**
//         * Filter by First Name and DNI (1 match)
//         */
//        adminFilter = new AdminFilter.AdminFilterBuilder()
//                .dni(DNI_1)
//                .firstName(FIRST_NAME_1)
//                .build();
//        admins = adminJdbcDao.getByFilter(adminFilter);
//        assertNotNull(admins);
//        assertEquals(1, admins.size());

    }

    @Test
    public void deleteAdmin() {
        Result result;
        final Map<String, Object> adminArgs = new HashMap<>();

        /**
         * Delete non existant admin
         */
        result = adminJdbcDao.deleteAdmin(DNI_1);
        assertEquals(Result.ERROR_UNKNOWN, result);

        /**
         * Delete an existant admin and check that it was deleted
         */
        adminArgs.put(ADMIN__DNI_COLUMN, DNI_1);
        adminInsert.execute(adminArgs);

        result = adminJdbcDao.deleteAdmin(DNI_1);
        assertEquals(Result.OK, result);

        Admin adminDeleted = adminJdbcDao.getByDni(DNI_1);
        assertNull(adminDeleted);
    }

    @Test
    public void testEnableAddInscription(){

        //Make sure AddInscription authority is disabled for students
        jdbcTemplate.update(DISABLE_ADD_INSCRIPTION);

        adminJdbcDao.enableAddInscriptions();

        //Check authority is enabled
        Integer addInscriptionEnabled = jdbcTemplate.queryForObject("SELECT count (*) FROM " + ROLE_AUTHORITIES_TABLE + " WHERE " +
                ROLE_AUTHORITIES__ROLE_COLUMN + " = 'STUDENT' AND " + ROLE_AUTHORITIES__AUTHORITY_COLUMN + " = "
                + "'ADD_INSCRIPTION'", Integer.class);
        assertEquals(addInscriptionEnabled, Integer.valueOf(1));
        //assertEquals(addInscriptionEnabled, 1);

    }

    @Test
    public void testDisableAddInscription(){

        //Make sure AddInscription authority is enabled for students
        final Map<String, Object> roleAuthoritiesArgs = new HashMap<>();
        roleAuthoritiesArgs.put(ROLE_AUTHORITIES__ROLE_COLUMN, "STUDENT");
        roleAuthoritiesArgs.put(ROLE_AUTHORITIES__AUTHORITY_COLUMN, "ADD_INSCRIPTION");
        roleAuthoritiesInsert.execute(roleAuthoritiesArgs);

        adminJdbcDao.disableAddInscriptions();

        //Check authority is disabled
        Integer addInscriptionEnabled = jdbcTemplate.queryForObject("SELECT count (*) FROM " + ROLE_AUTHORITIES_TABLE + " WHERE " +
                ROLE_AUTHORITIES__ROLE_COLUMN + " = 'STUDENT' AND " + ROLE_AUTHORITIES__AUTHORITY_COLUMN + " = "
                + "'ADD_INSCRIPTION'", Integer.class);

        assertEquals(addInscriptionEnabled, Integer.valueOf(0));

    }

    @Test
    public void testEnableDeleteInscription(){

        //Make sure DeleteInscription authority is disabled for students
        jdbcTemplate.update(DISABLE_DELETE_INSCRIPTION);

        adminJdbcDao.enableDeleteInscriptions();

        //Check authority is enabled
        Integer deleteInscriptionEnabled = jdbcTemplate.queryForObject("SELECT count (*) FROM " + ROLE_AUTHORITIES_TABLE + " WHERE " +
                ROLE_AUTHORITIES__ROLE_COLUMN + " = 'STUDENT' AND " + ROLE_AUTHORITIES__AUTHORITY_COLUMN + " = "
                + "'DELETE_INSCRIPTION'", Integer.class);

        assertEquals(deleteInscriptionEnabled, Integer.valueOf(1));

    }

    @Test
    public void testDisableDeleteInscription(){

        //Make sure DeleteInscription authority is enabled for students
        final Map<String, Object> roleAuthoritiesArgs = new HashMap<>();
        roleAuthoritiesArgs.put(ROLE_AUTHORITIES__ROLE_COLUMN, "STUDENT");
        roleAuthoritiesArgs.put(ROLE_AUTHORITIES__AUTHORITY_COLUMN, "DELETE_INSCRIPTION");
        roleAuthoritiesInsert.execute(roleAuthoritiesArgs);

        adminJdbcDao.disableDeleteInscriptions();

        //Check authority is disabled
        Integer deleteInscriptionEnabled = jdbcTemplate.queryForObject("SELECT count (*) FROM " + ROLE_AUTHORITIES_TABLE + " WHERE " +
                ROLE_AUTHORITIES__ROLE_COLUMN + " = 'STUDENT' AND " + ROLE_AUTHORITIES__AUTHORITY_COLUMN + " = "
                + "'DELETE_INSCRIPTION'", Integer.class);

        assertEquals(deleteInscriptionEnabled, Integer.valueOf(0));

    }

    @Test
    public void testIsInscriptionEnabled(){
	/* Test when both authorities are disabled */

        //Disable Add and Delete Inscription Authorities
        jdbcTemplate.update(DISABLE_ADD_INSCRIPTION);
        jdbcTemplate.update(DISABLE_DELETE_INSCRIPTION);

        assertEquals(adminJdbcDao.isInscriptionEnabled(), false);

	/* Test when only AddInscription authority is enabled */

        //Make sure AddInscription authority is enabled for students
        final Map<String, Object> roleAuthoritiesArgs = new HashMap<>();
        roleAuthoritiesArgs.put(ROLE_AUTHORITIES__ROLE_COLUMN, "STUDENT");
        roleAuthoritiesArgs.put(ROLE_AUTHORITIES__AUTHORITY_COLUMN, "ADD_INSCRIPTION");
        roleAuthoritiesInsert.execute(roleAuthoritiesArgs);

        assertEquals(adminJdbcDao.isInscriptionEnabled(), false);

	/* Test when only DeleteInscription authority is enabled */

        //Disable Add Inscription Authorities
        jdbcTemplate.update(DISABLE_ADD_INSCRIPTION);

        //Make sure AddInscription authority is enabled for students
        final Map<String, Object> roleAuthoritiesArgs2 = new HashMap<>();
        roleAuthoritiesArgs.put(ROLE_AUTHORITIES__ROLE_COLUMN, "STUDENT");
        roleAuthoritiesArgs.put(ROLE_AUTHORITIES__AUTHORITY_COLUMN, "DELETE_INSCRIPTION");
        roleAuthoritiesInsert.execute(roleAuthoritiesArgs);

        assertEquals(adminJdbcDao.isInscriptionEnabled(), false);

	/* Test when both authorities are enabled */

        //Make sure AddInscription authority is enabled for students
        final Map<String, Object> roleAuthoritiesArgs3 = new HashMap<>();
        roleAuthoritiesArgs.put(ROLE_AUTHORITIES__ROLE_COLUMN, "STUDENT");
        roleAuthoritiesArgs.put(ROLE_AUTHORITIES__AUTHORITY_COLUMN, "ADD_INSCRIPTION");
        roleAuthoritiesInsert.execute(roleAuthoritiesArgs);

        assertEquals(adminJdbcDao.isInscriptionEnabled(), true);

    }
}
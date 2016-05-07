package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.models.users.User;
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
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class UserJdbcDaoTest {
    private static final String ROLE_TABLE = "role";
    private static final String USERS_TABLE = "users";


    private static final String ROLE__ROLE_COLUMN = "role";

    private static final String USER__DNI_COLUMN = "dni";
    private static final String USER__FIRST_NAME_COLUMN = "first_name";
    private static final String USER__LAST_NAME_COLUMN = "last_name";
    private static final String USER__GENRE_COLUMN = "genre";
    private static final String USER__BIRTHDAY_COLUMN = "birthday";
    private static final String USER__EMAIL_COLUMN = "email";
    private static final String USER__PASSWORD_COLUMN = "password";
    private static final String USER__ROLE_COLUMN = "role";

    private static final String ROLE_1 = "ADMIN";
    private static final String ROLE_2 = "STUDENT";

    private static final int DNI_1 = 12345678;
    private static final String FIRST_NAME_1 = "MaTías NIColas";
    private static final String FIRST_NAME_1_EXPECTED = "Matías Nicolas";
    private static final String LAST_NAME_1 = "Comercio vazquez";
    private static final String LAST_NAME_1_EXPECTED = "Comercio Vazquez";
    private static final String EMAIL_1 = "mcomercio@bait.edu.ar";
    private static final String PASSWORD_1 = "pass1";
    private static final LocalDate BIRTHDAY_1 = LocalDate.parse("1994-08-17");
    private static final User.Genre GENRE_1 = User.Genre.M;
    private static final String GENRE_1_EXPECTED = "Male";

    private static final int DNI_2 = 87654321;
    private static final String FIRST_NAME_2 = "BreNda LiHuéN";
    private static final String FIRST_NAME_2_EXPECTED = "Brenda Lihuén";
    private static final String LAST_NAME_2 = "MaYan";
    private static final String LAST_NAME_2_EXPECTED = "Mayan";
    private static final String EMAIL_2 = "blihuen@bait.edu.ar";
    private static final String PASSWORD_2 = "pass2";

    private static final String PASSWORD_DEFAULT = "pass";


    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserJdbcDao userJdbcDao;

    private SimpleJdbcInsert roleInsert;
    private SimpleJdbcInsert userInsert;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(dataSource);

        roleInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(ROLE_TABLE);
        userInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(USERS_TABLE);

        JdbcTestUtils.deleteFromTables(jdbcTemplate, USERS_TABLE);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, ROLE_TABLE);

        /* Insertions */

        final Map<String, Object> roleArgs = new HashMap<>();

        roleArgs.put(ROLE__ROLE_COLUMN, ROLE_1);
        roleInsert.execute(roleArgs);
        roleArgs.put(ROLE__ROLE_COLUMN, ROLE_2);
        roleInsert.execute(roleArgs);
    }

    @Test
    public void update() {
        final Map<String, Object> userArgs = new HashMap<>();
        User user, userUpdated;
        Result result;

        userArgs.put(USER__DNI_COLUMN, DNI_1);
        userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
        userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
        userArgs.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
        userArgs.put(USER__PASSWORD_COLUMN, PASSWORD_1);
        userArgs.put(USER__ROLE_COLUMN, ROLE_1);
        userInsert.execute(userArgs);

        user = new Admin.Builder(DNI_1)
                .firstName(FIRST_NAME_2)
                .lastName(LAST_NAME_2)
                .email(EMAIL_1)
                .birthday(BIRTHDAY_1)
                .genre(GENRE_1)
                .build();

        /**
         * Update an invalid user and check that it fails
         */
        result = userJdbcDao.update(DNI_2, user);
        assertEquals(Result.ERROR_UNKNOWN, result);

        /**
         * Update a valid user and check that they updated ok
         */
        result = userJdbcDao.update(DNI_1, user);
        assertEquals(Result.OK, result);

        System.out.println(user.getFirstName());

        Admin.Builder adminBuilder = new Admin.Builder(DNI_1);
        userUpdated = userJdbcDao.getByDni(DNI_1, adminBuilder);
        assertEquals(FIRST_NAME_2_EXPECTED, userUpdated.getFirstName());
        assertEquals(LAST_NAME_2_EXPECTED, userUpdated.getLastName());
        assertEquals(EMAIL_1, userUpdated.getEmail());
        assertEquals(BIRTHDAY_1, userUpdated.getBirthday());
        assertEquals(GENRE_1, userUpdated.getGenre());

        /**
         * Update with not all the parameters filled
         */
        user = new Admin.Builder(DNI_1)
                .firstName(FIRST_NAME_1)
                .build();

        result = userJdbcDao.update(DNI_1, user);
        assertEquals(Result.OK, result);


    }

    @Test
    public void changePassword() {
        final Map<String, Object> userArgs = new HashMap<>();
        Result result;

        userArgs.put(USER__DNI_COLUMN, DNI_1);
        userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
        userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
        userArgs.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
        userArgs.put(USER__PASSWORD_COLUMN, PASSWORD_1);
        userArgs.put(USER__ROLE_COLUMN, ROLE_1);
        userInsert.execute(userArgs);

        /**
         * Change password with an invalid DNI
         */
        result = userJdbcDao.changePassword(DNI_2, PASSWORD_1, PASSWORD_2);
        assertEquals(Result.INVALID_INPUT_PARAMETERS, result);

        /**
         * Change password with an invalid password
         */
        result = userJdbcDao.changePassword(DNI_1, PASSWORD_2, PASSWORD_1);
        assertEquals(Result.INVALID_INPUT_PARAMETERS, result);

        /**
         * Change password with a valid DNI and check that it was changed
         */
        result = userJdbcDao.changePassword(DNI_1, PASSWORD_1, PASSWORD_2);
        assertEquals(Result.OK, result);

        Admin.Builder adminBuilder = new Admin.Builder(DNI_1);
        User user = userJdbcDao.getByDni(DNI_1, adminBuilder);
        assertEquals(PASSWORD_2, user.getPassword());
    }

    @Test
    public void resetPassword() {
        final Map<String, Object> userArgs = new HashMap<>();
        Result result;

        userArgs.put(USER__DNI_COLUMN, DNI_1);
        userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
        userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
        userArgs.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
        userArgs.put(USER__PASSWORD_COLUMN, PASSWORD_1);
        userArgs.put(USER__ROLE_COLUMN, ROLE_1);
        userInsert.execute(userArgs);

        /**
         * Reset password from a valid user and check that it is the default
         */
        result = userJdbcDao.resetPassword(DNI_1);
        assertEquals(Result.OK, result);

        Admin.Builder adminBuilder = new Admin.Builder(DNI_1);
        User user = userJdbcDao.getByDni(DNI_1, adminBuilder);
        assertEquals(PASSWORD_DEFAULT, user.getPassword());

        /**
         * Reset password from an invalid user
         */
        result = userJdbcDao.resetPassword(DNI_2);
        assertEquals(Result.INVALID_INPUT_PARAMETERS, result);
    }

}

package ar.edu.itba.paw.persistence;

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
    private static final String LAST_NAME_1 = "Comercio vazquez";
    private static final String EMAIL_1 = "mcomercio@bait.edu.ar";
    private static final String PASSWORD_1 = "pass1";

    private static final int DNI_2 = 87654321;
    private static final String FIRST_NAME_2 = "BreNda LiHuéN ";
    private static final String LAST_NAME_2 = "MaYan";
    private static final String EMAIL_2 = "blihuen@bait.edu.ar";
    private static final String PASSWORD_2 = "pass2";

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


        userArgs.put(USER__DNI_COLUMN, DNI_1);
        userArgs.put(USER__FIRST_NAME_COLUMN, FIRST_NAME_1.toLowerCase());
        userArgs.put(USER__LAST_NAME_COLUMN, LAST_NAME_1.toLowerCase());
        userArgs.put(USER__EMAIL_COLUMN, EMAIL_1.toLowerCase());
        userArgs.put(USER__PASSWORD_COLUMN, PASSWORD_1);
        userArgs.put(USER__ROLE_COLUMN, ROLE_1);
        userInsert.execute(userArgs);
    }

}

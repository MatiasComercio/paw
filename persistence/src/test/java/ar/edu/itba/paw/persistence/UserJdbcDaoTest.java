package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.persistence.TestConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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




}

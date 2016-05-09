package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@RunWith(Parameterized.class)
public class UserServiceImplTest {
    private static final int DOCKET_VALID = 7357;
    private static final int DOCKET_VALID_LIMIT = 1;
    private static final int DOCKET_INVALID_LIMIT = 0;
    private static final int DOCKET_INVALID = -7357;

    private static final int DNI_VALID = 7357;
    private static final int DNI_VALID_LIMIT = 1;
    private static final int DNI_INVALID_LIMIT = 0;
    private static final int DNI_INVALID = -7357;

    private static final String ROLE_1 = "ADMIN";
    private static final String ROLE_2 = "STUDENT";

    /* Mock objects */

    @Mock
    private UserDao userDao;

    /* // Mock objects */

    private UserServiceImpl userService;

    /* Parameter */

    @Parameter
    public int dni;

    @Parameter(value = 1)
    public Answer<List<Role>> roles;

    @Parameter(value = 2)
    public List<Role> expectedRoles;

    /* // Parameter */

    @Parameters
    public static Collection<Object[]> data() {
        Answer<List<Role>> roles1 = (invocation) -> {
            final List<Role> roles = new LinkedList<>();
            roles.add(Role.ADMIN);

            return roles;
        };

        List<Role> expectedRoles1 = new LinkedList<>();
        expectedRoles1.add(Role.ADMIN);

        Answer<List<Role>> rolesInvalid = (invocation) -> {
            final List<Role> roles = new LinkedList<>();

            return roles;
        };

        List<Role> expectedRolesInvalid = new LinkedList<>();

        return Arrays.asList(new Object[][] {
                { DNI_VALID, roles1, expectedRoles1 },
                { DNI_INVALID, rolesInvalid, expectedRolesInvalid }
        });
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl();
        userService.setUserDao(userDao);
    }


    @Test
    public void testGetRoles() {
        if(roles != null) {
            when(userDao.getRole(dni)).then(roles);
        }
        List<Role> roles = userService.getRole(dni);
        assertThat(roles, is(expectedRoles));
    }
}

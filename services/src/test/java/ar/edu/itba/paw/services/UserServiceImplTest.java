package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.Role;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
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
import java.util.List;

public class UserServiceImplTest {
    private static final int DOCKET_VALID = 7357;
    private static final int DOCKET_VALID_LIMIT = 1;
    private static final int DOCKET_INVALID_LIMIT = 0;
    private static final int DOCKET_INVALID = -7357;

    private static final int DNI_VALID = 7357;
    private static final int DNI_VALID_LIMIT = 1;
    private static final int DNI_INVALID_LIMIT = 0;
    private static final int DNI_INVALID = -7357;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                { /* PARAMS */ }
        });
    }

    @Mock
    private UserDao userDao;

    @Parameter(value = 1)
    public int dni;

    @Parameter(value = 2)
    public Answer<List<Role>> roles;


    @Parameter(value = 3)
    public Answer<List<Role>> expectedRoles;


    @Test
    public void testGetRoles() {
        if(roles != null) {
            when(userDao.getRole(dni)).then(roles);
        }

        List<Role> roles = userDao.getRole(dni);
        assertThat(roles, is(expectedRoles));
    }
}

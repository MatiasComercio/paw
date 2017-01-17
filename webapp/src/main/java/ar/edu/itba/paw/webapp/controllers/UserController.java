package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.webapp.forms.PasswordForm;
import ar.edu.itba.paw.webapp.forms.validators.PasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.*;

@Component
@Path("/users")
public class UserController {

  private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService us;

  @Autowired
  PasswordValidator passwordValidator;

  @POST
  @Path("/{dni}/password/change")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response usersPasswordChange(@PathParam("dni") final int dni,
                                      @Valid PasswordForm passwordForm) {
    passwordForm.setDni(dni);

    //TODO: add passwordConfirmation validation (the current one will not work)

    if(!us.changePassword(dni, passwordForm.getCurrentPassword(), passwordForm.getNewPassword())) {
      return status(Status.BAD_REQUEST).build();
    }

    return noContent().build();
  }

  @POST
  @Path("/{dni}/password/reset")
  public Response usersPasswordReset(@PathParam("dni") final int dni) {
    us.resetPassword(dni);

    return noContent().build();
  }

}

package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.webapp.forms.PasswordDTO;
import ar.edu.itba.paw.webapp.models.AuthoritiesDTO;
import ar.edu.itba.paw.webapp.models.AuthorityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.*;

@Component
@Path("/users")
public class UserController {

  private final static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  private static final Map<String, String> authoritiesMap =
          Arrays.stream(new String[][] {
                  // -- Roles

                  { "STUDENT", ""},
                  { "ADMIN", ""},

                  // -- StudentController

                  { "VIEW_STUDENTS", "/students"}, // Permit All
                  { "ADD_STUDENT", "/students"},
                  { "VIEW_STUDENT", "/students/{docket}"}, // Permit All
                  { "EDIT_STUDENT", "/students/{docket}"}, // Permit All
                  { "DELETE_STUDENT", "/students/{docket}"},

                  { "VIEW_INSCRIPTIONS", "/students/{docket}/courses"}, // Permit All
                  { "ADD_INSCRIPTION", "/students/{docket}/courses"}, // Permit All
                  { "DELETE_INSCRIPTION", "/students/{docket}/courses"}, // Permit All
                  //* { "VIEW_INSCRIPTIONS_AVAILABLE "/{docket}/courses/available" // Permit All

                  { "VIEW_GRADES", "/students/{docket}/grades"}, // Permit All
                  { "ADD_GRADE", "/students/{docket}/grades"},
                  { "EDIT_GRADE", "/students/{docket}/grades/{gradeId}"},

                  //* { "VIEW_FINAL_INSCRIPTIONS", "students/{docket}/finalInscriptions"}, // Permit All
                  //* {"VIEW_FINAL_INSCRIPTION", "students/{docket}/finalInscriptions/{finalInscriptionId}"}, // Permit All
                  { "ADD_FINAL_INSCRIPTION", "students/{docket}/finalInscriptions/{finalInscriptionId}"}, // Permit All
                  { "DELETE_FINAL_INSCRIPTION", "students/{docket}/finalInscriptions/{finalInscriptionId}"}, // Permit All
                  //* { "VIEW_FINAL_INSCRIPTIONS_AVAILABLE", "students/{docket}/finalInscriptions/available"}, // Permit All

                  // -- CourseController

                  { "VIEW_COURSES", "/courses"}, // Permit All
                  { "ADD_COURSE", "/courses"},
                  { "VIEW_COURSE", "/courses/{courseId}"}, // Permit All
                  { "EDIT_COURSE", "/courses/{courseId}"},
                  { "DELETE_COURSE", "/courses/{courseId}"},

                  //* {"VIEW_COURSE_STUDENTS", "/courses/{courseId}/students"} // Permit All
                  { "VIEW_STUDENTS_APPROVED", "/courses/{courseId}/students/passed"},

                  { "ADD_CORRELATIVE", "/courses/{courseId}/correlatives"},
                  { "DELETE_CORRELATIVE", "/courses/{courseId}/correlatives/{correlativeId}"},

                  //*{"VIEW_COURSE_FINAL_INSCRIPTIONS", "courses/finalInscriptions/{finalInscriptionId}"},
                  //*{"QUALIFY_COURSE_FINAL", "courses/finalInscriptions/{finalInscriptionId}/grades"},

                  // -- AdminController

                  { "VIEW_ADMINS", "/admins"},
                  { "ADD_ADMIN", "/admins"},
                  { "VIEW_ADMIN", "/admins/{dni}"},
                  { "EDIT_ADMIN", "/admins/{dni}"},
                  { "DELETE_ADMIN", "/admins/{dni}"},

                  { "DISABLE_INSCRIPTION", "/admins/inscriptions"},

                  // -- UserController

                  { "CHANGE_PASSWORD", "/users/{dni}/password/change"}, // Permit All
                  { "RESET_PASSWORD", "/users/{dni}/password/reset"},
          }).collect(Collectors.toMap(kv -> kv[0], kv -> kv[1]));

  @Autowired
  private UserService us;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response usersAuthorities(){
    List<String> actions = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(o -> o.toString()).collect(Collectors.toList());
    List<AuthorityDTO> authoritiesList = new LinkedList<>();

    for(String action: actions){
      authoritiesList.add(new AuthorityDTO(action, authoritiesMap.get(action)));
    }

    return ok().entity(new AuthoritiesDTO(authoritiesList)).build();
  }

  @POST
  @Path("/{dni}/password/change")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response usersPasswordChange(@PathParam("dni") final int dni,
                                      @Valid PasswordDTO passwordDTO) {
    passwordDTO.setDni(dni);

    if(!us.changePassword(dni, passwordDTO.getCurrentPassword(), passwordDTO.getNewPassword())) {
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

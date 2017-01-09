package ar.edu.itba.paw.webapp.controllers;


import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.StudentFilter;
import ar.edu.itba.paw.webapp.models.StudentIndexDTO;
import ar.edu.itba.paw.webapp.models.StudentShowDTO;
import ar.edu.itba.paw.webapp.models.StudentsList;
import ar.edu.itba.paw.webapp.models.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.*;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.*;

@Component
@Path("/students")
public class StudentController {

  /** Fields **/

  @Autowired
  private StudentService ss;

  @Autowired
  private DTOEntityMapper mapper;

  @Context
  private UriInfo uriInfo;

  /** API Methods **/

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response studentsIndex(
          @Min(value = 1, message = "docket must be greater than or equal to 1\n")
          @QueryParam("docket") final int docket,
          @Size(min=2, max=50, message = "firstName must have between 2 and 50 characters\n")
          @QueryParam("firstName") final String firstName,
          @NotNull(message = "lastName must not be null\n")
          @QueryParam("lastName") final String lastName){
    final StudentFilter studentFilter = new StudentFilter.StudentFilterBuilder()
            .docket(docket).firstName(firstName).lastName(lastName).build();

    final List<Student> students = ss.getByFilter(studentFilter);
    final List<StudentIndexDTO> studentList = students.stream()
            .map(student -> mapper.convertToStudentIndexDTO(student)).collect(Collectors.toList());

    return ok(new StudentsList(studentList)).build();
  }

  @GET
  @Path("/{docket}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response studentsShow(@PathParam("docket") final int docket) {
    final Student student = ss.getByDocket(docket);

    if(student == null) {
      return status(Status.NOT_FOUND).build();
    }
    final StudentShowDTO studentShowDTO = mapper.convertToStudentShowDTO(student);

    return ok(studentShowDTO).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response studentsNew(@Valid final UserDTO user) throws ValidationException{
    ss.create(mapper.convertToEntity(user));
    final Student student = ss.getByDni(user.getDni());
    final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(student.getDocket())).build();
    return created(uri).build();
  }

  @DELETE
  @Path("/{docket}")
  public Response studentsDestroy(@PathParam("docket") final int docket) {
    ss.deleteStudent(docket);
    return noContent().build();
  }

}

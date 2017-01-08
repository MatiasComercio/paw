package ar.edu.itba.paw.webapp.controllers;


import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.webapp.models.StudentIndexDTO;
import ar.edu.itba.paw.webapp.models.StudentShowDTO;
import ar.edu.itba.paw.webapp.models.StudentsList;
import ar.edu.itba.paw.webapp.models.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.Min;
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
  public Response studentsIndex(){
    final List<Student> students = ss.getByFilter(null);
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
    System.out.println("[HELLOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO]");
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

  @GET
  @Path("/validate")
  public Response validateStudent(
          @Size(min = 2, max = 25, message = "firstName Length should be between 2 and 25 character")
          @QueryParam("firstName") String firstName,

          @Size(min = 2, max = 25, message = "lastName Length should be between 2 and 25 character")
          @QueryParam("lastName") String lastName,

          @Min(value = 15, message = "age should not be less that 15")
          @QueryParam("age")
                  String age) throws ValidationException {

    String student = String.format("firstName: %s, lastName: %s, age: %s", firstName, lastName, age);
    String response = student;
    return Response.status(Response.Status.OK).entity(response).type(MediaType.TEXT_PLAIN).build();
  }

}

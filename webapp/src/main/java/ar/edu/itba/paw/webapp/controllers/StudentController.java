package ar.edu.itba.paw.webapp.controllers;


import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.StudentFilter;
import ar.edu.itba.paw.webapp.models.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

  private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

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
          @Min(value = 1)
          @QueryParam("docket") final Integer docket,
          @Size(min=2, max=50)
          @QueryParam("firstName") final String firstName,
          @Size(min=2, max=50)
          @QueryParam("lastName") final String lastName){
    final StudentFilter studentFilter = new StudentFilter.StudentFilterBuilder()
            .docket(docket).firstName(firstName).lastName(lastName).build();

    final List<Student> students = ss.getByFilter(studentFilter);
    final List<StudentIndexDTO> studentList = students.stream()
            .map(student -> mapper.convertToStudentIndexDTO(student)).collect(Collectors.toList());

    return ok(new StudentsList(studentList)).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response studentsNew(@Valid final UserNewDTO userNewDTO) throws ValidationException{
    ss.create(mapper.convertToStudent(userNewDTO));
    final Student student = ss.getByDni(userNewDTO.getDni());
    final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(student.getDocket())).build();
    return created(uri).build();
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
  @Path("/{docket}")
  public Response studentsUpdate(@PathParam("docket") final int docket,
                                 @Valid final StudentsUpdateDTO studentUpdateDTO) {

    final Student oldStudent = ss.getByDocket(docket);
    if(oldStudent == null) {
      return status(Status.NOT_FOUND).build();
    }

    final Student partialStudent = mapper.convertToStudent(studentUpdateDTO);
    ss.update(partialStudent, oldStudent);

    // TODO: Why return the resource location? Can the ID (docket) change?
    final URI uri = uriInfo.getAbsolutePathBuilder().build();
    return ok(uri).build();
  }

  @DELETE
  @Path("/{docket}")
  public Response studentsDestroy(@PathParam("docket") final int docket) {
    ss.deleteStudent(docket);
    return noContent().build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{docket}/address")
  public Response studentsAddressShow(@PathParam("docket") final int docket){
    final Student student = ss.getByDocket(docket);
    if(student == null || student.getAddress() == null) {
      return status(Status.NOT_FOUND).build();
    }
    final AddressDTO addressDTO = mapper.convertToAddressDTO(student.getAddress());
    return ok(addressDTO).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/{docket}/address")
  public Response studentAddressUpdate(
          @PathParam("docket") final int docket,
          @Valid AddressDTO addressDTO){
    final Student student = ss.getByDocket(docket);
    if(student == null){
      return status(Status.NOT_FOUND).build();
    }
    Address address = mapper.convertToAddress(addressDTO);
    address.setDni(student.getDni());
    student.setAddress(address);
    ss.editAddress(student, address);
    return ok().build();
  }

}

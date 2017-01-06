package ar.edu.itba.paw.webapp.controllers;


import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.webapp.models.StudentIndexDTO;
import ar.edu.itba.paw.webapp.models.StudentsList;
import ar.edu.itba.paw.webapp.models.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Path("/students")
public class StudentController {

  @Autowired
  private StudentService ss;

  @Autowired
  private DTOEntityMapper mapper;

  @Context
  private UriInfo uriInfo;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response studentsIndex(){
    List<Student> students = ss.getByFilter(null);
    List<StudentIndexDTO> studentList = students.stream()
            .map(student -> mapper.convertToDTO(student)).collect(Collectors.toList());

    return Response.ok(new StudentsList(studentList)).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response studentsNew(UserDTO user){
    ss.create(mapper.convertToEntity(user));
    Student student = ss.getByDni(user.getDni());

    final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(student.getDocket())).build();
    return Response.created(uri).build();
  }
}

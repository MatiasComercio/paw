package ar.edu.itba.paw.webapp.controllers;


import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.webapp.models.StudentIndexDTO;
import ar.edu.itba.paw.webapp.models.StudentsList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Path("/students")
public class StudentController {

  @Autowired
  private StudentService ss;

  @Autowired
  private DTOEntityMapper mapper;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response studentsIndex(){
    List<Student> students = ss.getByFilter(null);
    List<StudentIndexDTO> studentList = students.stream()
            .map(student -> mapper.convertToDTO(student)).collect(Collectors.toList());

    return Response.ok(new StudentsList(studentList)).build();
  }

}

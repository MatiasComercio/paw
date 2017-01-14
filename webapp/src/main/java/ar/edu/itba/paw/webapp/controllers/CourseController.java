package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.StudentFilter;
import ar.edu.itba.paw.webapp.models.CourseDTO;
import ar.edu.itba.paw.webapp.models.StudentIndexDTO;
import ar.edu.itba.paw.webapp.models.StudentsList;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.print.attribute.standard.Media;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

import static javax.ws.rs.core.Response.*;


@Component
@Path("/courses")
public class CourseController {

  private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

  /** Fields **/

  @Autowired
  private CourseService cs;

  @Autowired
  private DTOEntityMapper mapper;

  @GET
  @Path("/{courseId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response coursesShow(@PathParam("courseId") final String courseId) {
    final Course course = cs.getByCourseID(courseId);

    if(course == null) {
      return status(Status.NOT_FOUND).build();
    }

    final CourseDTO courseDTO = mapper.convertToCourseDTO(course);
    return ok(courseDTO).build();
  }

  @POST
  @Path("/{courseId}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response coursesUpdate(@PathParam("courseId") final String courseId,
                                @Valid final CourseDTO courseDTO) {
    if(cs.getByCourseID(courseId) == null) {
      return status(Status.NOT_FOUND).build();
    }
    System.out.println(courseDTO);
    final Course courseUpdated = mapper.convertToCourse(courseDTO);

    if(!cs.update(courseId, courseUpdated)) {
      return status(Status.BAD_REQUEST).build(); //TODO: Decide what to return
    }
    return ok().build();
  }

  @GET
  @Path("/{courseId}/students")
  @Produces(MediaType.APPLICATION_JSON)
  public Response coursesStudentsIndex(@PathParam("courseId") final String courseId,
                                       @Min(value = 1)
                                       @QueryParam("docket") final Integer docket,
                                       @Size(max=50)
                                       @QueryParam("firstName") final String firstName,
                                       @Size(max=50)
                                       @QueryParam("lastName") final String lastName) {
    final Course course = cs.getByCourseID(courseId);

    if(course == null) {
      return status(Status.NOT_FOUND).build();
    }
    final StudentFilter studentFilter = new StudentFilter.StudentFilterBuilder()
            .docket(docket).firstName(firstName).lastName(lastName).build();
    final List<Student> courseStudents = cs.getCourseStudents(courseId, studentFilter);

    final List<StudentIndexDTO> studentsList = courseStudents.stream().map(student -> mapper.convertToStudentIndexDTO(student)).collect(Collectors.toList());

    return ok(new StudentsList(studentsList)).build();
  }
}

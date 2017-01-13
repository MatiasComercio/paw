package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.webapp.models.CourseDTO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

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
  @Path("/{course_id}")
  public Response coursesShow(@PathParam("course_id") final String courseId) {
    final Course course = cs.getByCourseID(courseId);

    if(course == null) {
      return status(Status.NOT_FOUND).build();
    }

    final CourseDTO courseDTO = mapper.convertToCourseDTO(course);
    return ok(courseDTO).build();
  }
}

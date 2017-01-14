package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.webapp.models.CourseDTO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
}

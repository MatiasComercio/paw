package ar.edu.itba.paw.webapp.controllers;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.FinalInscription;
import ar.edu.itba.paw.models.Grade;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.StudentFilter;
import ar.edu.itba.paw.webapp.models.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
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
  private StudentService ss;

  @Autowired
  private DTOEntityMapper mapper;


  @Context
  private UriInfo uriInfo;

  /** API Methods **/

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response coursesIndex(@Size(max=5)
                               @QueryParam("courseId") final String courseId,
                               @Size(max=50)
                               @QueryParam("name") final String name) {
    final CourseFilter courseFilter = new CourseFilter.CourseFilterBuilder()
            .id(courseId)
            .keyword(name)
            .build();

    final List<Course> coursesFiltered = cs.getByFilter(courseFilter);
    final List<CourseDTO> coursesList = coursesFiltered.stream().map(course -> mapper.convertToCourseDTO(course)).collect(Collectors.toList());

    return ok(new CoursesList(coursesList)).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response coursesNew(@Valid final CourseDTO courseDTO) {
    final Course course = mapper.convertToCourse(courseDTO);

    if(!cs.create(course)) {
      return status(Status.BAD_REQUEST).build();
    }
    final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(course.getCourseId())).build();

    return created(uri).build();
  }

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
    final Course courseUpdated = mapper.convertToCourse(courseDTO);

    if(!cs.update(courseId, courseUpdated)) {
      return status(Status.BAD_REQUEST).build(); //TODO: Decide what to return
    }

    final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(courseUpdated.getCourseId())).build();

    return ok(uri).build();
  }

  @DELETE
  @Path("/{courseId}")
  public Response coursesDestroy(@PathParam("courseId") final String courseId) {
    if(!cs.deleteCourse(courseId)) {
      return Response.status(Status.CONFLICT).build(); //TODO: check what to return
    }

    return Response.noContent().build();
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

  //TODO: It seems that it never returned the grade, should we do it?
  @GET
  @Path("/{courseId}/students/passed")
  @Produces(MediaType.APPLICATION_JSON)
  public Response coursesStudentsPassedIndex(@PathParam("courseId") final String courseId,
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
    final Course courseWithStudents = cs.getStudentsThatPassedCourse(courseId, studentFilter);
    final List<Student> approvedStudents = courseWithStudents.getApprovedStudents();

    final List<StudentIndexDTO> studentsList = approvedStudents.stream().map(student -> mapper.convertToStudentIndexDTO(student)).collect(Collectors.toList());

    return ok(new StudentsList(studentsList)).build();
  }

  @POST
  @Path("/{courseId}/correlatives")
  public Response coursesCorrelativesNew(@PathParam("courseId") final String courseId,
                                         @Valid final CorrelativeDTO correlativeDTO) {
    if(!cs.addCorrelative(courseId, correlativeDTO.getCorrelativeId())) {
      return status(Status.BAD_REQUEST).build();
    }

    return noContent().build();
  }

  @DELETE
  @Path("/{courseId}/correlatives/{correlativeId}")
  public Response coursesCorrelativesDestroy(@PathParam("courseId") final String courseId,
                                             @PathParam("correlativeId") final String correlativeId) {
    if(!cs.deleteCorrelative(courseId, correlativeId)) {
      return status(Status.BAD_REQUEST).build();
    }

    return noContent().build();
  }

  @GET
  @Path("/finalInscription/{finalInscriptionId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response coursesFinalInscriptionsShow(@PathParam("finalInscriptionId") final int finalInscriptionId) {
    final FinalInscription finalInscription = cs.getFinalInscription(finalInscriptionId);

    if(finalInscription == null) {
      return status(Status.NOT_FOUND).build();
    }

    final FinalInscriptionDTO finalInscriptionDTO = mapper.convertToFinalInscriptionDTO(
            finalInscription,
            cs.getFinalStudents(finalInscriptionId));

    return ok(finalInscriptionDTO).build();
  }

  @POST
  @Path("/finalInscription/{finalInscriptionId}")
  public Response coursesFinalInscriptionQualify(@PathParam("finalInscriptionId") final int finalInscriptionId,
                                                 @Valid GradeDTO gradeDTO) {
    //TODO see if gradeDTO.getGrade can be improved
    if(!ss.addFinalGrade(finalInscriptionId, gradeDTO.getStudent().getDocket(), gradeDTO.getGrade())) {
      return status(Status.BAD_REQUEST).build();
    }

    return noContent().build();
  }
}

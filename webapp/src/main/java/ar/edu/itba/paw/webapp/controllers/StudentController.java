package ar.edu.itba.paw.webapp.controllers;


import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.StudentFilter;
import ar.edu.itba.paw.webapp.models.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
import java.util.Collection;
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
  private CourseService cs;

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
          @Size(max=50)
          @QueryParam("firstName") final String firstName,
          @Size(max=50)
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
    final Student student = mapper.convertToStudent(userNewDTO);
    if(!ss.create(student)) {
      return status(Status.BAD_REQUEST).build();
    }
    final int docket = ss.getByDni(userNewDTO.getDni()).getDocket();
    final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(docket)).build();
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

    return noContent().build();
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
  public Response studentsAddressUpdate(
          @PathParam("docket") final int docket,
          @Valid AddressDTO addressDTO) {
    final Student student = ss.getByDocket(docket);

    if(student == null){
      return status(Status.NOT_FOUND).build();
    }

    Address address = mapper.convertToAddress(addressDTO);
    ss.editAddress(docket, address);
    return ok().build();
  }

  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/{docket}/courses")
  public Response studentsCoursesIndex(
          @Min(value = 1)
          @PathParam("docket") final Integer docket,
          @Size(max=5)
          @QueryParam("id") final String courseID,
          @Size(max=50)
          @QueryParam("name") final String courseName){

    final Student student = ss.getByDocket(docket);
    if(student == null){
      return status(Status.NOT_FOUND).build();
    }

    final CourseFilter courseFilter = new CourseFilter.CourseFilterBuilder().
            id(courseID).keyword(courseName).build();

    List<Course> courses = ss.getStudentCourses(docket, courseFilter);

    final List<CourseDTO> coursesList = courses.stream()
            .map(course -> mapper.convertToCourseDTO(course)).collect(Collectors.toList());

    return ok(new CoursesList(coursesList)).build();

  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/{docket}/courses")
  public Response studentsCoursesNew(
          @PathParam("docket") final Integer docket,
          @Valid InscriptionDTO inscriptionDTO){

    //TODO securityContext.getAuthentication().getAuthorities() ADD_INSCRIPTIONS see if we can put it in WebAuthConfig as an antmatcher

    final Student student = ss.getByDocket(docket);
    if(student == null){
      return status(Status.NOT_FOUND).build();
    }

    final Course course = cs.getByCourseID(inscriptionDTO.getCourseId());
    if(course == null){
      return status(Status.BAD_REQUEST).build();
    }

    boolean result = ss.enroll(docket, course.getCourseId());

    if(!result){
      return status(Status.BAD_REQUEST).build();
      // TODO: Decide what to return
      // return status(Status.PRECONDITION_FAILED).build();
    }
    return status(Status.OK).build();
  }

  @DELETE
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/{docket}/courses")
  public Response studentsCoursesDestroy(
          @PathParam("docket") final Integer docket,
          @Valid InscriptionDTO inscriptionDTO){

    //TODO securityContext.getAuthentication().getAuthorities() DELETE_INSCRIPTIONS

    final Student student = ss.getByDocket(docket);
    if(student == null){
      return status(Status.NOT_FOUND).build();
    }

    final Course course = cs.getByCourseID(inscriptionDTO.getCourseId());
    if(course == null){
      return status(Status.BAD_REQUEST).build();
    }

    ss.unenroll(docket, course.getId());

    return Response.noContent().build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{docket}/courses/available")
  public Response studentsCoursesAvailable(
          @Min(value = 1)
          @PathParam("docket") final Integer docket,
          @Size(max=5)
          @QueryParam("id") final String courseID,
          @Size(max=50)
          @QueryParam("name") final String courseName){

    final Student student = ss.getByDocket(docket);
    if(student == null){
      return status(Status.NOT_FOUND).build();
    }

    final CourseFilter courseFilter = new CourseFilter.CourseFilterBuilder().
            id(courseID).keyword(courseName).build();

    List<Course> courses = ss.getAvailableInscriptionCourses(docket, courseFilter);

    final List<CourseDTO> coursesList = courses.stream()
            .map(course -> mapper.convertToCourseDTO(course)).collect(Collectors.toList());

    return ok(new CoursesList(coursesList)).build();
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{docket}/grades")
  public Response studentsGradesIndex(@PathParam("docket") final Integer docket){

    final Student student = ss.getByDocket(docket);
    if(student == null){
      return status(Status.NOT_FOUND).build();
    }

    final Collection<Collection<TranscriptGrade>> transcript = ss.getTranscript(docket);

    final List<List<TranscriptGradeDTO>> transcriptDTO = transcript.stream()
            .map(semestersList -> semestersList.stream()
                    .map(transcriptGrade -> mapper.convertToTranscriptGradeDTO(transcriptGrade)).collect(Collectors.toList()))
            .collect(Collectors.toList());

    return ok(new TranscriptDTO(transcriptDTO)).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/{docket}/grades")
  public Response studentsGradesNew(@PathParam("docket") final Integer docket,
                                       @Valid GradeDTO gradeDTO){

    final Student student = ss.getByDocket(docket);
    if(student == null){
      return status(Status.NOT_FOUND).build();
    }

    final Course course = cs.getByCourseID(gradeDTO.getCourseId());
    if(course == null){
      return status(Status.BAD_REQUEST).build();
    }

    final List<Course> studentCourses = ss.getStudentCourses(docket, null);
    if(studentCourses == null || !studentCourses.contains(course)){
      return status(Status.BAD_REQUEST).build(); //TODO: Return Precondition Failed status??
    }

    Grade grade = mapper.convertToGrade(gradeDTO, student, course, null);

    int gradeId = ss.addGrade(grade);

    // (Same goes for an inscription)
    final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(gradeId)).build();
    return created(uri).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("/{docket}/grades/{gradeId}")
  public Response studentsGradesUpdate(
          @PathParam("docket") final Integer docket,
          @Min(1)
          @PathParam("gradeId") final Integer gradeId,
          @Valid GradeDTO gradeDTO){

    final Student student = ss.getByDocket(docket);
    if(student == null){
      return status(Status.NOT_FOUND).build();
    }

    final Course course = cs.getByCourseID(gradeDTO.getCourseId());
    if(course == null){
      return status(Status.BAD_REQUEST).build();
    }

    Grade grade = mapper.convertToGrade(gradeDTO, student, course, gradeId);

    ss.editGrade(grade, null);

    return ok().build();

  }

  /*************************  NOT WORKING ************************************************/

  @GET
  @Path("/{docket}/finalInscriptions")
  public Response studentsFinalInscriptionsIndex(@PathParam("docket") final Integer docket){
    // TODO: Test this method

    final Student student = ss.getByDocket(docket);
    if(student == null){
      return status(Status.NOT_FOUND).build();
    }

    final List<FinalInscription> finalInscriptions = ss.getFinalInscriptionsTaken(docket);

    final List<FinalInscriptionIndexDTO> list = finalInscriptions.stream()
            .map(finalInscription -> mapper.convertToFinalInscriptionIndexDTO(finalInscription)).collect(Collectors.toList());

    return ok(new FinalInscriptionsList(list)).build();
  }

  @POST
  @Path("/{docket}/finalInscriptions/{finalInscriptionId}")
  public Response studentsFinalInscriptionsNew(
          @PathParam("docket") final Integer docket,
          @Min(1)
          @PathParam("finalInscriptionId") final Integer inscriptionId){

    // TODO: Test this method

    final Student student = ss.getByDocket(docket);
    if(student == null){
      return status(Status.NOT_FOUND).build();
    }

    boolean done = ss.addStudentFinalInscription(docket, inscriptionId);
    if(!done){
      return status(Status.BAD_REQUEST).build();
    }

    return ok().build();
  }

  @DELETE
  @Path("/{docket}/finalInscriptions/{finalInscriptionId}")
  public Response studentsFinalInscriptionsDestroy(
          @PathParam("docket") final Integer docket,
          @Min(1)
          @PathParam("finalInscriptionId") final Integer inscriptionId) {

    // TODO: Test this method

    final Student student = ss.getByDocket(docket);
    if(student == null){
      return status(Status.NOT_FOUND).build();
    }
    ss.deleteStudentFinalInscription(docket, inscriptionId);

    return ok().build(); // TODO: No content??

  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/{docket}/finalInscriptions/available")
  public Response studentsFinalInscriptionsAvailable(@PathParam("docket") final Integer docket){
    // TODO: Test this method
    final Student student = ss.getByDocket(docket);
    if(student == null){
      return status(Status.NOT_FOUND).build();
    }

    final List<FinalInscription> finalInscriptions = ss.getAvailableFinalInscriptions(docket);

    final List<FinalInscriptionIndexDTO> list = finalInscriptions.stream()
            .map(finalInscription -> mapper.convertToFinalInscriptionIndexDTO(finalInscription)).collect(Collectors.toList());

    return ok(new FinalInscriptionsList(list)).build();
  }

  /*************************  NOT WORKING ************************************************/

}

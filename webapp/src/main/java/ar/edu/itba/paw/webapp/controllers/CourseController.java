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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    if (cs.getByCourseID(course.getCourseId()) != null) {
      return status(Status.CONFLICT).build();
    }
    cs.create(course);
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

	  if (cs.getByCourseID(courseDTO.getCourseId()) != null && !courseId.equals(courseDTO.getCourseId())) {
		  LOGGER.debug("Attempting to update course {} with courseId {}, which already exists", courseId, courseDTO.getCourseId());
		  return status(Status.CONFLICT).entity(createJSONEntryMessage("conflictField", "courseId")).build();
	  }

    final Course courseUpdated = mapper.convertToCourse(courseDTO);
    if(!cs.update(courseId, courseUpdated)) {
	    return status(Status.CONFLICT).entity(createJSONEntryMessage("conflictField", "semester")).build();
    }

	  if (!courseId.equals(courseDTO.getCourseId())) {
		  final URI uri = uriInfo.getBaseUriBuilder().path("/courses/" + String.valueOf(courseUpdated.getCourseId())).build();
		  return ok(uri).build();
	  }

	  return noContent().build();
  }

	private String createJSONEntryMessage(final String key, final String value) {
		final StringBuilder json = new StringBuilder();

		json
						.append("{")
						.append("\"")
						.append(key)
						.append("\"")
						.append(":")
						.append("\"")
						.append(value)
						.append("\"")
						.append("}");

		return json.toString();
	}

  @DELETE
  @Path("/{courseId}")
  public Response coursesDestroy(@PathParam("courseId") final String courseId) {
    if(!cs.deleteCourse(courseId)) {
      return Response.status(Status.CONFLICT).build(); //TODO: check what to return -- Look at PRECONDITION_FAILED
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

	@POST
	@Path("/{courseId}/students/qualify")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response coursesStudentsQualify(@PathParam("courseId") final String courseId,
	                                       @Valid final List<QualifyStudent> qualifiedStudents) {
		final HTTPResponseList httpStatusList = new HTTPResponseList(qualifiedStudents.size());

		final Course course = cs.getByCourseID(courseId);

		if (course == null) {
			return status(Status.NOT_FOUND).build();
		}

		if (qualifiedStudents.size() != 0) {

			for (final QualifyStudent qualifyStudent : qualifiedStudents) {
				final int docket = qualifyStudent.getDocket();
				final BigDecimal gradeValue = qualifyStudent.getGrade();

				final Student student = ss.getByDocket(qualifyStudent.getDocket());

				if (student == null) {
					httpStatusList.add(String.valueOf(docket), Status.NOT_FOUND);
				} else {
					final List<Course> studentCourses = ss.getStudentCourses(docket, null);

					if (studentCourses == null || !studentCourses.contains(course)) {
						httpStatusList.add(String.valueOf(docket), Status.CONFLICT);
						final Grade grade = new Grade.Builder(null, student, course.getId(), course.getCourseId(), gradeValue).build();

						grade.setCourse(course);

						final int gradeId = ss.addGrade(grade);

						final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(gradeId)).build();
						httpStatusList.add(uri.toString(), Status.CREATED);
					}
				}
			}
		}
		return Response.status(207).entity(httpStatusList).build();
	}

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
            .docket(docket)
            .firstName(firstName)
            .lastName(lastName)
            .build();
    final Map<Student, Grade> approvedStudents = cs.getStudentsThatPassedCourse(courseId, studentFilter);
    final List<StudentWithGradeDTO> studentsList = mapper.convertToStudentsWithGradeDTO(approvedStudents);

    return ok(new StudentsWithGradeList(studentsList)).build();
  }

  @GET
  @Path("/{courseId}/correlatives")
  public Response coursesCorrelativesShow(@PathParam("courseId") final String courseId) {
    if(cs.getByCourseID(courseId) == null) {
      return status(Status.NOT_FOUND).build();
    }
    final List<String> correlativesIds = cs.getCorrelativesIds(courseId);
    final List<Course> correlatives = new ArrayList<>(correlativesIds.size());

    for(String correlativeId : correlativesIds) {
      correlatives.add(cs.getByCourseID(correlativeId));
    }

    final List<CourseDTO> correlativesDTO = correlatives.stream().map(correlative -> mapper.convertToCourseDTO(correlative)).collect(Collectors.toList());

    return ok(new CoursesList(correlativesDTO)).build();
  }

  @POST
  @Path("/{courseId}/correlatives")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response coursesCorrelativesNew(@PathParam("courseId") final String courseId,
                                         @Valid final CorrelativeDTO correlativeDTO) {
    if(!cs.addCorrelative(courseId, correlativeDTO.getCorrelativeId())) {
      return status(Status.BAD_REQUEST).build();
    }

    return noContent().build();
  }


  @GET
  @Path("/{courseId}/correlatives/available")
  public Response coursesCorrelativesAvailableIndex(@Size(max=5)  @PathParam("courseId") final String courseId,
                                                    @Size(max=5)  @QueryParam("correlativeId") final String correlativeId,
                                                    @Size(max=50) @QueryParam("name") final String name) {
    final CourseFilter courseFilter = new CourseFilter.CourseFilterBuilder()
            .id(correlativeId)
            .keyword(name)
            .build();

    final List<Course> coursesFiltered = cs.getAvailableAddCorrelatives(courseId, courseFilter);
    final List<CourseDTO> coursesList = coursesFiltered.stream().map(course -> mapper.convertToCourseDTO(course)).collect(Collectors.toList());

    return ok(new CoursesList(coursesList)).build();

  }

  @DELETE
  @Path("/{courseId}/correlatives/{correlativeId}")
  public Response coursesCorrelativesDestroy(@PathParam("courseId") final String courseId,
                                             @PathParam("correlativeId") final String correlativeId) {

	  if (cs.getByCourseID(courseId) == null || cs.getByCourseID(correlativeId) == null) {
		  return status(Status.NOT_FOUND).build();
	  }

    if (courseId.equals(correlativeId)) {
      return status(Status.CONFLICT).build();
    }
	  cs.deleteCorrelative(courseId, correlativeId);

    return noContent().build();
  }

  @GET
  @Path("/{courseId}/finalInscriptions")
  @Produces(MediaType.APPLICATION_JSON)
  public Response coursesFinalInscriptionsIndex(
          @Pattern(regexp="\\d{2}\\.\\d{2}")
          @PathParam("courseId") final String courseId) {

    final Course course = cs.getByCourseID(courseId);
    if(course == null){
      return status(Status.NOT_FOUND).build();
    }

    List<FinalInscription> finalInscriptions = cs.getCourseFinalInscriptions(courseId);

    final List<FinalInscriptionIndexDTO> list = finalInscriptions.stream()
            .map(finalInscription -> mapper.convertToFinalInscriptionIndexDTO(finalInscription)).collect(Collectors.toList());

    return ok(new FinalInscriptionsList(list)).build();
  }

  @POST
  @Path("/{courseId}/finalInscriptions")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response coursesFinalInscriptionsNew(
          @Pattern(regexp="\\d{2}\\.\\d{2}")
          @PathParam("courseId") final String courseId,
          @Valid final FinalInscriptionDTO finalinscriptionDTO) {

    final Course course = cs.getByCourseID(courseId);
    if(course == null){
      return status(Status.NOT_FOUND).build();
    }

    FinalInscription finalInscription = mapper.convertToFinalInscription(finalinscriptionDTO, course);

    int id = cs.addFinalInscription(finalInscription);

    final URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id)).build();
    return created(uri).build();
  }

  @GET
  @Path("/{courseId}/finalInscriptions/{finalInscriptionId}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response coursesFinalInscriptionsShow(
          @Pattern(regexp="\\d{2}\\.\\d{2}")
          @PathParam("courseId") final String courseId,
          @PathParam("finalInscriptionId") final int finalInscriptionId) {

    final Course course = cs.getByCourseID(courseId);
    if(course == null){
      return status(Status.NOT_FOUND).build();
    }

    final FinalInscription finalInscription = cs.getFinalInscription(finalInscriptionId);
    if(finalInscription == null) {
      return status(Status.NOT_FOUND).build();
    }

    final FinalInscriptionDTO finalInscriptionDTO = mapper.convertToFinalInscriptionDTO(
            finalInscription,
            cs.getFinalStudents(finalInscriptionId));

    return ok(finalInscriptionDTO).build();
  }

  @DELETE
  @Path("/{courseId}/finalInscriptions/{finalInscriptionId}")
  public Response coursesFinalInscriptionsDestroy(
          @Pattern(regexp="\\d{2}\\.\\d{2}")
          @PathParam("courseId") final String courseId,
          @PathParam("finalInscriptionId") final int finalInscriptionId) {

    final Course course = cs.getByCourseID(courseId);
    if(course == null){
      return status(Status.NOT_FOUND).build();
    }

    cs.deleteFinalInscription(finalInscriptionId);
    return noContent().build();
  }

	@POST
	@Path("/{courseId}/finalInscriptions/{finalInscriptionId}/qualify")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response courseFinalInscriptionQualify(@Pattern(regexp = "\\d{2}\\.\\d{2}") @PathParam("courseId") final String courseId,
	                                              @PathParam("finalInscriptionId") final int finalInscriptionId,
	                                              @Valid List<QualifyStudent> qualifiedStudents) {
		if (cs.getByCourseID(courseId) == null || cs.getFinalInscription(finalInscriptionId) == null) {
			return status(Status.NOT_FOUND).build();
		}

		final FinalInscription finalInscription = cs.getFinalInscription(finalInscriptionId);
		if (!cs.getCourseFinalInscriptions(courseId).contains(finalInscription)) {
			return status(Status.NOT_FOUND).build();
		}

		final HTTPResponseList httpStatusList = new HTTPResponseList(qualifiedStudents.size());

		for (final QualifyStudent qualifyStudent : qualifiedStudents) {
			final int docket = qualifyStudent.getDocket();
			final BigDecimal grade = qualifyStudent.getGrade();

			if (!ss.addFinalGrade(finalInscriptionId, docket, grade)) {
				httpStatusList.add(String.valueOf(docket), Status.CONFLICT);
			} else {
				httpStatusList.add(String.valueOf(docket), Status.NO_CONTENT);
			}
		}

		return status(207).entity(httpStatusList).build();
	}
}

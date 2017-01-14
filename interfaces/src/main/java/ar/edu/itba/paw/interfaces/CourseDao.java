package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.FinalInscription;
import ar.edu.itba.paw.shared.CourseFilter;

import java.util.List;

public interface CourseDao {

    /**
     *
     * @param course The course to be persisted in the database.
     * @return The Result code of the insertion
     */
    boolean create(Course course);

    /**
     * Update a course
     * @param courseId Id of the old course
     * @param course Modified course
     * @return The result code of the insertion
     */
    boolean update(String courseId, Course course);

    /**
     * Get the course identified by an ID
     * @param id that identifies the course
     * @return the desired course, if exists; else null
     */
    Course getById(int id);

    /**
     * Gets the desired course by the course's ID code
     * @param courseID the course's ID code
     * @return the course if it exists; null otherwise.
     */
    Course getByCourseID(String courseID);

    /**
     * Gets the desired course by the identifier with the inscribed students
     * @param courseId the id of the course
     * @return the course created, with a list of the inscribed students
     */
    Course getCourseStudents(String courseId);

    /**
     * Get all the courses in the storage
     * @return all the courses stored.
     */
    List<Course> getAllCourses();

    /**
     *  Get all the courses that comply with the filters
     * @param courseFilter which contains all the filters that will be used in the query.
     * @return all the courses that complied with the filters
     */
    List<Course> getByFilter(CourseFilter courseFilter);


    /**
     * Attempts to delete the course with the given id
     * @param id of the course to delete
     * @return OK if the course was deleted;
     *         COURSE_EXISTS_INSCRIPTION if there are inscriptions in that course;
     *         COURSE_EXISTS_GRADE if there grades tied to the course;
     *         INVALID_INPUT_PARAMETERS if the ID is invalid;
     *         ERROR_UNKNOWN else;
     */
    boolean deleteCourse(int id);

    /**
     * @param courseId The id of the course.
     * @return List of correlatives for the given course (i.d. The courses that are requiered to enroll a student in the
     * given course)
     */
    List<Integer> getCorrelatives(int courseId);

    /**
     * Check if a correlativity loop is formed
     *
     * @param id The id of the course who will have the correlativity
     * @param correlativeId The id of the course correlative course
     * @return The boolean value indicating if the correlativity loop
     * is generated.
     */
    boolean checkCorrelativityLoop(int id, int correlativeId);

    /**
     * Persist a correlativity
     *
     * @param id The id of the course who will have the correlativity
     * @param correlativeId The id of the course correlative course
     * @return The insertion result.
     */
    boolean addCorrelativity(int id, int correlativeId);

    /**
     * Check whether a course exists or not
     * @param id The id of the course;
     * @return The boolean indicating if the course exists
     */
    boolean courseExists(int id);

    /**
     * @param courseId The id of the course.
     * @return The boolean indicating if the given course has any enrolled students.
     */
    boolean inscriptionExists(int courseId);

    /**
     * @param courseId The id of the course.
     * @return The boolean indicating whether there are any students with grades of the given course.
     */
    boolean gradeExists(int courseId);

    /**
     * @param courseId The id of the course.
     * @return List of correlatives for the given course (i.d. The courses that require this course to enroll a student in the
     * given course)
     */
    List<Integer> getUpperCorrelatives(int courseId);

    /**
     *
     * @param courseId The id of the course.
     * @param correlativeId The id of the correlative for the given course.
     * @return OK if no errors were found, UNKNOWN_ERROR otherwise.
     */
    boolean deleteCorrelative(int courseId, int correlativeId);

    /**
     * Get the number of semesters.
     * @return Integer indicating the number of semesters
     */
    Integer getTotalSemesters();

    /**
     *
     * @param courseId The course Id
     * @return List of correlatives for the given course (i.d. The courses that are required to enroll a student in the
     * given course)
     */
    List<Course> getCorrelativeCourses(String courseId);

    /**
     * Get the total credits of the plan.
     * @return Integer indicating the total credits of the plan.
     */
    Integer getTotalPlanCredits();

    /* +++ xtest */
    /* +++ xdocument */
    Course getStudentsThatPassedCourse(int id);


    /**
     * Get the open final inscriptions corresponding to a course.
     * @param id The course's id
     * @return The list containing all the final inscriptions.
     */
    List<FinalInscription> getOpenFinalInsciptions(Integer id);
}

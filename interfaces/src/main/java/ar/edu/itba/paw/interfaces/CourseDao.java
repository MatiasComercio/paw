package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.Result;

import java.util.List;
import java.util.Set;

public interface CourseDao {

    /**
     *
     * @param course The course to be persisted in the database.
     * @return The Result code of the insertion
     */
    Result create(Course course);

    /**
     * Update a course
     * @param id Id of the old course
     * @param course Modified course
     * @return The result code of the insertion
     */
    Result update(final Integer id, final Course course);

    /**
     * Get the course identified by an ID
     * @param id that identifies the course
     * @return the desired course, if exists; else null
     */
    Course getById(int id);



    /**
     * Gets the desired course by the identifier with the inscribed students
     * @param id the id of the course
     * @return the course created, with a list of the inscribed students
     */
    Course getCourseStudents(int id);

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
     * @return true if the course was deleted; false in other case
     */
    Result deleteCourse(Integer id);

    /**
     * @param courseId The id of the course.
     * @return List of correlatives for the given course (i.d. The courses that are requiered to enroll a student in the
     * given course)
     */
    List<Integer> getCorrelatives(Integer courseId);

    /**
     * Check if a correlativity loop is formed
     *
     * @param id The id of the course who will have the correlativity
     * @param correlativeId The id of the course correlative course
     * @return The boolean value indicating if the correlativity loop
     * is generated.
     */
    boolean checkCorrelativityLoop(final Integer id, final Integer correlativeId);

    /**
     * Persist a correlativity
     *
     * @param id The id of the course who will have the correlativity
     * @param correlativeId The id of the course correlative course
     * @return The insertion result.
     */
    Result addCorrelativity(final Integer id, final Integer correlativeId);

    /**
     * Check whether a course exists or not
     * @param id The id of the course;
     * @return The boolean indicating if the course exists
     */
    boolean courseExists(final Integer id);

    /**
     * @param courseId The id of the course.
     * @return The boolean indicating if the given course has any enrolled students.
     */
    boolean inscriptionExists(final Integer courseId);

    /**
     * @param courseId The id of the course.
     * @return The boolean indicating whether there are any students with grades of the given course.
     */
    boolean gradeExists(final Integer courseId);

    /**
     * @param courseId The id of the course.
     * @return List of correlatives for the given course (i.d. The courses that require this course to enroll a student in the
     * given course)
     */
    List<Integer> getUpperCorrelatives(Integer courseId);

    /**
     *
     * @param courseId The id of the course.
     * @param correlativeId The id of the correlative for the given course.
     * @return OK if no errors were found, UNKNOWN_ERROR otherwise.
     */
    Result deleteCorrelative(Integer courseId, Integer correlativeId);

    /**
     * Get the number of semesters.
     * @return Integer indicating the number of semesters
     */
    Integer getTotalSemesters();

}

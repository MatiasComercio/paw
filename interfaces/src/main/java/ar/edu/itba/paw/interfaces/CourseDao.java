package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.FinalInscription;
import ar.edu.itba.paw.models.Grade;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;

import java.util.List;
import java.util.Map;

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
     * @param courseId of the course to delete
     */
    void deleteCourse(String courseId);

    /**
     * @param courseId The id of the course.
     * @return List of correlatives for the given course (i.d. The courses that are requiered to enroll a student in the
     * given course)
     */
    List<String> getCorrelativesIds(String courseId);

    /**
     * Check if a correlativity loop is formed
     *
     * @param id The id of the course who will have the correlativity
     * @param correlativeId The id of the course correlative course
     * @return The boolean value indicating if the correlativity loop
     * is generated.
     */
    boolean checkCorrelativityLoop(String id, String correlativeId);

    /**
     * Persist a correlativity
     *
     * @param id The id of the course who will have the correlativity
     * @param correlativeId The id of the course correlative course
     */
    void addCorrelativity(String id, String correlativeId);

    /**
     * Check whether a course exists or not
     * @param id The id of the course;
     * @return The boolean indicating if the course exists
     */
    boolean courseExists(String id);

    /**
     * @param courseId The id of the course.
     * @return The boolean indicating if the given course has any enrolled students.
     */
    boolean inscriptionExists(String courseId);

    /**
     * @param courseId The id of the course.
     * @return The boolean indicating whether there are any students with grades of the given course.
     */
    boolean gradeExists(String courseId);

    /**
     * @param courseId The id of the course.
     * @return List of correlatives for the given course (i.d. The courses that require this course to enroll a student in the
     * given course)
     */
    List<String> getUpperCorrelatives(String courseId);

    /**
     *
     * @param courseId The id of the course.
     * @param correlativeId The id of the correlative for the given course.
     */
    void deleteCorrelative(String courseId, String correlativeId);

    /**
     * Get the number of semesters.
     * @return Integer indicating the number of semesters
     */
    int getTotalSemesters();

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
    int getTotalPlanCredits();

    /**
     * Gets the student that passed both the given course and the course's final exam
     * @param courseId The Course's Id
     * @return The list of students that passed the course
     */

    Map<Student, Grade> getStudentsThatPassedCourse(String courseId);


    /**
     * Get the final inscriptions corresponding to a course.
     * @param courseId The course's id
     * @return A list of all the final inscriptions for the given course
     */
    List<FinalInscription> getCourseFinalInscriptions(String courseId);

    /**
     * Get the open final inscriptions corresponding to a course.
     * @param courseId The course's id
     * @return A list of all the open final inscriptions for the given course
     */
    List<FinalInscription> getCourseOpenFinalInscriptions(String courseId);

    /**
     * Get a final inscription by it's id
     * @param id The given's final inscription's id
     * @return The Final Inscription
     */
    FinalInscription getFinalInscription(int id);

    /**
     * Creates a new final inscription
     * @param finalInscription The final inscription to be created
     * @return The created inscription's id
     */
    int addFinalInscription(FinalInscription finalInscription);

    /**
     * Deletes the a final inscription with the given id
     * @param finalInscriptionId The final inscription's id
     */
    void deleteFinalInscription(int finalInscriptionId);

}

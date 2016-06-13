package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.FinalInscription;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.StudentFilter;

import java.util.List;
import java.util.Set;

public interface CourseService {
    /**
     *
     * @param course The course to be persisted in the database.
     * @return The Result code of the insertion.
     */
    boolean create(Course course);

    /**
     * Update a course
     * @param id Id the previous course.
     * @param course Modified course.
     * @return The result code of the insertion.
     */
    boolean update(int id, Course course);

	/**
     * Get all the current available courses
     * @return a list containing all the current available courses, if any.
     */
    List<Course> getAllCourses();

    /**
     * Gets the desired course by the identifier
     * @param id The course's ID
     * @return the course with the given ID, if exists; null otherwise.
     */
    Course getById(int id);

    /**
     * Gets the desired course by the identifier with the inscribed students
     * @param id the id of the course
     * @return the course created, with a list of the inscribed students
     */
    Course getCourseStudents(int id);

    /**
     * Gets the courses that comply with the list of filters
     * @param courseFilter the list of filters to apply
     * @return the list of courses that match the list of filters. If no course matches the filters, it returns
     * an empty list.
     */
    List<Course> getByFilter(CourseFilter courseFilter);

    /**
     * Attempts to delete the course with the given id
     * @param id of the course to delete
     * @return OK if the course was deleted;
     *         ERROR_ID_OUT_OF_BOUNDS if the id is not within the required limits;
     *         COURSE_EXISTS_INSCRIPTION if there are inscriptions in that course;
     *         COURSE_EXISTS_GRADE if there grades tied to the course;
     *         INVALID_INPUT_PARAMETERS if the ID is invalid;
     *         ERROR_UNKNOWN else;
     */
    boolean deleteCourse(int id);

    /**
     * Gets the desired course by the identifier with the inscribed students,
     * filtering them using the given studentFilter
     * @param id the id of the course
     * @param studentFilter the filter to be applied to the student
     * @return the list of students enrolled in the given course
     */
    List<Student> getCourseStudents(int id, StudentFilter studentFilter);

    /**
     * @param courseId The id of the course.
     * @return The id's of correlatives for the given course (i.d. The courses that are requiered to enroll a student in the
     * given course)
     */
    List<Integer> getCorrelatives(int courseId);

    /**
     * Make the course corresponding to the correlativeId necessary
     * to take the course corresponding to the id. Checks that no correlativity
     * loop is generated.
     *
     * @param id The id of the course to which a correlative course is going to be added
     * @param correlativeId The id of the correlative course
     * @return The result indicating if the action could be done.
     */
    boolean addCorrelative(int id, int correlativeId);

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
     *
     * @param courseId Given the id of a course, delete all the correlative
     * @return The result code of the operation
     */
    boolean deleteCourseCorrelatives(int courseId);

    /**
     * Get the number of semesters.
     * @return Integer indicating the number of semesters
     */
    int getTotalSemesters();

    /**
     *
     * @param courseId The id of the course.
     * @param courseFilter The course's filter. If null, no filter is applied.
     * @return A list of correlatives courses to the given course, with the filter applied.
     */
    List<Course> getCorrelativesByFilter(int courseId, CourseFilter courseFilter);

    /**
     *
     * @param courseId The id of the course
     * @return A list of the courses that are available to be added as correlatives for the given course
     */
    List<Course> getAvailableAddCorrelatives(int courseId, CourseFilter courseFilter);

    /**
     * Get the total credits of the plan.
     * @return Integer indicating the total credits of the plan.
     */
    int getTotalPlanCredits();

    /* +++xtest */
    /* +++xdocument */
	Course getStudentsThatPassedCourse(int id, StudentFilter studentFilter);

    /**
     * Get the open final inscriptions corresponding to a course.
     * @param id The course's id
     * @return The list containing all the final inscriptions.
     */
    List<FinalInscription> getOpenFinalInsciptions(Integer id);

    /**
     * Get the student's inscribed in a FinalInscription
     * @param id The FinalInscription's id
     * @return The list of students
     */
    Set<Student> getFinalStudents(int id);


    /**
     * Get a final inscription by it's id
     * @param id The given's final inscription's id
     * @return The Final Inscription
     */
    FinalInscription getFinalInscription(int id);
}

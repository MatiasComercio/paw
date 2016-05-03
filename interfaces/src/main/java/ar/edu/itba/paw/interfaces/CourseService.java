package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.shared.StudentFilter;

import java.util.List;

public interface CourseService {
    /**
     *
     * @param course The course to be persisted in the database.
     * @return The Result code of the insertion.
     */
    Result create(Course course);

    /**
     * Update a course
     * @param id Id the previous course.
     * @param course Modified course.
     * @return The result code of the insertion.
     */
    Result update(final Integer id, final Course course);

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
     * @return +++xdocument
     */
    Result deleteCourse(Integer id);

//    +++xtest
    /**
     * Gets the desired course by the identifier with the inscribed students,
     * filtering them using the given studentFilter
     * @param id the id of the course
     * @param studentFilter the filter to be applied to the student
     * @return the list of students enrolled in the given course
     */
    List<Student> getCourseStudents(final Integer id, final StudentFilter studentFilter);

    /**
     * @param courseId The id of the course.
     * @return List of correlatives for the given course (i.d. The courses that are requiered to enroll a student in the
     * given course)
     */
    List<Integer> getCorrelatives(Integer courseId);

    /**
     * Make the course corresponding to the correlativeId necessary
     * to take the course corresponding to the id. Checks that no correlativity
     * loop is generated.
     *
     * @param id The id of the course to which a correlative course is going to be added
     * @param correlativeId The id of the correlative course
     * @return The result indicating if the action could be done.
     */
    Result addCorrelative(final Integer id, final Integer correlativeId);

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
     *
     * @param courseId Given the id of a course, delete all the correlative
     */
    Result deleteCourseCorrelatives(Integer courseId);

    /**
     * Get the number of semesters.
     * @return Integer indicating the number of semesters
     */
    Integer getTotalSemesters();

}

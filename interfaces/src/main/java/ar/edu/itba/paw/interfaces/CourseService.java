package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.shared.CourseFilter;

import java.util.List;

public interface CourseService {
    /**
     *
     * @param course The course to be persisted in the database.
     */
    void create(Course course);

    /**
     * Update a course
     * @param id Id the previous course
     * @param course Modified course
     */
    void update(final Integer id, final Course course);

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


}

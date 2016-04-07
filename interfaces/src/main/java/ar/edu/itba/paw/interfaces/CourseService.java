package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.shared.CourseFilter;

import java.util.List;

/**
 * Created by mati on 30/03/16.
 */
public interface CourseService {
    /**
     *
     * @param course The course to be persisted in the database.
     */
    void create(Course course);

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

    List<Course> getByFilter(CourseFilter courseFilter);

}

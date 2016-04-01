package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.CourseFilter;

import java.util.List;

/**
 * Created by mati on 30/03/16.
 */
public interface CourseService {
    /**
     * Creates a new Course
     * @param id the id of the course
     * @param coursename the name of the course
     * @param credits the number of credits assigned to the course
     * @return the course created, if the id does not exist in another course; else null
     */
    Course create(int id, String coursename, int credits);

    /**
     * Gets the desired course by the identifier
     * @param id The course's ID
     * @return the course with the given ID, if exists; null otherwise.
     */
    Course getById(int id);

    /**
     * Requests a list of all the courses available
     * @return the list of the courses. If there are no courses, it returns an empty list
     */
    List<Course> getAllCourses();

    List<Course> getByFilter(CourseFilter keyword);

}

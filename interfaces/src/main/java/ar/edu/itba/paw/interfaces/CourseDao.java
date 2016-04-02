package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.shared.CourseFilter;

import java.util.List;

public interface CourseDao {

    /**
     * Create a new course and store it
     * @param id of the new course
     * @param coursename the name of the course
     * @param credits the number of credits which are assigned to the course
     * @return the course with the desired properties, if the id does not collide with another course's id which
     * already exists; null otherwise
     */
    Course create(int id, String coursename, int credits);

    /**
     * Get the course identified by an ID
     * @param id that identifies the course
     * @return the desired course, if exists; else null
     */
    Course getById(int id);

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
}

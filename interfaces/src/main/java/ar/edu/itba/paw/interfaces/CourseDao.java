package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.Result;

import java.util.List;

public interface CourseDao {

    /**
     *
     * @param course The course to be persisted in the database.
     * @return The Result code of the insertion
     */
    Result create(Course course);

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


}

package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.shared.CourseFilter;

import java.util.List;

/**
 * Created by mati on 30/03/16.
 */
public interface CourseDao {

    Course create(int id, String coursename, int credits);

    Course getById(int id);

    List<Course> getAllCourses();

    List<Course> getByFilter(CourseFilter courseFilter);
}

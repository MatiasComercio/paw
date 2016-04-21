package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Override
    public Result create(Course course) {
        return courseDao.create(course);
    }

    @Override
    public Course getById(int id) {
        if(id >= 1) {
            return courseDao.getById(id);
        } else {
            return null;
        }
    }

    @Override
    public Course getCourseStudents(int id) {
        return courseDao.getCourseStudents(id);
    }


    @Override
    public List<Course> getByFilter(CourseFilter courseFilter) {
        return courseDao.getByFilter(courseFilter);
    }

    /* +++xtest method */
    public void setCourseDao(CourseDao courseDao) {
        this.courseDao = courseDao;
    }


}

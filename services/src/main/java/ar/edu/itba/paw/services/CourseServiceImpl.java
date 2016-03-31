package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.models.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mati on 30/03/16.
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Override
    public Course create(int id, String coursename, int credits) {
        return courseDao.create(id, coursename, credits);
    }

    @Override
    public Course getById(int id) {
        return courseDao.getById(id);
    }

}

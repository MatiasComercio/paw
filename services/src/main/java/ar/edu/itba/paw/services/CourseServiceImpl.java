package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.shared.StudentFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private StudentService studentService;

    @Override
    public Result create(Course course) {
        return courseDao.create(course);
    }

    @Override
    public Result update(Integer id, Course course){
        return courseDao.update(id, course);
    }

    @Override
    public List<Course> getAllCourses() {
        return getByFilter(null);
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

    @Override
    public Result deleteCourse(Integer id) {
        if(id >= 0) {
            return courseDao.deleteCourse(id);
        }
        return Result.ERROR_ID_OUT_OF_BOUNDS;
    }

    @Override
    public List<Student> getCourseStudents(final Integer id, final StudentFilter studentFilter) {

        final Course course = courseDao.getCourseStudents(id);
        if (course == null) {
            return null;
        }
        final List<Student> students = course.getStudents();
        if (students == null) {
            return null;
        }

        List<Student> studentsFiltered = studentService.getByFilter(studentFilter);
        students.retainAll(studentsFiltered);

        return students;
    }

    /* Test purpose only */
	/* default */ void setCourseDao(CourseDao courseDao) {
        this.courseDao = courseDao;
    }
    /* Test purpose only */
	/* default */ void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }


    //TODO: IF NOT CALLED FROM CONTROLLER, DELETE IMPLEMENTATION AND DELETE FROM INTERFACE

    @Override
    public List<Integer> getCorrelatives(Integer courseId) {
        return courseDao.getCorrelatives(courseId);
    }

    @Override
    public List<Integer> getUpperCorrelatives(Integer courseId) {
        return courseDao.getUpperCorrelatives(courseId);
    }

    //////////////////////////////////////////////////

    @Override
    public Result deleteCorrelative(Integer courseId, Integer correlativeId) {
        List<Integer> correlatives = getCorrelatives(courseId);
        List<Integer> upperCorrelatives = getUpperCorrelatives(courseId);

        Result result = courseDao.deleteCorrelative(courseId, correlativeId);

        if (result.equals(Result.OK) && correlatives != null){
            for (Integer correlative : correlatives) {
                for (Integer upperCorrelative : upperCorrelatives) {
                    //addCorrelative(correlative, upperCorrelative);
                }
            }
        }
        return result;
    }

}

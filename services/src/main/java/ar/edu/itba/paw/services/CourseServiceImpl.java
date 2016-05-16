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
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private StudentService studentService;

    @Transactional
    @Override
    public Result create(Course course) {
        return courseDao.create(course);
    }

    @Transactional
    @Override
    public Result update(final int id, final Course course){

        List<Course> correlatives = getCorrelativesByFilter(id, null);
        for (Course correlative : correlatives){
            if (correlative.getSemester() >= course.getSemester()) {
                return Result.CORRELATIVE_SEMESTER_INCOMPATIBILITY;
            }
        }

        return courseDao.update(id, course);
    }

    @Override
    public List<Course> getAllCourses() {
        return getByFilter(null);
    }

    @Transactional
    @Override
    public Course getById(int id) {
        if(id >= 1) {
            return courseDao.getById(id);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public Course getCourseStudents(int id) {
        return courseDao.getCourseStudents(id);
    }

    @Transactional
    @Override
    public List<Course> getByFilter(CourseFilter courseFilter) {
        return courseDao.getByFilter(courseFilter);
    }

    @Transactional
    @Override
    public int getTotalPlanCredits() {
        return courseDao.getTotalPlanCredits();
    }

    @Transactional
    @Override
    public Result deleteCourse(final int courseId) {

        if(courseId<0){
            return Result.ERROR_ID_OUT_OF_BOUNDS;
        }
        if(courseDao.inscriptionExists(courseId)){
            return Result.COURSE_EXISTS_INSCRIPTION;
        }
        if(courseDao.gradeExists(courseId)){
            return Result.COURSE_EXISTS_GRADE;
        }

        deleteCourseCorrelatives(courseId);

        Result result = courseDao.deleteCourse(courseId);

        return result;

    }

    @Transactional
    @Override
    public List<Student> getCourseStudents(final int id, final StudentFilter studentFilter) {

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

    @Transactional
    @Override
    public int getTotalSemesters() {
        return courseDao.getTotalSemesters();
    }

    @Transactional
    @Override
    public Result addCorrelative(final int id, final int correlativeId) {

        if(id == correlativeId){
            return Result.CORRELATIVE_SAME_COURSE;
        }

        //Check courses exists
        if (!courseDao.courseExists(id) || !courseDao.courseExists(correlativeId)){
            return Result.COURSE_NOT_EXISTS;
        }

        //Check correlativity loop
        if (courseDao.checkCorrelativityLoop(id, correlativeId)){
            return Result.CORRELATIVE_CORRELATIVITY_LOOP;
        }

        //Check semester
        Course course = getById(id);
        Course correlativeCourse = getById(correlativeId);

        if (course.getSemester() <= correlativeCourse.getSemester()){
            return Result.CORRELATIVE_SEMESTER_INCOMPATIBILITY;
        }

        return courseDao.addCorrelativity(id, correlativeId);
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

    @Transactional
    @Override
    public List<Integer> getCorrelatives(final int courseId) {
        return courseDao.getCorrelatives(courseId);
    }

    @Transactional
    @Override
    public List<Integer> getUpperCorrelatives(final int courseId) {
        return courseDao.getUpperCorrelatives(courseId);
    }

    //////////////////////////////////////////////////

    @Transactional
    @Override
    public Result deleteCorrelative(final int courseId, final int correlativeId) {
        return courseDao.deleteCorrelative(courseId, correlativeId);
    }

    @Override
    public Result deleteCourseCorrelatives(final int courseId) {
        List<Integer> correlatives = getCorrelatives(courseId);
        List<Integer> upperCorrelatives = getUpperCorrelatives(courseId);

        /* Adds transitive correlatives:
         * Say c2 is the given courseId and the following correlativities exist:
         * (c3,c2) and (c2, c1), in that case (c3, c1) is added to the correlatives table.
         */
        if (correlatives != null && upperCorrelatives != null){
            for (Integer correlative : correlatives) {
                for (Integer upperCorrelative : upperCorrelatives) {
                    addCorrelative(upperCorrelative, correlative); //Adding correlatives after a delete can't introduce loops
                }
            }
        }
        if(correlatives != null){
            for(Integer correlative: correlatives){
                deleteCorrelative(courseId, correlative);
            }
        }
        if(upperCorrelatives != null){
            for(Integer upperCorrelative: upperCorrelatives){
                deleteCorrelative(upperCorrelative, courseId);
            }
        }
        return Result.OK;
    }

    @Transactional
    @Override
    public List<Course> getCorrelativesByFilter(final int courseId, final CourseFilter courseFilter) {

        final List<Course> courses = getByFilter(courseFilter);

        if (courses == null) {
            return null;
        }

        List<Course> correlatives = courseDao.getCorrelativeCourses(courseId);
        if(correlatives == null){
            return null;
        }

        //Remove all courses that do not match the filter
        correlatives.retainAll(courses);

        return correlatives;
    }

    @Override
    public List<Course> getAvailableAddCorrelatives(final int courseId, final CourseFilter courseFilter) {
        List<Course> courses =  getByFilter(courseFilter);
        List<Course> correlatives = getCorrelativesByFilter(courseId, null);

        //Remove all correlatives from the list
        courses.removeAll(correlatives);

        //Remove the course itself from the list
        courses.remove(getById(courseId));
        return courses;
    }

    @Override
    public Course getStudentsThatPassedCourse(final int id, final StudentFilter studentFilter) {
        if (id <= 0) {
            return null;
        }

        final Course course = courseDao.getStudentsThatPassedCourse(id);
        if (course == null) {
            return null;
        }

        final List<Student> students = course.getStudents();
        if (students  != null) {
            students.retainAll(studentService.getByFilter(studentFilter));
        }
        course.setStudents(students);

        return course;
    }
}

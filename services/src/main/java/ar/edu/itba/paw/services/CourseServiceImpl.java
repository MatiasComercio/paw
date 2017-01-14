package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.FinalInscription;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.StudentFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
/* IMPORTANT! Do not annotate with @Transactional; do it for each method that requires it */
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private StudentService studentService;

    @Transactional
    @Override
    public boolean create(Course course) {
        return courseDao.create(course);
    }

    @Transactional
    @Override
    public boolean update(final String courseId, final Course courseUpdated){

        final List<Course> correlatives = getCorrelativesByFilter(courseId, null);
        final Course courseOld = courseDao.getByCourseID(courseId);

        courseUpdated.setId(courseOld.getId());
        for (Course correlative : correlatives){
            if (correlative.getSemester() >= courseUpdated.getSemester()) {
                return false;
            }
        }

        for (Course course1 : courseOld.getUpperCorrelatives()) {
            if (course1.getSemester() <= courseUpdated.getSemester()){
                return false;
            }
        }

        return courseDao.update(courseId, courseUpdated);
    }

    @Override
    public List<Course> getAllCourses() {
        return getByFilter(null);
    }

    @Transactional
    @Override
    public Course getById(int id) {
        return courseDao.getById(id);
    }

    @Transactional
    @Override
    public Course getByCourseID(String courseID) {
        return courseDao.getByCourseID(courseID);
    }

    @Transactional
    @Override
    public Course getCourseStudents(String courseId) {
        return courseDao.getCourseStudents(courseId);
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
    public boolean deleteCourse(final int courseId) {
        if(courseDao.inscriptionExists(courseId)){
//            return Result.COURSE_EXISTS_INSCRIPTION;
            return false;
        }

        if(courseDao.gradeExists(courseId)){
//            return Result.COURSE_EXISTS_GRADE;
            return false;
        }

        deleteCourseCorrelatives(courseId);

        return courseDao.deleteCourse(courseId);

    }

    @Transactional
    @Override
    public List<Student> getCourseStudents(final String id, final StudentFilter studentFilter) {

        final Course course = courseDao.getCourseStudents(id);
        if (course == null) {
            return null;
        }
        final List<Student> modifiableStudents = course.getStudents();
        if (modifiableStudents == null) {
            return null;
        }

        List<Student> studentsFiltered = studentService.getByFilter(studentFilter);

        return studentsFiltered
                .stream()
                .filter(modifiableStudents::contains)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional
    @Override
    public int getTotalSemesters() {
        return courseDao.getTotalSemesters();
    }

    @Transactional
    @Override
    public boolean addCorrelative(final int id, final int correlativeId) {

        if(id == correlativeId){
//            return Result.CORRELATIVE_SAME_COURSE;
            return false;
        }

        //Check courses exists
        if (!courseDao.courseExists(id) || !courseDao.courseExists(correlativeId)){
//            return Result.COURSE_NOT_EXISTS;
            return false;
        }

        //Check correlativity loop
        if (courseDao.checkCorrelativityLoop(id, correlativeId)){
//            return Result.CORRELATIVE_CORRELATIVITY_LOOP;
            return false;
        }

        //Check semester
        final Course course = getById(id);
        final Course correlativeCourse = getById(correlativeId);

        if (course.getSemester() <= correlativeCourse.getSemester()){
//            return Result.CORRELATIVE_SEMESTER_INCOMPATIBILITY;
            return false;
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
    public boolean deleteCorrelative(final int courseId, final int correlativeId) {
        return courseDao.deleteCorrelative(courseId, correlativeId);
    }

    @Transactional
    @Override
    public boolean deleteCourseCorrelatives(final int courseId) {
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
//        return Result.OK;
        return true;
    }

    @Transactional
    @Override
    public List<Course> getCorrelativesByFilter(final String courseId, final CourseFilter courseFilter) {

        final List<Course> courses = getByFilter(courseFilter);

        if (courses == null) {
            return null;
        }

        List<Course> correlatives = courseDao.getCorrelativeCourses(courseId);
        if(correlatives == null){
            return null;
        }

        //Remove all courses that do not match the filter
        final List<Course> rCourses = new LinkedList<>();
        for (Course c : courses) {
            if (correlatives.contains(c)) {
                rCourses.add(c);
            }
        }

        return rCourses;
    }

    @Transactional
    @Override
    public List<Course> getAvailableAddCorrelatives(final String courseId, final CourseFilter courseFilter) {
        List<Course> courses =  getByFilter(courseFilter);
        List<Course> correlatives = getCorrelativesByFilter(courseId, null);

        // +++ximprove: should not show incompatible correlative courses

        //Remove all correlatives from the list
        courses.removeAll(correlatives);

        //Remove the course itself from the list
        courses.remove(getByCourseID(courseId));
        return courses;
    }

    @Transactional
    @Override
    public Course getStudentsThatPassedCourse(final int id, final StudentFilter studentFilter) {
        final Course course = courseDao.getStudentsThatPassedCourse(id);
        if (course == null) {
            return null;
        }

        //TODO: DELETE - final List<Student> students = course.getStudents();
        final List<Student> students = course.getApprovedStudents();
        final List<Student> st = new LinkedList<>();
        if (students  != null) {
            final List<Student> studentsFilter = studentService.getByFilter(studentFilter);
            for (Student s : studentsFilter) {
                if (students.contains(s)) {
                    s.setGrades(s.getGrades());
                    st.add(s);
                }
            }
        }
        // Care with this! if the method or class is annotated with @Transactional, this could
        // be updating course students, and data could be lost
        course.setApprovedStudents(st);

        return course;
    }

    @Override
    public List<FinalInscription> getOpenFinalInsciptions(Integer id) {
        return courseDao.getOpenFinalInsciptions(id);
    }

    @Override
    public Set<Student> getFinalStudents(int id) {
        return  studentService.getFinalStudents(id);
    }

    @Override
    public FinalInscription getFinalInscription(int id) {
        return studentService.getFinalInscription(id);
    }
}

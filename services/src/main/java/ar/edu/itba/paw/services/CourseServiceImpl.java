package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.FinalInscription;
import ar.edu.itba.paw.models.Grade;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.StudentFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
        if(getByCourseID(course.getCourseId()) != null) {
            return false;
        }
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
    public boolean deleteCourse(final String courseId) {
        if(courseDao.getByCourseID(courseId) == null) {
            return true;
        }
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
    public boolean addCorrelative(final String courseId, final String correlativeId) {

        if(courseId.equals(correlativeId)) {
            return false;
        }

        //Check courses exists
        if (!courseDao.courseExists(courseId) || !courseDao.courseExists(correlativeId)){
            return false;
        }

        //Check correlativity loop
        if (courseDao.checkCorrelativityLoop(courseId, correlativeId)) {
            return false;
        }

        //Check semester
        final Course course = getByCourseID(courseId);
        final Course correlativeCourse = getByCourseID(correlativeId);

        if (course.getSemester() <= correlativeCourse.getSemester()) {
            return false;
        }

        return courseDao.addCorrelativity(courseId, correlativeId);
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

    /**
     * Gets the correlatives of a given course.
     * It is assumed that the courseId exists
     * @param courseId The id of the course.
     * @return a list of the course id's of the correlatives in a String manner
     */
    @Transactional
    @Override
    public List<String> getCorrelativesIds(final String courseId) {
        return courseDao.getCorrelativesIds(courseId);
    }

    @Transactional
    @Override
    public List<String> getUpperCorrelatives(final String courseId) {
        return courseDao.getUpperCorrelatives(courseId);
    }

    //////////////////////////////////////////////////

    @Transactional
    @Override
    public boolean deleteCorrelative(final String courseId, final String correlativeId) {
        if(getByCourseID(courseId) == null || getByCourseID(correlativeId) == null) {
            return false;
        }
        return courseDao.deleteCorrelative(courseId, correlativeId);
    }

    @Transactional
    @Override
    public void deleteCourseCorrelatives(final String courseId) {
        List<String> correlatives = getCorrelativesIds(courseId);
        List<String> upperCorrelatives = getUpperCorrelatives(courseId);

        /* Adds transitive correlatives:
         * Say c2 is the given courseId and the following correlativities exist:
         * (c3,c2) and (c2, c1), in that case (c3, c1) is added to the correlatives table.
         */
        if (correlatives != null && upperCorrelatives != null){
            for (String correlative : correlatives) {
                for (String upperCorrelative : upperCorrelatives) {
                    addCorrelative(upperCorrelative, correlative); //Adding correlatives after a delete can't introduce loops
                }
            }
        }
        if(correlatives != null){
            for(String correlative: correlatives){
                deleteCorrelative(courseId, correlative);
            }
        }
        if(upperCorrelatives != null){
            for(String upperCorrelative: upperCorrelatives){
                deleteCorrelative(courseId, upperCorrelative);
            }
        }
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
    public Map<Student, Grade> getStudentsThatPassedCourse(final String courseId, final StudentFilter studentFilter) {
        final Map<Student, Grade> passedStudents = courseDao.getStudentsThatPassedCourse(courseId);
        final Map<Student, Grade> result = new HashMap<>();

        final List<Student> studentsFilter = studentService.getByFilter(studentFilter);
        for (Student student : studentsFilter) {
            if (passedStudents.get(student) != null) {
                result.put(student, passedStudents.get(student));
            }
        }

        return result;
    }

    @Transactional
    @Override
    public List<FinalInscription> getCourseFinalInscriptions(String courseId) {
        return courseDao.getCourseFinalInscriptions(courseId);
    }

    @Transactional
    @Override
    public List<FinalInscription> getCourseOpenFinalInscriptions(final String courseId) {
        return courseDao.getCourseOpenFinalInscriptions(courseId);
    }

    @Transactional
    @Override
    public FinalInscription getFinalInscription(int id) {
        return courseDao.getFinalInscription(id);
    }

    @Transactional
    @Override
    public int addFinalInscription(FinalInscription finalInscription) {
        return courseDao.addFinalInscription(finalInscription);
    }

    @Transactional
    @Override
    public void deleteFinalInscription(int finalInscriptionId) {
        courseDao.deleteFinalInscription(finalInscriptionId);
    }

    @Transactional
    @Override
    public Set<Student> getFinalStudents(int id) {
        Set<Student> set = new HashSet<>();
        set.addAll(getFinalInscription(id).getHistory());
        return set;
    }

}

package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.Result;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class CourseHibernateDao implements CourseDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Result create(Course course){
        Session session = em.unwrap(Session.class);
        session.save(course);
        return Result.OK;
    }

    @Override
    public Result update(int id, Course course) {
        //Course updatedCourse = new Course.Builder(id).name(course.getName()).credits(course.getCredits()).semester(course.getId()).build();

        Session session = em.unwrap(Session.class);
        //session.save(updatedCourse);
        //session.update(updatedCourse);

        Course oldCourse = em.getReference(Course.class, id);
        //oldCourse.setId(course.getId());
        oldCourse.setName(course.getName());
        oldCourse.setCredits(course.getCredits());
        oldCourse.setSemester(course.getSemester());

        session.update(oldCourse);

        return Result.OK;
    }

    @Override
    public Course getById(int id) {
        /*final TypedQuery<Course> query = em.createQuery("from Course as c where c.id = :id", Course.class);
        query.setParameter("id", id);
        final List<Course> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);*/

        //TODO: For DEBUG purposes only
        //Set<Course> upperCorrelatives = em.find(Course.class,id).getUpperCorrelatives();
        Integer semesters = getTotalSemesters();
        Integer credits = getTotalPlanCredits();
        //checkCorrelativityLoop();

        return em.find(Course.class,id);
    }

    //TODO: Requires students
    @Override
    public Course getCourseStudents(int id) {
        return null;
    }

    @Override
    public List<Course> getAllCourses() {
        final TypedQuery<Course> query = em.createQuery("select c from Course c", Course.class);
        final List<Course> list = query.getResultList();
        return list;
    }

    @Override
    public List<Course> getByFilter(CourseFilter courseFilter) {
        //if (courseFilter == null){
        return getAllCourses();
        //}
        //return null;
    }

    @Override
    public Result deleteCourse(int id) {
        Course course = getById(id);
        em.remove(course);
        return Result.OK;
    }

    @Override
    public List<Integer> getCorrelatives(int courseId) {
        Course course = getById(courseId);
        List<Integer> correlatives = new ArrayList<Integer>();

        for(Course correlative: course.getCorrelatives()){
            correlatives.add(correlative.getId());
        }
        return correlatives;
    }

    //TODO: TEST
    @Override
    public boolean checkCorrelativityLoop(int id, int correlativeId) {

        //If the given id is in the list of inmediate correlatives of correlativeId, a cycle is detected, because it would be
        //necesary to pass id in order to be able to enroll in correlativeId.
        //If no loop is detected in the inmediate correlatives, then we keep searching in the correlative's correlatives until
        //the loop is found or no more correlatives can be add.

        Course course = getById(id);
        Course correlative = getById(correlativeId);

        Set<Course> current = new HashSet<Course>();
        current.addAll(correlative.getCorrelatives());

        int prevSize = 0;
        boolean loop = false;

        while(current.size() > prevSize && !loop){
            if(current.contains(course)){
                loop = true;
            }
            else{
                prevSize = current.size();
                for(Course auxCourse: current){
                    current.addAll(auxCourse.getCorrelatives());
                }
            }
        }
        return loop;
    }

    @Override
    public Result addCorrelativity(int id, int correlativeId) {
        Course course = getById(id);
        Course correlative = getById(correlativeId);
        course.getCorrelatives().add(correlative);

        Session session = em.unwrap(Session.class);
        session.save(course);
        return Result.OK;
    }

    @Override
    public boolean courseExists(int id) {
        return getById(id) != null;
    }

    //TODO: Requires students
    @Override
    public boolean inscriptionExists(int courseId) {
        return false;
    }

    //TODO: Requires students
    @Override
    public boolean gradeExists(int courseId) {
        return false;
    }

    @Override
    public List<Integer> getUpperCorrelatives(int courseId) {
        Course course = getById(courseId);
        List<Integer> upperCorrelatives = new ArrayList<Integer>();

        for(Course correlative: course.getUpperCorrelatives()){
            upperCorrelatives.add(correlative.getId());
        }
        return upperCorrelatives;

    }

    @Override
    public Result deleteCorrelative(int courseId, int correlativeId) {
        Course course = getById(courseId);
        Course correlative = getById(correlativeId);
        course.getCorrelatives().remove(correlative);
        Session session = em.unwrap(Session.class);
        session.save(course);
        return Result.OK;
    }

    //TODO: TEST THIS
    @Override
    public Integer getTotalSemesters() {
        //final TypedQuery<Integer> query = em.createQuery("select c.semester from Course as c where c.semester = max(c.semester)", Integer.class);
        final TypedQuery<Integer> query = em.createQuery("select max(c.semester) from Course as c", Integer.class);
        Integer totalSemesters = query.getSingleResult();
        return totalSemesters;
    }

    @Override
    public List<Course> getCorrelativeCourses(int courseId) {
        Course course = getById(courseId);
        List<Course> correlatives = new ArrayList<Course>();

        for(Course correlative: course.getCorrelatives()){
            correlatives.add(correlative);
        }
        return correlatives;
    }

    //TODO: TEST THIS
    @Override
    public Integer getTotalPlanCredits() {
        final TypedQuery<Long> query = em.createQuery("select sum(c.credits) from Course as c", Long.class);
        Integer totalCredits = query.getSingleResult().intValue();
        return totalCredits;
    }

    //TODO: Requires students
    @Override
    public Course getStudentsThatPassedCourse(int id) {
        return null;
    }
}

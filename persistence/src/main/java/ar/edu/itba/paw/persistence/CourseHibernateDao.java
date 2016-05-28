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
import java.util.List;

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
        Course updatedCourse = new Course.Builder(id).name(course.getName()).credits(course.getCredits()).semester(course.getId()).build();
        Session session = em.unwrap(Session.class);
        session.save(updatedCourse);
        return Result.OK;
    }

    @Override
    public Course getById(int id) {
        /*final TypedQuery<Course> query = em.createQuery("from Course as c where c.id = :id", Course.class);
        query.setParameter("id", id);
        final List<Course> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);*/
        return em.find(Course.class,id);
    }

    @Override
    public Course getCourseStudents(int id) {
        return null;
    }

    @Override
    public List<Course> getAllCourses() {
        //final TypedQuery<Course> query = em.createQuery("from " + Course.class.getName(), Course.class);
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

    @Override
    public boolean checkCorrelativityLoop(int id, int correlativeId) {
        return false;
    }

    @Override
    public Result addCorrelativity(int id, int correlativeId) {
        return null;
    }

    @Override
    public boolean courseExists(int id) {
        return getById(id) == null;
    }

    @Override
    public boolean inscriptionExists(int courseId) {
        return false;
    }

    @Override
    public boolean gradeExists(int courseId) {
        return false;
    }

    @Override
    public List<Integer> getUpperCorrelatives(int courseId) {
        return null;
    }

    @Override
    public Result deleteCorrelative(int courseId, int correlativeId) {
        return null;
    }

    @Override
    public Integer getTotalSemesters() {
        return null;
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

    @Override
    public Integer getTotalPlanCredits() {
        return null;
    }

    @Override
    public Course getStudentsThatPassedCourse(int id) {
        return null;
    }
}

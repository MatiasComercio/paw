package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.Result;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class CourseHibernateDao implements CourseDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Course getCourseStudents(int id) {
        return null;
    }

    @Override
    public List<Course> getAllCourses() {
        return null;
    }

    @Override
    public List<Course> getByFilter(CourseFilter courseFilter) {
        return null;
    }

    @Override
    public Result deleteCourse(int id) {
        return null;
    }

    @Override
    public List<Integer> getCorrelatives(int courseId) {
        return null;
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
        return false;
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
        return null;
    }

    @Override
    public Integer getTotalPlanCredits() {
        return null;
    }

    @Override
    public Course getStudentsThatPassedCourse(int id) {
        return null;
    }

    //@Override
    public Result create(Course course) {
        em.persist(course);
        return Result.OK;
    }

    //@Override
    public Result update(int id, Course course) {
        Course updatedCourse = new Course.Builder(id).name(course.getName()).credits(course.getCredits()).semester(course.getId()).build();
        em.persist(updatedCourse);
        return Result.OK;
    }

    //@Override
    public Course getById(int id) {
        final TypedQuery<Course> query = em.createQuery("from course as c where c.id = :id", Course.class);
        query.setParameter("id", id);
        final List<Course> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
}

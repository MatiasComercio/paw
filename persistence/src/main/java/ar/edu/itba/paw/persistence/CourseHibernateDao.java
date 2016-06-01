package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.Result;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class CourseHibernateDao implements CourseDao {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private StudentDao studentDao;

    @Override
    public Result create(Course course){
        Session session = em.unwrap(Session.class);
        session.save(course);
        return Result.OK;
    }


    @Override
    public Result update(int id, Course course){

        //NOTE: In this case if the id is changed an exception is thrown. In the future we shouldn't allow a user to modify the seq_id!
        Session session = em.unwrap(Session.class);
        Course oldCourse = em.getReference(Course.class, id);
        em.detach(oldCourse);
        session.update(course);

        return Result.OK;
    }

/*  TODO: Delete (Backup purposes only)
    @Override
    public Result update(int id, Course course) {
        //Course updatedCourse = new Course.Builder(id).name(course.getName()).credits(course.getCredits()).semester(course.getId()).build();

        Session session = em.unwrap(Session.class);
        Course oldCourse = em.getReference(Course.class, id);

        //oldCourse.setId(course.getId());

        oldCourse.setName(course.getName());
        oldCourse.setCredits(course.getCredits());
        oldCourse.setSemester(course.getSemester());

        session.update(course);
        return Result.OK;
    }
*/

    @Override
    public Course getById(int id) {
//        //TODO: For DEBUG purposes only
//        Set<Course> upperCorrelatives = em.find(Course.clKass,id).getUpperCorrelatives();
//        Integer semesters = getTotalSemesters();
//        Integer credits = getTotalPlanCredits();
//        checkCorrelativityLoop();
//        boolean inscriptionExists = inscriptionExists(id);
//        boolean gradeExists = gradeExists(id);

        return em.find(Course.class, id);
    }

    @Override
    public Course getCourseStudents(int id) {
        Course course = getById(id);
        course.getStudents();
        return course;
    }

    @Override
    public List<Course> getAllCourses() {

        final TypedQuery<Course> query = em.createQuery("select c from Course c", Course.class);
        final List<Course> list = query.getResultList();
        return list;
    }

    @Override
    public List<Course> getByFilter(CourseFilter courseFilter) {
        QueryFilter queryFilter = new QueryFilter(em);

        if (courseFilter != null) {
            queryFilter.filterByKeyword(courseFilter);
            queryFilter.filterById(courseFilter);
        }

        return em.createQuery(queryFilter.getQuery()).getResultList();
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

        /* If the given id is in the list of inmediate correlatives of correlativeId, a cycle is detected, because it would be
        necesary to pass id in order to be able to enroll in correlativeId.
        If no loop is detected in the inmediate correlatives, then we keep searching in the correlative's correlatives until
        the loop is found or no more correlatives can be add. */

        Course course = getById(id);
        Course correlative = getById(correlativeId);

        Set<Course> toAdd;
        Set<Course> current = new HashSet<>();
        current.addAll(correlative.getCorrelatives());

        int prevSize = 0;
        boolean loop = false;

        while(current.size() > prevSize && !loop){
            if(current.contains(course)){
                loop = true;
            }
            else{
                toAdd = new HashSet<>();
                prevSize = current.size();
                for(Course auxCourse: current){
                    //current.addAll(auxCourse.getCorrelatives());
                    toAdd.addAll(auxCourse.getCorrelatives());
                }
                current.addAll(toAdd);
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
    //TODO: Test this
    @Override
    public boolean inscriptionExists(int courseId) {
        Course course = getById(courseId);
        if(course.getStudents() == null || course.getStudents().size() == 0){
            return false;
        }
        return true;
    }

    @Override
    public boolean gradeExists(int courseId) {

        final TypedQuery<Long> query = em.createQuery("select count(gr) from Grade as gr where gr.course.id = :id", Long.class);
        query.setParameter("id", courseId);
        Long totalGrades = query.getSingleResult();
        return totalGrades > 0;
    }

    @Override
    public List<Integer> getUpperCorrelatives(int courseId) {
        Course course = getById(courseId);
        List<Integer> upperCorrelatives = new ArrayList<>();

        for(Course correlative : course.getUpperCorrelatives()){
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


    @Override
    public Integer getTotalSemesters() {
        final TypedQuery<Integer> query = em.createQuery("select max(c.semester) from Course as c", Integer.class);
        Integer totalSemesters = query.getSingleResult();
        return totalSemesters;
    }

    @Override
    public List<Course> getCorrelativeCourses(int courseId) {
        Course course = getById(courseId);
        List<Course> correlatives = new ArrayList<>();

        for(Course correlative: course.getCorrelatives()){
            correlatives.add(correlative);
        }
        return correlatives;
    }

    @Override
    public Integer getTotalPlanCredits() {
        final TypedQuery<Long> query = em.createQuery("select sum(c.credits) from Course as c", Long.class);
        Integer totalCredits = query.getSingleResult().intValue();
        return totalCredits;
    }

    //TODO: TEST THIS
    public Course getStudentsThatPassedCourse(int id) {
        Course course = getById(id);
        List<Student> approvedStudents = studentDao.getStudentsPassed(id);
        course.setApprovedStudents(approvedStudents);
        return course;
    }

    //QueryFilter

    private static class QueryFilter {

        private final CriteriaBuilder builder;
        private final CriteriaQuery<Course> query;
        private final EntityType<Course> type;
        private final Root<Course> root;

        private final List<Predicate> predicates = new ArrayList<>();

        QueryFilter(EntityManager em){
            builder = em.getCriteriaBuilder();
            query = builder.createQuery(Course.class);
            type = em.getMetamodel().entity(Course.class);
            root = query.from(Course.class);

        }

        private String escapeFilter(final Object filter) {
            return filter.toString().replace("%", "\\%").replace("_", "\\_");
        }

        public void filterByKeyword(CourseFilter courseFilter) {
            final Object keyword = courseFilter.getKeyword();
            final Predicate p;

            if (keyword != null){
                p = builder.like(
                        builder.lower(
                                root.get(
                                        type.getSingularAttribute("name", String.class)
                                )
                        ),
                        "%" + escapeFilter(keyword).toLowerCase() + "%"
                );
                predicates.add(p);
            }
        }

        public void filterById(CourseFilter courseFilter) {
            final Object id = courseFilter.getId();
            final Predicate p;

            if (id != null){
                p = builder.like(
                        root.get(
                                type.getSingularAttribute("id", Integer.class)
                        ).as(String.class),
                        "%" + escapeFilter(id) + "%"
                );
                predicates.add(p);
            }
        }

        public CriteriaQuery<Course> getQuery(){

            return query.where(builder.and(predicates.toArray(new Predicate[] {})));
        }


        private interface FilterQueryMapper {
            void filter(final Object filter, final String filterName);
        }
    }
}

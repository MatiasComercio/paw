package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.FinalGrade;
import ar.edu.itba.paw.models.FinalInscription;
import ar.edu.itba.paw.models.Grade;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import java.math.BigDecimal;
import java.util.*;

@Repository
public class CourseHibernateDao implements CourseDao {

    private static final String GET_BY_COURSE_ID = "from Course as c where c.courseId = :courseId";
    private static final String COURSE_ID_PARAM = "courseId";

    @PersistenceContext
    private EntityManager em;

    @Override
    public boolean create(Course course){
        Session session = em.unwrap(Session.class);
        session.save(course);

        return true;
    }


    @Override
    public boolean update(final String id, final Course course){

        //NOTE: In this case if the id is changed an exception is thrown. In the future we shouldn't allow a user to modify the seq_id!
        final List<Course> correlatives = getCorrelativeCourses(id);

        final Set<Course> setCorrelatives = new HashSet<>(correlatives);

        course.setCorrelatives(setCorrelatives);

        Session session = em.unwrap(Session.class);
        Course oldCourse = em.getReference(Course.class, course.getId());

        course.setUpperCorrelatives(oldCourse.getUpperCorrelatives());

        em.detach(oldCourse);
        session.update(course);

        return true;
    }

    @Override
    public Course getById(final int id) {
        return em.find(Course.class, id);
    }

    @Override
    public Course getByCourseID(final String courseID) {
        final TypedQuery<Course> query = em.createQuery(GET_BY_COURSE_ID, Course.class);
        query.setParameter(COURSE_ID_PARAM, courseID);
        query.setMaxResults(1);
        final List<Course> courses = query.getResultList();
        return courses.isEmpty() ? null : courses.get(0);
    }

    @Override
    public Course getCourseStudents(final String courseId) {
        final Course course = getByCourseID(courseId);
        course.getStudents();

        return course;
    }

    @Override
    public List<Course> getAllCourses() {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Course> q = cb.createQuery(Course.class);
        final Root<Course> r = q.from(Course.class);

        return em.createQuery(q.orderBy(
                cb.asc(r.get("semester")),
                cb.asc(r.get("credits")),
                cb.asc(r.get("name")),
                cb.asc(r.get("id"))
        )).getResultList();
    }

    @Override
    public List<Course> getByFilter(final CourseFilter courseFilter) {
        final QueryFilter queryFilter = new QueryFilter(em);

        if (courseFilter != null) {
            queryFilter.filterByKeyword(courseFilter);
            queryFilter.filterById(courseFilter);
        }

        return em.createQuery(queryFilter.getQuery()).getResultList();
    }

    @Override
    public void deleteCourse(final String courseId) {
        final Course course = getByCourseID(courseId);
        em.remove(course);
    }

    @Override
    public List<String> getCorrelativesIds(final String courseId) {
        final Course course = getByCourseID(courseId);
        final List<String> correlatives = new ArrayList<>();

        for(Course correlative: course.getCorrelatives()){
            correlatives.add(correlative.getCourseId());
        }
        return correlatives;
    }

    @Override
    public boolean checkCorrelativityLoop(final String id, final String correlativeId) {

        /* If the given id is in the list of inmediate correlatives of correlativeId, a cycle is detected, because it would be
        necesary to pass id in order to be able to enroll in correlativeId.
        If no loop is detected in the inmediate correlatives, then we keep searching in the correlative's correlatives until
        the loop is found or no more correlatives can be add. */

        final Course course = getByCourseID(id);
        final Course correlative = getByCourseID(correlativeId);

        Set<Course> toAdd;
        final Set<Course> current = new HashSet<>();
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
                    toAdd.addAll(auxCourse.getCorrelatives());
                }
                current.addAll(toAdd);
            }
        }
        return loop;
    }

    @Override
    public void addCorrelativity(final String id, final String correlativeId) {
        final Course course = getByCourseID(id);
        final Course correlative = getByCourseID(correlativeId);
        course.getCorrelatives().add(correlative);

        Session session = em.unwrap(Session.class);
        session.save(course);
    }

    @Override
    public boolean courseExists(final String id) {
        return getByCourseID(id) != null;
    }

    @Override
    public boolean inscriptionExists(String courseId) {
        final Course course = getByCourseID(courseId);

        if(course.getStudents() == null || course.getStudents().size() == 0){
            return false;
        }
        return true;
    }

    @Override
    public boolean gradeExists(final String courseId) {
        final TypedQuery<Long> query = em.createQuery("select count(gr) from Grade as gr where gr.course.courseId = :id", Long.class);

        query.setParameter("id", courseId);
        Long totalGrades = query.getSingleResult();
        return totalGrades > 0;
    }

    @Override
    public List<String> getUpperCorrelatives(final String courseId) {
        final Course course = getByCourseID(courseId);
        final List<String> upperCorrelatives = new ArrayList<>();

        for(Course correlative : course.getUpperCorrelatives()){
            upperCorrelatives.add(correlative.getCourseId());
        }
        return upperCorrelatives;

    }

    @Override
    public void deleteCorrelative(final String courseId, final String correlativeId) {
        final Course course = getByCourseID(courseId);
        final Course correlative = getByCourseID(correlativeId);
        course.getCorrelatives().remove(correlative);
        final Session session = em.unwrap(Session.class);
        session.save(course);
    }


    @Override
    public int getTotalSemesters() {
        final TypedQuery<Integer> query = em.createQuery("select max(c.semester) from Course as c", Integer.class);

        return query.getSingleResult();
    }

    @Override
    public List<Course> getCorrelativeCourses(final String courseId) {
        final Course course = getByCourseID(courseId);
        final List<Course> correlatives = new ArrayList<>();

        for(Course correlative: course.getCorrelatives()){
            correlatives.add(correlative);
        }
        return correlatives;
    }

    @Override
    public int getTotalPlanCredits() {
        final TypedQuery<Long> query = em.createQuery("select sum(c.credits) from Course as c", Long.class);

        return query.getSingleResult().intValue();
    }

    @Override
    public Map<Student, Grade> getStudentsThatPassedCourse(final String courseId) {
        final Map<Student, Grade> studentsPassed = new HashMap<>();

        final TypedQuery<Grade> query = em.createQuery("select gr from Grade as gr where gr.course.courseId = :id and gr.grade >= 4", Grade.class);
        query.setParameter("id", courseId);
        final List<Grade> grades = query.getResultList();

        if(grades != null){
            for(Grade grade : grades){
                for(FinalGrade finalGrade : grade.getFinalGrades()){
                    if(BigDecimal.valueOf(4).compareTo(finalGrade.getGrade()) <= 0){
                        studentsPassed.put(grade.getStudent(), grade);
                    }
                }
            }
        }

        return studentsPassed;
    }

    @Override
    public List<FinalInscription> getCourseFinalInscriptions(final String courseId) {
        final TypedQuery<FinalInscription> query = em.createQuery("from FinalInscription as fi where fi.course.courseId = :courseId", FinalInscription.class);

        query.setParameter("courseId", courseId);

        return query.getResultList();
    }

    @Override
    public List<FinalInscription> getCourseOpenFinalInscriptions(final String courseId) {
        final TypedQuery<FinalInscription> query = em.createQuery("from FinalInscription as fi where fi.course.courseId = :courseId and fi.state = :state", FinalInscription.class);
        query.setParameter("courseId", courseId);
        query.setParameter("state", FinalInscription.FinalInscriptionState.OPEN);

        return query.getResultList();
    }

    @Override
    public FinalInscription getFinalInscription(final int id) {
        return em.find(FinalInscription.class, id);
    }

    @Override
    public int addFinalInscription(final FinalInscription finalInscription) {
        final Session session = em.unwrap(Session.class);
        session.save(finalInscription);
        return finalInscription.getId();
    }

    @Override
    public void deleteFinalInscription(final int finalInscriptionId) {
        final FinalInscription finalInscription = getFinalInscription(finalInscriptionId);
        em.remove(finalInscription);
    }

    @Override
    public void closeFinalInscription(int finalInscriptionId) {
        final FinalInscription finalInscription = getFinalInscription(finalInscriptionId);
        final Session session = em.unwrap(Session.class);

        finalInscription.setState(FinalInscription.FinalInscriptionState.CLOSED);
        session.save(finalInscription);
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

        void filterByKeyword(CourseFilter courseFilter) {
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

        void filterById(CourseFilter courseFilter) {
            final Object id = courseFilter.getId();
            final Predicate p;

            if (id != null){
                p = builder.like(
                        root.get(
                                type.getSingularAttribute("courseId", String.class)
                        ).as(String.class),
                        "%" + escapeFilter(id) + "%"
                );
                predicates.add(p);
            }
        }

        CriteriaQuery<Course> getQuery() {

            return query.where(builder.and(predicates.toArray(new Predicate[] {}))).orderBy(
                    builder.asc(root.get("semester")),
                    builder.asc(root.get("credits")),
                    builder.asc(root.get("name")),
                    builder.asc(root.get("courseId"))
            );
        }
    }
}

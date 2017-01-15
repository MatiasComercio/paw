package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.FinalInscription;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
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

    private static final String GET_BY_COURSE_ID = "from Course as c where c.courseId = :courseId";
    private static final String COURSE_ID_PARAM = "courseId";

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private StudentDao studentDao;

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
    public Course getByCourseID(String courseID) {
        final TypedQuery<Course> query = em.createQuery(GET_BY_COURSE_ID, Course.class);
        query.setParameter(COURSE_ID_PARAM, courseID);
        query.setMaxResults(1);
        final List<Course> courses = query.getResultList();
        return courses.isEmpty() ? null : courses.get(0);
    }

    @Override
    public Course getCourseStudents(String courseId) {
        Course course = getByCourseID(courseId);
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

//        final TypedQuery<Course> query = em.createQuery("select c from Course c", Course.class);
//        return query.getResultList();
    }

    @Override
    public List<Course> getByFilter(final CourseFilter courseFilter) {
        QueryFilter queryFilter = new QueryFilter(em);

        if (courseFilter != null) {
            queryFilter.filterByKeyword(courseFilter);
            queryFilter.filterById(courseFilter);
        }

        return em.createQuery(queryFilter.getQuery()).getResultList();
    }

    @Override
    public boolean deleteCourse(final String courseId) {
        Course course = getByCourseID(courseId);
        em.remove(course);

        return true;
    }

    @Override
    public List<Integer> getCorrelatives(String courseId) {
        Course course = getByCourseID(courseId);
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
    public boolean addCorrelativity(int id, int correlativeId) {
        Course course = getById(id);
        Course correlative = getById(correlativeId);
        course.getCorrelatives().add(correlative);

        Session session = em.unwrap(Session.class);
        session.save(course);

        return true;
    }

    @Override
    public boolean courseExists(int id) {
        return getById(id) != null;
    }

    //TODO: Requires students
    //TODO: Test this
    @Override
    public boolean inscriptionExists(String courseId) {
        Course course = getByCourseID(courseId);
        if(course.getStudents() == null || course.getStudents().size() == 0){
            return false;
        }
        return true;
    }

    @Override
    public boolean gradeExists(String courseId) {

        final TypedQuery<Long> query = em.createQuery("select count(gr) from Grade as gr where gr.course.courseId = :id", Long.class);
        query.setParameter("id", courseId);
        Long totalGrades = query.getSingleResult();
        return totalGrades > 0;
    }

    @Override
    public List<Integer> getUpperCorrelatives(String courseId) {
        Course course = getByCourseID(courseId);
        List<Integer> upperCorrelatives = new ArrayList<>();

        for(Course correlative : course.getUpperCorrelatives()){
            upperCorrelatives.add(correlative.getId());
        }
        return upperCorrelatives;

    }

    @Override
    public boolean deleteCorrelative(String courseId, int correlativeId) { //fixme is ok that always returrs true?
        Course course = getByCourseID(courseId);
        Course correlative = getById(correlativeId);
        course.getCorrelatives().remove(correlative);
        Session session = em.unwrap(Session.class);
        session.save(course);

        return true;
    }


    @Override
    public Integer getTotalSemesters() {
        final TypedQuery<Integer> query = em.createQuery("select max(c.semester) from Course as c", Integer.class);
        Integer totalSemesters = query.getSingleResult();
        return totalSemesters;
    }

    @Override
    public List<Course> getCorrelativeCourses(final String courseId) {
        Course course = getByCourseID(courseId);
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
    @Override
    public Course getStudentsThatPassedCourse(int id) {
        Course course = getById(id);
        List<Student> approvedStudents = studentDao.getStudentsPassed(id);
        course.setApprovedStudents(approvedStudents);
        return course;
    }

    @Override
    public List<FinalInscription> getOpenFinalInsciptions(Integer id) {
        final TypedQuery<FinalInscription> query = em.createQuery("from FinalInscription as fi where fi.course.id = :id and fi.state = :state", FinalInscription.class);
        query.setParameter("id", id);
        query.setParameter("state", FinalInscription.FinalInscriptionState.OPEN);

        return query.getResultList();
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
                                type.getSingularAttribute("courseId", String.class)
                        ).as(String.class),
                        "%" + escapeFilter(id) + "%"
                );
                predicates.add(p);
            }
        }

        public CriteriaQuery<Course> getQuery(){

            return query.where(builder.and(predicates.toArray(new Predicate[] {}))).orderBy(
                    builder.asc(root.get("semester")),
                    builder.asc(root.get("credits")),
                    builder.asc(root.get("name")),
                    builder.asc(root.get("courseId"))
            );
        }


        private interface FilterQueryMapper {
            void filter(final Object filter, final String filterName);
        }
    }
}

package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.FinalGrade;
import ar.edu.itba.paw.models.FinalInscription;
import ar.edu.itba.paw.models.Grade;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.shared.StudentFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.*;

@Repository
@Primary // +++xremove this when AdmindbcDao is deleted
public class StudentHibernateDao implements StudentDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentHibernateDao.class);
	private static final String GET_ALL_STUDENTS = "from Student";
	private static final String GET_BY_DOCKET = "from Student as s where s.docket = :docket";
	private static final String GET_BY_DNI = "from Student as s where s.dni = :dni";
	private static final String GET_APPROVED_GRADES_BY_DOCKET = "from Grade as g where g.studentDocket = :docket and g.grade >= 4";
	private static final String IS_INSCRIPTION_ENABLED = "from RoleClass as r where r.role = :role";

	private static final String ROLE_COLUMN = "role";
	private static final int FIRST = 0;
	private static final int ONE = 1;
	private static final String DOCKET_PARAM = "docket";
	private static final String DNI_PARAM = "dni";
    private static final String GET_GRADES = "from Grade as g where g.studentDocket = :docket";

	@PersistenceContext
	private EntityManager em;

    @Autowired
    private CourseDao courseDao;

	@Override
	public Student getByDocket(final int docket) {
		return getBy(GET_BY_DOCKET, DOCKET_PARAM, docket);
	}

	@Override
	public Student getGrades(final int docket) {

		final Student student = getBy(GET_BY_DOCKET, DOCKET_PARAM, docket);
		// call the grades list in case it is lazy initialized
		if (student != null) {
			student.getModifiableGrades();
		}
		return student;
	}

	@Override
	public Student getGrades(final int docket, final int semesterIndex) {

		final Student student = getBy(GET_BY_DOCKET, DOCKET_PARAM, docket);
		// call the grades list in case it is lazy initialized
		if (student != null) {
			final List<Grade> grades = student.getModifiableGrades();
			grades.removeIf(grade -> {
				final Course course = grade.getCourse();
				if (course != null && course.getSemester() == semesterIndex) {
					return true;
				}
				return false;
			});
		}
		return student;
	}

	@Override
	public List<Course> getStudentCourses(final int docket) {
		final TypedQuery<Student> query = em.createQuery(GET_BY_DOCKET, Student.class);
		query.setParameter(DOCKET_PARAM, docket);
		query.setMaxResults(ONE);
		final List<Student> students = query.getResultList();
		if (students.isEmpty()) {
			return null;
		}

		return students.get(FIRST).getStudentCourses();
	}

	@Override
	public Result create(final Student student) {
		em.persist(student);
		LOGGER.debug("[create] - {}", student);
		return Result.OK;
	}

	@Override
	public Result update(final Student student) {
		em.merge(student);
		LOGGER.debug("[update] - {}", student);
		return Result.OK;
	}

	@Override
	public Result deleteStudent(final int docket) {
		final Student student = getByDocket(docket);
		em.remove(student);
		LOGGER.debug("[delete] - {}", student);
		return Result.OK;
	}

	@Override
	public List<Student> getByFilter(final StudentFilter studentFilter) {
		final UserHibernateDao.QueryFilter<Student> queryFilter
				= new UserHibernateDao.QueryFilter<>(em, Student.class);

		if (studentFilter != null) {
			queryFilter.applyFilters(studentFilter);
		}

		return em.createQuery(queryFilter.getQuery()).getResultList();
	}

	@Override
	public Result addGrade(final Grade grade) {
		em.persist(grade);
		LOGGER.debug("[addGrade] - {}", grade);
		return Result.OK;
	}

	@Override
	public Result addFinalGrade(final Grade grade, final FinalGrade finalGrade) {
		//TODO: Test this
		em.persist(finalGrade);
		LOGGER.debug("[addFinalGrade] - {} - {}", grade, finalGrade);

		List<FinalGrade> list = new ArrayList<>();
		list.addAll(grade.getFinalGrades());
		list.add(finalGrade);
		em.merge(grade);

		return Result.OK;
	}

	@Override
	public Result editGrade(final Grade newGrade, final BigDecimal oldGrade) {
		em.merge(newGrade);
//		final CriteriaBuilder cb = em.getCriteriaBuilder();
//		final CriteriaUpdate<Grade> query = cb.createCriteriaUpdate(Grade.class);
//		final Root<Grade> root = query.from(Grade.class);
//		private final List<Predicate> predicates;
//		final Predicate pCourseId = builder.equal(root.get("id"), id);

		LOGGER.debug("[editGrade] - {}", newGrade);
		return Result.OK;
	}

	@Override
	public Result enroll(final int studentDocket, final int courseId) {
		final Student student = getByDocket(studentDocket);
		final Course course = courseDao.getById(courseId);
		final List<Course> studentCourses = student.getStudentCourses();
		studentCourses.add(course);
		em.merge(student);
		return Result.OK;
	}

	@Override
	public Result unenroll(final int studentDocket, final int courseId) {
		final Student student = getByDocket(studentDocket);
		final Course course = courseDao.getById(courseId);
		final List<Course> studentCourses = student.getStudentCourses();
		studentCourses.remove(course);
		em.merge(student);
		return Result.OK;
	}

	@Override
	public Collection<Course> getApprovedCourses(final int docket) {
		// +++xcheck why this does not work; figure it out, and replace the code below with this commented one
//		final Collection<Course> courses = new ArrayList<>();
//
//		final TypedQuery<Grade> query = em.createQuery(GET_APPROVED_GRADES_BY_DOCKET, Grade.class);
//		query.setParameter(DOCKET_PARAM, docket);
//
//		final List<Grade> approvedGrades = query.getResultList();
//
//		//TODO: Improve efficiency
//		for (Grade grade : approvedGrades) {
//			courses.add(grade.getCourse());
//		}
//
//		return courses;
		final Student student = getByDocket(docket);
		final List<Course> courses = new LinkedList<>();
		for (Grade grade : student.getGrades()) {
			if (BigDecimal.valueOf(4).compareTo(grade.getGrade()) <= 0) {
				courses.add(grade.getCourse());
			}
		}

		return courses;
	}

	@Override
	public List<Integer> getApprovedCoursesId(final int docket) {
		final Student student = getByDocket(docket);
		final List<Integer> coursesId = new LinkedList<>();
		for (Grade grade : student.getGrades()) {
			if (BigDecimal.valueOf(4).compareTo(grade.getGrade()) <= 0) {
				coursesId.add(grade.getCourse().getId());
			}
		}
		return coursesId;
	}

	@Override
	public List<Student> getStudentsPassed(final int id) {

		final Course courseId = courseDao.getById(id);
		final List<Student> studentsPassed = new LinkedList<>();

		//TODO: Add a Student variable instead of a docket in Grade model
		final TypedQuery<Integer> query = em.createQuery("select gr.studentDocket from Grade as gr where gr.course.id = :id and gr.grade >= 4", Integer.class);

		query.setParameter("id", id);

		List<Integer> docketList = query.getResultList();
		for(Integer docket: docketList){
			studentsPassed.add(getByDocket(docket));
		}

		return studentsPassed;
	}

	@Override
	public Student getByDni(final int dni) {
		return getBy(GET_BY_DNI, DNI_PARAM, dni);
	}

	private Student getBy(final String queryName, final String paramName, final int paramValue) {
		final TypedQuery<Student> query = em.createQuery(queryName, Student.class);
		query.setParameter(paramName, paramValue);
		query.setMaxResults(ONE);
		final List<Student> students = query.getResultList();
		return students.isEmpty() ? null : students.get(FIRST);
	}

	//TODO: Test this
	@Override
	public boolean isApproved(int docket, int courseId) {
		//TODO: This is doing 1 query per loop: Improve eficiency. (Use student's grades??)
		TypedQuery<Long> query = em.createQuery("select count(gr) from Grade as gr where gr.course.id=:id and gr.studentDocket = :docket" +
				" and gr.grade >= 4",
				Long.class);
		query.setParameter("id", courseId);
		query.setParameter("docket", docket);
		Long approvedGrades = query.getSingleResult();
		return approvedGrades > 0;
	}

	//TODO: Test this
	@Override
	public List<FinalInscription> getAllFinalInscriptions() {
		final TypedQuery<FinalInscription> query = em.createQuery("select inscription from FinalInscription inscription", FinalInscription.class);
		final List<FinalInscription> list = query.getResultList();


		return list;
	}

	@Override
	public FinalInscription getFinalInscription(int id) {
		return em.find(FinalInscription.class, id);
	}
}

package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Grade;
import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.shared.StudentFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Repository
@Primary // +++xremove this when AdmindbcDao is deleted
public class StudentHibernateDao implements StudentDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentHibernateDao.class);

	private static final String GET_ALL_STUDENTS = "from Student";
	private static final String GET_BY_DOCKET = "from Student as s where s.docket = :docket";
	private static final String IS_INSCRIPTION_ENABLED = "from RoleClass as r where r.role = :role";
	private static final String ROLE_COLUMN = "role";

	private static final int FIRST = 0;
	private static final int ONE = 1;
	private static final String DOCKET_PARAM = "docket";

	@PersistenceContext
	private EntityManager em;

	@Override
	public Student getByDocket(final int docket) {
		final TypedQuery<Student> query = em.createQuery(GET_BY_DOCKET, Student.class);
		query.setParameter(DOCKET_PARAM, docket);
		query.setMaxResults(ONE);
		final List<Student> students = query.getResultList();
		return students.isEmpty() ? null : students.get(FIRST);
	}

	@Override
	public Student getGrades(final int docket) {
		// TODO
		return null;
	}

	@Override
	public Student getGrades(final int docket, final int semesterIndex) {
		// TODO
		return null;
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
		return Result.OK;
	}

	@Override
	public Result update(final int docket, final int dni, final Student student) {
		// TODO
		return null;
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
	public Integer getDniByDocket(final int docket) {
		// TODO
		return null;
	}

	@Override
	public Result deleteStudent(final int docket) {
		em.remove(getByDocket(docket));
		return Result.OK;
	}

	@Override
	public Result addGrade(final Grade grade) {
		// TODO
		return null;
	}

	@Override
	public Result editGrade(final Grade newGrade, final BigDecimal oldGrade) {
		// TODO
		return null;
	}

	@Override
	public Result enroll(final int studentDocket, final int courseId) {
		// TODO
		return null;
	}

	@Override
	public Result unenroll(final int studentDocket, final int courseId) {
		// TODO
		return null;
	}

	@Override
	public Collection<Course> getApprovedCourses(final int docket) {
		// TODO
		return null;
	}

	@Override
	public List<Integer> getApprovedCoursesId(final int docket) {
		// TODO
		return null;
	}

	@Override
	public boolean hasAddress(final int dni) {
		// TODO
		return false;
	}

	@Override
	public void createAddress(final int dni, final Student student) {
		// TODO
	}

	@Override
	public void updateAddress(final int dni, final Student student) {
		// TODO
	}

	@Override
	public Student getByDni(final int dni) {
		// TODO
		return null;
	}

	@Override
	public List<Student> getStudentsPassed(final int id) {
		// TODO
		return null;
	}
}

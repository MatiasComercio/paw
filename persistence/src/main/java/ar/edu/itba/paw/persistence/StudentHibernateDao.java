package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.models.Course;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
@Primary // +++xremove this when AdmindbcDao is deleted
public class StudentHibernateDao implements StudentDao {

	private static final String GET_APPROVED_GRADES_BY_DOCKET = "from Grade as g where g.studentDocket = :docket and g.grade >= 4";
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentHibernateDao.class);
	private static final String GET_ALL_STUDENTS = "from Student";
	private static final String GET_BY_DOCKET = "from Student as s where s.docket = :docket";
	private static final String GET_BY_DNI = "from Student as s where s.dni = :dni";
	private static final String IS_INSCRIPTION_ENABLED = "from RoleClass as r where r.role = :role";

	private static final String ROLE_COLUMN = "role";
	private static final int FIRST = 0;
	private static final int ONE = 1;
	private static final String DOCKET_PARAM = "docket";
	private static final String DNI_PARAM = "dni";
	private static final String GET_GRADES = "from Grade as g where ";

	@PersistenceContext
	private EntityManager em;

    @Autowired
    private CourseDao courseDao;

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
		final Student student = getBy(GET_GRADES, DOCKET_PARAM, docket);
		// call the grades list in case it is lazy initialized
		if (student != null) {
			student.getGrades();
		}
		return student;
	}

	private Student getBy(final String queryName, final String paramName, final int paramValue) {
		final TypedQuery<Student> query = em.createQuery(queryName, Student.class);
		query.setParameter(paramName, paramValue);
		query.setMaxResults(ONE);
		final List<Student> students = query.getResultList();
		return students.isEmpty() ? null : students.get(FIRST);
	}

	@Override
	public Student getGrades(final int docket, final int semesterIndex) {

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
        final Collection<Course> courses = new ArrayList<>();

        final TypedQuery<Grade> query = em.createQuery(GET_APPROVED_GRADES_BY_DOCKET, Grade.class);
        query.setParameter(DOCKET_PARAM, docket);

        final List<Grade> approvedGrades = query.getResultList();

        for (Grade grade : approvedGrades) {
            courses.add(courseDao.getById(grade.getCourseId()));
        }

        return courses;
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

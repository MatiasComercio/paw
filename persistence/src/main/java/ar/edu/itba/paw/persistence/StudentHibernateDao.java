package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.CourseDao;
import ar.edu.itba.paw.interfaces.ProcedureDao;
import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.StudentFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StudentHibernateDao implements StudentDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentHibernateDao.class);
	private static final String GET_BY_DOCKET = "from Student as s where s.docket = :docket";
	private static final String GET_BY_DNI = "from Student as s where s.dni = :dni";
	private static final String GET_APPROVED_GRADES_BY_DOCKET = "from Grade as g where g.student.docket = :docket and g.grade >= 4";

	private static final int FIRST = 0;
	private static final int ONE = 1;
	private static final String DOCKET_PARAM = "docket";
	private static final String DNI_PARAM = "dni";

	private static final Integer MAX_FINALS_CHANCES = 3;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private CourseDao courseDao;

	@Autowired
	private ProcedureDao procedureDao;

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

		return students.get(FIRST).getStudentCourses();
	}

	@Override
	public void create(final Student student) {
		em.persist(student);
		LOGGER.debug("[create] - {}", student);
	}

	@Override
	public void update(final Student student) {
		em.merge(student);
		LOGGER.debug("[update] - {}", student);
	}

	@Override
	public void deleteStudent(final int docket) {
		final Student student = getByDocket(docket);
		em.remove(student);
		LOGGER.debug("[delete] - {}", student);
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
	public int addGrade(final Grade grade) {
		em.persist(grade);
		LOGGER.debug("[addGrade] - {}", grade);

		return grade.getId();
	}

	@Override
	public void addFinalGrade(final Grade grade, final FinalGrade finalGrade) {
		LOGGER.info("Will try to persist {}", finalGrade);
		em.persist(finalGrade);
		LOGGER.debug("[addFinalGrade] - {} - {}", grade, finalGrade);

		grade.getFinalGrades().add(finalGrade);
		em.merge(grade);
	}

	@Override
	public void editGrade(final Grade newGrade) {
		em.merge(newGrade);
		LOGGER.debug("[editGrade] - {}", newGrade);
	}

	@Override
	public void editAddress(final Address address) {
		em.merge(address);
	}

	@Override
	public void enroll(final int studentDocket, final String courseId) {
		final Student student = getByDocket(studentDocket);
		final Course course = courseDao.getByCourseID(courseId);
		final List<Course> studentCourses = student.getStudentCourses();

		studentCourses.add(course);
		em.merge(student);
	}

	@Override
	public void unenroll(final int studentDocket, final int courseId) {
		final Student student = getByDocket(studentDocket);
		final Course course = courseDao.getById(courseId);
		final List<Course> studentCourses = student.getStudentCourses();

		studentCourses.remove(course);
		em.merge(student);
	}

	@Override
	public List<Course> getApprovedCourses(final int docket) {
		final List<Course> courses = new LinkedList<>();

		final TypedQuery<Grade> query = em.createQuery(GET_APPROVED_GRADES_BY_DOCKET, Grade.class);
		query.setParameter(DOCKET_PARAM, docket);

		final List<Grade> approvedGrades = query.getResultList();

		for (Grade grade : approvedGrades) {

			if (grade.getFinalGrades().size() == MAX_FINALS_CHANCES){
				for (FinalGrade finalGrade : grade.getFinalGrades()) {
					if (BigDecimal.valueOf(4).compareTo(finalGrade.getGrade()) <= 0){ // Course and Finals were approved
						courses.add(grade.getCourse());
					}
				}

			} else { // This is the last time the course was approved (Finals are pending or Course&Final were approved)
				courses.add(grade.getCourse());
			}
		}

		return courses;

	}

	//Modified to return courses that are approved and less than 3 finals are taken, or there's a final passed
	@Override
	public List<Integer> getApprovedCoursesId(final int docket) {
		final Student student = getByDocket(docket);
		final List<Integer> coursesId = new LinkedList<>();
		for (Grade grade : student.getGrades()) {
			if (BigDecimal.valueOf(4).compareTo(grade.getGrade()) <= 0) {
				if (grade.getFinalGrades().size() == MAX_FINALS_CHANCES){
					for (FinalGrade finalGrade : grade.getFinalGrades()) {
						if (BigDecimal.valueOf(4).compareTo(finalGrade.getGrade()) <= 0){
							coursesId.add(grade.getCourse().getId());
						}
					}
				} else {
					coursesId.add(grade.getCourse().getId());
				}
			}
		}
		return coursesId;
	}

	@Override
	public void createProcedure(final Procedure procedure) {
		procedureDao.createProcedure(procedure);
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

	@Override
	public List<FinalInscription> getAllFinalInscriptions() {
		final TypedQuery<FinalInscription> query = em.createQuery("select inscription from FinalInscription inscription", FinalInscription.class);
		return query.getResultList();
	}

	@Override
	public List<FinalInscription> getAllFinalInscriptionsFromOpenInstance(){
		final TypedQuery<FinalInstance> query = em.createQuery("select instance from FinalInstance instance where instance.state = :state", FinalInstance.class);
		query.setParameter("state", FinalInscription.FinalInscriptionState.OPEN);

		final FinalInstance instance = query.getSingleResult();

		return instance.getFinalInscriptions();
	}

	@Override
	public boolean canTakeCourseFinal(int docket, int courseId) {
		final TypedQuery<Grade> query = em.createQuery("from Grade as gr where gr.course.id=:id and gr.student.docket = :docket" +
						" and gr.grade >= 4", Grade.class);
		query.setParameter("id", courseId);
		query.setParameter("docket", docket);
		final List<Grade> approvedGrades = query.getResultList();

		for (Grade grade : approvedGrades) {
			if (grade.getFinalGrades().size() < MAX_FINALS_CHANCES){

				for (FinalGrade finalGrade : grade.getFinalGrades()) {
					if (BigDecimal.valueOf(4).compareTo(finalGrade.getGrade()) <= 0) {
						return false; // Course and Final was already approved
					}
				}
				return true; // Course was approved and there are still chances to take the final exam
			}
		}

		return false; // Never approved the course, or the course was approved but must be retaken
	}


	@Override
	public List<FinalInscription> getOpenFinalInscriptionsByCourse(Course course){
		return getAllFinalInscriptionsFromOpenInstance().stream().filter(inscription -> inscription.getCourse().equals(course)).collect(Collectors.toList());
	}

	@Override
	public List<Course> getApprovedFinalCourses(int docket) {
		final Student student = getByDocket(docket);
		final List<Course> courses = new LinkedList<>();

		for (Grade grade : student.getGrades()) {
			if (BigDecimal.valueOf(4).compareTo(grade.getGrade()) <= 0) {

				//Course approved, check the final grade
				for (FinalGrade finalGrade : grade.getFinalGrades()) {
					if (BigDecimal.valueOf(4).compareTo(finalGrade.getGrade()) <= 0) {
						//Subject entirely approved
						courses.add(grade.getCourse());
					}
				}

			}
		}

		return courses;
	}

	@Override
	public void addStudentFinalInscription(Student student, FinalInscription finalInscription) {
		final FinalInscription inscription = courseDao.getFinalInscription(finalInscription.getId()); // Needed to access History

		inscription.getHistory().add(student);
		inscription.increaseVacancy();
		em.merge(inscription);
	}

	@Override
	public void deleteStudentFinalInscription(Student student, FinalInscription finalInscription) {
		final FinalInscription inscription = courseDao.getFinalInscription(finalInscription.getId()); // Needed to access History

		if (inscription.getHistory().contains(student)) {
			inscription.getHistory().remove(student);
			inscription.decreaseVacancy();
			em.merge(inscription);
		}
	}

}

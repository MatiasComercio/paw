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
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Repository
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
		if (students.isEmpty()) {
			return null;
		}

		return students.get(FIRST).getStudentCourses();
	}

	@Override
	public boolean create(final Student student) {
		em.persist(student);
		LOGGER.debug("[create] - {}", student);

		return true;
	}

	@Override
	public boolean update(final Student student) {
		final Student s = getByDni(student.getDni());
		final List<Course> courses = getStudentCourses(s.getDocket());
		student.setStudentCourses(courses);
		em.merge(student);
		LOGGER.debug("[update] - {}", student);

		return true;
	}

	@Override
	public boolean deleteStudent(final int docket) {
		final Student student = getByDocket(docket);
		em.remove(student);
		LOGGER.debug("[delete] - {}", student);

		return true;
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
	public boolean addGrade(final Grade grade) {
		em.persist(grade);
		LOGGER.debug("[addGrade] - {}", grade);

		return true;
	}

	@Override
	public boolean addFinalGrade(final Grade grade, final FinalGrade finalGrade) {
		//TODO: Test this
        LOGGER.info("Will try to persist {}", finalGrade);
		em.persist(finalGrade);
		LOGGER.debug("[addFinalGrade] - {} - {}", grade, finalGrade);

		grade.getFinalGrades().add(finalGrade);

		return true;
	}

	@Override
	public boolean editGrade(final Grade newGrade, final BigDecimal oldGrade) {
		em.merge(newGrade);
//		final CriteriaBuilder cb = em.getCriteriaBuilder();
//		final CriteriaUpdate<Grade> query = cb.createCriteriaUpdate(Grade.class);
//		final Root<Grade> root = query.from(Grade.class);
//		private final List<Predicate> predicates;
//		final Predicate pCourseId = builder.equal(root.get("id"), id);

		LOGGER.debug("[editGrade] - {}", newGrade);
		return true;
	}

	@Override
	public boolean enroll(final int studentDocket, final int courseId) {
		final Student student = getByDocket(studentDocket);
		final Course course = courseDao.getById(courseId);
		final List<Course> studentCourses = student.getStudentCourses();

		studentCourses.add(course);
		em.merge(student);

		return true;
	}

	@Override
	public boolean unenroll(final int studentDocket, final int courseId) {
		final Student student = getByDocket(studentDocket);
		final Course course = courseDao.getById(courseId);
		final List<Course> studentCourses = student.getStudentCourses();

		studentCourses.remove(course);
		em.merge(student);
		return true;
	}

    //Modified to return courses that are approved and less than 3 finals are taken, or there's a final passed
    //TODO:TEST
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
//		for (Grade grade : approvedGrades) {
//			courses.add(grade.getCourse());
//		}
//
//		return courses;
		final Student student = getByDocket(docket);
		if (student == null) {
			return null;
		}
		final List<Course> courses = new LinkedList<>();
		for (Grade grade : student.getGrades()) {
			if (BigDecimal.valueOf(4).compareTo(grade.getGrade()) <= 0) {
                if (grade.getFinalGrades().size() == MAX_FINALS_CHANCES){
                    for (FinalGrade finalGrade : grade.getFinalGrades()) {
                        if (BigDecimal.valueOf(4).compareTo(finalGrade.getGrade()) <= 0){
                            courses.add(grade.getCourse());
                        }
                    }
                } else {
                    courses.add(grade.getCourse());
                }
			}
		}

		return courses;
	}

    //Modified to return courses that are approved and less than 3 finals are taken, or there's a final passed
    //TODO:TEST
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
	public List<Student> getStudentsPassed(final int id) {

		//TODO: Add a Student variable instead of a docket in Grade model
		final List<Student> studentsPassed = new LinkedList<>();
		final TypedQuery<Integer> query =
				em.createQuery("select gr.student.docket from Grade as gr where gr.course.id = :id and gr.grade >= 4",
						Integer.class);

		query.setParameter("id", id);

		List<Integer> docketList = query.getResultList();

		for(Integer docket: docketList){
			studentsPassed.add(getByDocket(docket));
		}

		return studentsPassed;
	}

	@Override
	public boolean createProcedure(final Procedure procedure) {
		return procedureDao.createProcedure(procedure);
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
    //Return if the docket's student can take a final exam;
    public boolean isApproved(int docket, int courseId) {
        //TODO: This is doing 1 query per loop: Improve eficiency. (Use student's grades??)
        TypedQuery<Grade> query = em.createQuery("from Grade as gr where gr.course.id=:id and gr.student.docket = :docket" +
                " and gr.grade >= 4", Grade.class);
        query.setParameter("id", courseId);
        query.setParameter("docket", docket);
        List<Grade> approvedGrades = query.getResultList();

        //TODO:Improve this
        for (Grade grade : approvedGrades) {
            if (grade.getFinalGrades().size() == MAX_FINALS_CHANCES){

                //for (FinalGrade finalGrade : grade.getFinalGrades()) {
                //    if (BigDecimal.valueOf(4).compareTo(finalGrade.getGrade()) <= 0) {
                //        return true;
                //    }
                //}
                //return false;

            } else {
                for (FinalGrade finalGrade : grade.getFinalGrades()) {
                    if (BigDecimal.valueOf(4).compareTo(finalGrade.getGrade()) <= 0) {
                            return false;
                        }
                }
                return true;
            }
        }

        return false;
    }

	@Deprecated
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
	public FinalInscription getFinalInscription(int id) {
		return em.find(FinalInscription.class, id);
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

}

package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.shared.StudentFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private UserService userService;

	@Autowired
	private CourseService courseService;

	@Transactional
	@Override
	public Student getByDocket(final int docket) {
		return docket <= 0 ? null : studentDao.getByDocket(docket);
	}

	@Transactional
	@Override
	public List<Course> getStudentCourses(final int docket, final CourseFilter courseFilter) {
		if (docket <= 0) {
			return null;
		}

		List<Course> courses = studentDao.getStudentCourses(docket);
		if (courses == null) {
			return null;
		}

		List<Course> coursesFiltered = courseService.getByFilter(courseFilter);

		final List<Course> rCourses = new LinkedList<>();
		for (Course c : coursesFiltered) {
			if (courses.contains(c)) {
				rCourses.add(c);
			}
		}

		return rCourses;
	}

	@Transactional
	@Override
	public List<Student> getByFilter(final StudentFilter studentFilter) {
		return studentDao.getByFilter(studentFilter);
	}

	@Transactional
	@Override
	public Result create(final Student student) {
		student.setRole(Role.STUDENT);
		if (student.getEmail() == null || Objects.equals(student.getEmail(), "")) {
			student.setEmail(userService.createEmail(student));
		}
		return studentDao.create(student);
	}

	@Transactional
	@Override
	public Result deleteStudent(final int docket) {
		if(docket <= 0) {
			return Result.ERROR_DOCKET_OUT_OF_BOUNDS;
		}
		return studentDao.deleteStudent(docket);
	}

	@Transactional
	@Override
	public Result addGrade(final Grade grade) {
		if (grade == null) {
			return null;
		}

		Result result = studentDao.addGrade(grade);
		if (result != null && result.equals(Result.OK)) {
			result = studentDao.unenroll(grade.getStudentDocket(), grade.getCourseId());
		}
		return result;
	}

	@Transactional
	@Override
	public Result editGrade(final Grade newGrade, final BigDecimal oldGrade) {
		return studentDao.editGrade(newGrade, oldGrade);
	}

	@Transactional
	@Override
	public Result update(final int docket, final Student student) {
		return studentDao.update(student);
	}

    @Override
    public int getTotalPlanCredits() {
        return courseService.getTotalPlanCredits();
    }

	@Transactional
    @Override
    public Integer getPassedCredits(final int docket) {
        final List<Course> list = (List<Course>) getApprovedCourses(docket);

        Integer amount = 0;

        for (Course course : list) {
            amount += course.getCredits();
        }
        return amount;
    }

	@Transactional
    @Override
	public Student getGrades(final int docket) {
		return docket <= 0 ? null : studentDao.getGrades(docket);
	}

	@Transactional
	@Override
	public List<Course> getAvailableInscriptionCourses(final int docket, final CourseFilter courseFilter) {
		if (docket <= 0) {
			return null;
		}

		final List<Course> courses = courseService.getByFilter(courseFilter);

		if (courses == null) {
			return null;
		}

		Collection<Course> currentCourses = getStudentCourses(docket, null);
		if (currentCourses != null) {
			courses.removeAll(currentCourses);
		}

		Collection<Course> approvedCourses = getApprovedCourses(docket);
		if (currentCourses != null && approvedCourses != null) {
			courses.removeAll(approvedCourses);
		}

		return courses;
	}

	@Transactional
	@Override
	public Result enroll(final int studentDocket, final int courseId) {
		if (studentDocket <= 0 ) {
			return Result.ERROR_DOCKET_OUT_OF_BOUNDS;
		}
		if (courseId <= 0) {
			return Result.ERROR_ID_OUT_OF_BOUNDS;
		}

		Result result;

		if (!checkCorrelatives(studentDocket, courseId)) {
			return Result.ERROR_CORRELATIVE_NOT_APPROVED;
		}

		result = studentDao.enroll(studentDocket, courseId);
		if (result == null) {
			return Result.ERROR_UNKNOWN;
		}
		if (!result.equals(Result.OK)) {
			return result;
		}

		/* notifyInscription(studentDocket, courseId); mail +++xtodo */

		return result;
	}

	@Transactional
	@Override
	public boolean checkCorrelatives(final int docket, final int courseId) {
		List<Integer> correlatives = courseService.getCorrelatives(courseId);
        List<Integer> approvedCourses = studentDao.getApprovedCoursesId(docket);

        for (Integer correlative : correlatives){
            if (!approvedCourses.contains(correlative)){
                return false;
            }
        }

        return true;
	}

	@Transactional
	@Override
	public Collection<Collection<TranscriptGrade>> getTranscript(final int docket) {
		final List<Collection<TranscriptGrade>> semesters = new ArrayList<>();
		final int tSemesters = courseService.getTotalSemesters();
		int iSemester;

		// Load all semesters
		for (int i = 0 ; i < tSemesters; i++) {
			semesters.add(new LinkedList<>());
		}

		TranscriptGrade gradeForm;

		// add taken courses
		final Student student = getGrades(docket);
		final List<Grade> approved = student.getGrades();
		Course course;
		TranscriptGrade lastGrade;
		Map<Integer, TranscriptGrade> lastGrades = new HashMap<>();
		for (Grade g : approved) {
			course = g.getCourse();
			iSemester = course.getSemester() - 1;
			gradeForm = new TranscriptGrade();
			gradeForm.loadFromGrade(g);
			if ((lastGrade = lastGrades.get(course.getId())) != null) {
				if (lastGrade.getModified().compareTo(g.getModified()) < 0) {
					// there is a new grade that can be edited
					lastGrades.put(course.getId(), gradeForm);
					gradeForm.setCanEdit(true);
					lastGrade.setCanEdit(false);
				}
			} else {
				// this is the first grade for this course
				lastGrades.put(course.getId(), gradeForm);
				gradeForm.setCanEdit(true);
			}
			semesters.get(iSemester).add(gradeForm);
		}

		// add current courses
		final List<Course> current = getStudentCourses(docket, null);
		for (Course c : current) {
			iSemester = c.getSemester() - 1;
			gradeForm = new TranscriptGrade();
			gradeForm.setDocket(docket);
			gradeForm.setCourseId(c.getId());
			gradeForm.setCourseName(c.getName());
			gradeForm.setTaking(true);
			semesters.get(iSemester).add(gradeForm);
		}

		// add not taken courses
		final List<Course> future = getAvailableInscriptionCourses(docket, null);
		for (Course c : future) {
			iSemester = c.getSemester() - 1;
			gradeForm = new TranscriptGrade();
			gradeForm.setDocket(docket);
			gradeForm.setCourseId(c.getId());
			gradeForm.setCourseName(c.getName());
			semesters.get(iSemester).add(gradeForm);
		}

		return semesters;
	}

	@Transactional
	@Override
	public Result unenroll(final int studentDocket, final int courseId) {
		if (studentDocket <= 0 ) {
			return Result.ERROR_DOCKET_OUT_OF_BOUNDS;
		}
		if (courseId <= 0) {
			return Result.ERROR_ID_OUT_OF_BOUNDS;
		}

		Result result;

		result = studentDao.unenroll(studentDocket, courseId);
		if (result == null) {
			return Result.ERROR_UNKNOWN;
		}
		if (!result.equals(Result.OK)) {
			return result;
		}

		/* notifyUnenrollment(studentDocket, courseId); mail +++xtodo */
		return result;
	}

	@Transactional
	@Override
	public Collection<Course> getApprovedCourses(final int docket) {
		return docket <= 0 ? null : studentDao.getApprovedCourses(docket);
	}

	@Transactional
	@Override
	public Student getByDni(final int dni) {
		return dni <= 0 ? null : studentDao.getByDni(dni);
	}

	/* Test purpose only */
	/* default */ void setStudentDao(final StudentDao studentDao) {
		this.studentDao = studentDao;
	}
	/* Test purpose only */
	/* default */ void setCourseService(final CourseService courseService) {
		this.courseService = courseService;
	}

	@Transactional
	@Override
	public List<FinalInscription> getAvailableFinalInscriptions(int docket) {

		List<FinalInscription> finalInscriptions = new ArrayList<>();

		Student student = studentDao.getByDocket(docket);

		//TODO: Check correlativity
		for(FinalInscription inscription : studentDao.getAllFinalInscriptions()){
			if(studentDao.isApproved(docket, inscription.getCourse().getId()) &&
					!inscription.getStudents().contains(student)){
				finalInscriptions.add(inscription);
			}
		}
		return finalInscriptions;
	}

	@Transactional
	@Override
	public FinalInscription getFinalInscription(int id) {
		return studentDao.getFinalInscription(id);
	}

	@Transactional
	@Override
	public void addStudentFinalInscription(int docket, int finalInscriptionId) {
		FinalInscription finalInscription = studentDao.getFinalInscription(finalInscriptionId);
		finalInscription.getStudents().add(studentDao.getByDocket(docket));

	}
}

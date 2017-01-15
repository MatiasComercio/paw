package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
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
	public boolean create(final Student student) {
		student.setRole(Role.STUDENT);
		if (student.getEmail() == null || Objects.equals(student.getEmail(), "")) {
			student.setEmail(userService.createEmail(student));
		}
		if(student.getAddress() != null){
			student.getAddress().setDni(student.getDni());
		}
		return studentDao.create(student);
	}

	@Transactional
	@Override
	public boolean deleteStudent(final int docket) {
		return studentDao.deleteStudent(docket);
	}

	@Transactional
	@Override
	public boolean addGrade(final Grade grade) {
		boolean done = studentDao.addGrade(grade);
		if (done) {
			done = studentDao.unenroll(grade.getStudentDocket(), grade.getCourseId());
		}
		return done;
	}

	@Transactional
	@Override
	public boolean editGrade(final Grade newGrade, final BigDecimal oldGrade) {
		return studentDao.editGrade(newGrade, oldGrade);
	}

	@Transactional
	@Override
	public boolean update(final Student student, final Student oldStudent) {

		/* Set Remaining information that cannot be updated by the user via this method */
		student.setDocket(oldStudent.getDocket());
		student.setId_seq(oldStudent.getId_seq());
		student.setAddress(oldStudent.getAddress());
		if(student.getAddress() != null){
			student.getAddress().setDni(student.getDni());
		}
		student.setPassword(oldStudent.getPassword());
		student.setEmail(oldStudent.getEmail());
		student.setRole(oldStudent.getRole());

		/* Set student courses */
		final List<Course> courses = studentDao.getStudentCourses(oldStudent.getDocket());
		student.setStudentCourses(courses);

		return studentDao.update(student);
	}

	@Transactional
	@Override
	public void editAddress(final int docket, final Address address) {
		if(address == null){
			return;
		}
    final Student student = getByDocket(docket);
    final Address oldAddress = student.getAddress();

		address.setDni(student.getDni());
    if(oldAddress != null) {
			address.setId_seq(oldAddress.getId_seq());
		}

    studentDao.editAddress(address);
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

	@Override
	public boolean existsEmail(final String email) {
		return userService.existsEmail(email);
	}

	public boolean createProcedure(final Procedure procedure) {
		return studentDao.createProcedure(procedure);
	}

	@Transactional
	@Override
	public List<Procedure> getProcedures(final int docket) {
		final Student student = getByDocket(docket);

		return student.getProcedures();
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
	public boolean enroll(final int studentDocket, final String courseId) {
		boolean result;

		if (!checkCorrelatives(studentDocket, courseId)) {
//			return Result.ERROR_CORRELATIVE_NOT_APPROVED;
			return false;
		}

		result = studentDao.enroll(studentDocket, courseId);

		return result;
//		if (result == null) {
//			return Result.ERROR_UNKNOWN;
//		}
//		if (!result.equals(Result.OK)) {
//			return result;
//		}

		/* notifyInscription(studentDocket, courseId); mail +++xtodo */
	}

	@Transactional
	@Override
	public boolean checkCorrelatives(final int docket, final String courseId) {
		List<String> correlatives = courseService.getCorrelatives(courseId);
		List<Integer> approvedCourses = studentDao.getApprovedCoursesId(docket);

		for (String correlative : correlatives){
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
			gradeForm.setCourseCodId(c.getCourseId());
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
			gradeForm.setCourseCodId(c.getCourseId());
			gradeForm.setCourseName(c.getName());
			semesters.get(iSemester).add(gradeForm);
		}

		return semesters;
	}

	@Transactional
	@Override
	public boolean unenroll(final int studentDocket, final int courseId) {
		final boolean done = studentDao.unenroll(studentDocket, courseId);

		/* notifyUnenrollment(studentDocket, courseId); mail +++xtodo */

		return done;
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

		//inscription.getVacancy() < inscription.getMaxVacancy() The inscription should be visible even when full
		for(FinalInscription inscription : studentDao.getAllFinalInscriptionsFromOpenInstance()){
			if(studentDao.isApproved(docket, inscription.getCourse().getId()) &&
							//checks if student is not already included in the current final inscription.
							!inscription.getStudents().contains(student) && inscription.isOpen()){
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
	public boolean addStudentFinalInscription(int docket, int finalInscriptionId) {

		FinalInscription finalInscription = studentDao.getFinalInscription(finalInscriptionId);
		Student student = studentDao.getByDocket(docket);

		if (!checkFinalCorrelatives(docket, finalInscription.getCourse().getCourseId())){
			return false;
		}

		if (finalInscription.getVacancy() >= finalInscription.getMaxVacancy()){
			return false;
		}

		//Checks that the student was not in this inscription or another for this course
		List<FinalInscription> finalInscriptionsForCourse = studentDao.getOpenFinalInscriptionsByCourse(finalInscription.getCourse());

		for (FinalInscription inscription : finalInscriptionsForCourse) {
			if (inscription.getHistory().contains(student))
				return false;
		}


		finalInscription.getStudents().add(student);
		finalInscription.getHistory().add(student);
		finalInscription.increaseVacancy();
		return true;
	}

	@Transactional
	@Override
	public List<FinalInscription> getFinalInscriptionsTaken(int docket) {
		final List<FinalInscription> finalInscriptionsTaken = new ArrayList<>();
		final Student student = studentDao.getByDocket(docket);
		final List<FinalInscription> finalInscriptions = studentDao.getAllFinalInscriptionsFromOpenInstance();

		for (FinalInscription inscription : finalInscriptions) {
			if (inscription.getStudents().contains(student))
				finalInscriptionsTaken.add(inscription);
		}

		return finalInscriptionsTaken;

	}

	@Transactional
	@Override
	public boolean deleteStudentFinalInscription(int docket, int finalInscriptionId) {
		FinalInscription finalInscription = studentDao.getFinalInscription(finalInscriptionId);
		Student student = studentDao.getByDocket(docket);

		if (finalInscription.getStudents().contains(student)) {
			finalInscription.getStudents().remove(student);
			finalInscription.getHistory().remove(student);
			finalInscription.decreaseVacancy();
		}

		return true;
	}

	@Transactional
	@Override
	public boolean checkFinalCorrelatives(int docket, String courseId) {

		final List<Course> correlatives = courseService.getCorrelativesByFilter(courseId, null);
		final List<Course> approvedCourses = studentDao.getApprovedFinalCourses(docket);

		if (approvedCourses.containsAll(correlatives))
			return true;
		return false;

	}

	@Transactional
	@Override
	public Set<Student> getFinalStudents(int id) {
		Set<Student> set = new HashSet<>();
		set.addAll(studentDao.getFinalInscription(id).getHistory());
		return set;
	}

	@Transactional
	@Override
	public boolean addFinalGrade(Integer id, Integer docket, BigDecimal grade) {
		final FinalInscription fi = studentDao.getFinalInscription(id);
		final Student student = studentDao.getByDocket(docket);

		for (Grade grade1 : student.getGrades()) {
			if (grade1.getCourse().equals(fi.getCourse()) && BigDecimal.valueOf(4).compareTo(grade1.getGrade()) <= 0 && grade1.getFinalGrades().size() < 3){

				FinalGrade fg = new FinalGrade.Builder(null, grade).build();
				studentDao.addFinalGrade(grade1, fg);

				//Remove student inscription to final exam
				fi.getStudents().remove(student);
				return true;
			}

		}

		return false;
	}
}

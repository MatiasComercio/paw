package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.CourseService;
import ar.edu.itba.paw.interfaces.StudentDao;
import ar.edu.itba.paw.interfaces.StudentService;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Grade;
import ar.edu.itba.paw.models.Role;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.shared.StudentFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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
		courses.retainAll(coursesFiltered);

		return courses;
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
		final Integer dni = studentDao.getDniByDocket(docket);

        if (dni == null)
            return Result.STUDENT_NOT_EXISTS;

		if (studentDao.hasAddress(dni)){
            studentDao.updateAddress(dni, student);
		} else {
            studentDao.createAddress(dni, student);
		}

		return studentDao.update(docket, dni, student);
	}

    @Override
    public int getTotalPlanCredits() {
        return courseService.getTotalPlanCredits();
    }

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
		if (currentCourses != null) {
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
	public List<List<Grade>> getTranscript(final int docket) {
		final List<List<Grade>> semesterList = new ArrayList<>();
		final Integer totalSemesters = courseService.getTotalSemesters();


		for (int i = 0; i < totalSemesters; i++) {
            int semesterIndex = i + 1;

            //Get all grades from that semester and add them
            Student student = studentDao.getGrades(docket, semesterIndex);

            semesterList.add(i, new ArrayList<>());

            for (Grade grade : student.getGrades()) {
                semesterList.get(i).add(grade);
            }
		}

        //Add courses that are being taken
        final List<Course> coursesTaken = getStudentCourses(docket, null);
        for (Course course : coursesTaken){
            int semesterIndex = course.getSemester() - 1;
            semesterList.get(semesterIndex).add(new Grade.Builder(docket, course.getId(), null).courseName(course.getName()).taking(true).build());
        }

        //Complete with the rest of the courses that are not taken
        final List<Course> availCourses = getAvailableInscriptionCourses(docket, null);

        for (Course course : availCourses){
            int semesterIndex = course.getSemester() - 1;
            semesterList.get(semesterIndex).add(new Grade.Builder(docket, course.getId(), null).courseName(course.getName()).build());
        }

		return semesterList;
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
}

package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.users.Student;
import java.util.List;

public interface StudentService {

	/**
	 * Gets the student with the given docket.
	 * If no student exists with that username, null is returned.
	 * @param docket The student's docket
	 * @return The student with the given docket, if exists; null otherwise.
	 */
	Student getByDocket(final int docket);
	List<Student> getAll();

	List<Course> getStudentCourses(final int docket);
}

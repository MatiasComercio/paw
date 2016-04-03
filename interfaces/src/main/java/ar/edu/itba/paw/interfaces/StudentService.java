package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.StudentFilter;

import java.util.List;

public interface StudentService {

	/**
	 * Gets the student's main data that matches the given docket.
	 * If no student exists with that docket, null is returned.
	 * @param docket The student's docket
	 * @return The student with the given docket, if exists; null otherwise.
	 */
	Student getByDocket(final int docket);

	/**
	 * Gets all the students within the database
	 * @return all the students within the database
	 */
	List<Student> getAll();

	/**
	 * Gets the student with the given docket containing all the grades of the courses they took.
	 * If no student exists with that docket, null is returned.
	 * @param docket The student's docket
	 * @return The student with the given docket, if exists; null otherwise.
	 */
	Student getGrades(final int docket);

	List<Course> getStudentCourses(final int docket);

	List<Student> getByFilter(StudentFilter studentFilter);
}

package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.shared.StudentFilter;

import java.time.LocalDate;
import java.util.List;

public interface StudentDao {

	/**
	 * Gets the student with the given docket.
	 * If no student exists with that username, null is returned.
	 * @param docket The student's docket
	 * @return The student with the given docket, if exists; null otherwise.
	 */
	Student getByDocket(final int docket);

	/**
	 * Gets the student with the given docket containing all the grades of the courses they took.
	 * If no student exists with that docket, null is returned.
	 * @param docket The student's docket
	 * @return The student with the given docket, if exists; null otherwise.
	 */
	Student getGrades(int docket);

	/**
	 * Gets the courses which match to a student, given a docket.
	 * @param docket The student's docket
	 * @return the list of courses, if the student exists. If the docket doesn't match to a student,
	 * it returns null
	 */
	List<Course> getStudentCourses(final int docket);

	/**
	 *
	 * @param student The student to be persisted in the database.
     */
	void create(Student student);


	/**
	 * Gets the students that comply to a list of filters
	 * @param studentFilter The list of filters to apply
	 * @return the list of students that match the list of filters. If no student matches the filters, it returns
	 * an empty list.
	 */
	List<Student> getByFilter(StudentFilter studentFilter);

	/**
	 * Enrolls the student with the given docket into the course with the specified id.
	 *
	 * @param studentDocket The student's docket
	 * @param courseId The course id
	 * @return a Result object containing information of the operation carried out
	 */
	Result enroll(final int studentDocket, final int courseId);
}

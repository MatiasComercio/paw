package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Grade;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.shared.StudentFilter;

import java.math.BigDecimal;
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
	 * Gets the student with the given docket containing all the grades of the courses they took.
	 * If no student exists with that docket, null is returned.
	 * @param docket The student's docket
	 * @return The student with the given docket, if exists; null otherwise.
	 */
	Student getGrades(final int docket);

	/**
	 * Gets the courses which match to a student, given a docket.
	 * @param docket The student's docket
	 * @return the list of courses, if the student exists. If the docket doesn't match to a student,
	 * it returns null
     */
	List<Course> getStudentCourses(final int docket);

	/**
	 * Gets the students that comply to a list of filters
	 * @param studentFilter The list of filters to apply
	 * @return the list of students that match the list of filters. If no student matches the filters, it returns
	 * an empty list.
     */
	List<Student> getByFilter(StudentFilter studentFilter);


	/**
	 *
	 * @param student The student to be persisted in the database.
	 * @return The Result code of the insertion
	 */
	Result create(Student student);

	/**
	 * Update student
	 * @param docket  The docket of the old student
	 * @param student The new student
	 * @return The Result code of update
	 */
	Result update(final Integer docket, final Student student);

	/**
	 * Delete the student that matches the given docket.
	 * @param docket The student's docket
	 * @return 	OK if the student was deleted;
	 * 		ERROR_DOCKET_OUT_OF_BOUNDS if the docket is invalid;
	 * 		ERROR_UNKNOWN else;
     */
	Result deleteStudent(Integer docket);

	/**
	 * Add the grade for a given student and course;
	 * @param grade which contains the student docket, the course id and the grade
	 * @return OK if the grade was added;
	 * 		INVALID_INPUT_PARAMETERS if one or more parameters are invalid;
	 * 		ERROR_UNKNOWN else;
     */
	Result addGrade(Grade grade);

	/**
	 * @param newGrade The new grade values
	 * @param oldGrade The grade to be updated
	 * @return The result code of the Update
     */
	Result editGrade(Grade newGrade, BigDecimal oldGrade);
}

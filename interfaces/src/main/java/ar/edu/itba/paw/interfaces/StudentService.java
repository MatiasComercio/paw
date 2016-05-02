package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Grade;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.shared.StudentFilter;

import java.math.BigDecimal;
import java.util.Collection;
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
	 * Gets the list of the courses the student with the given docket is enroll in,
	 * applying the specified courseFilter
	 * @param docket The student's docket
	 * @param courseFilter The course's filter. If null, no filter is applied.
	 * @return the list of courses, if the student exists. If the docket doesn't match to a student,
	 * it returns null
     */
	List<Course> getStudentCourses(final int docket, final CourseFilter courseFilter);

	/**
	 * Gets the students that comply to a list of filters
	 * @param studentFilter The list of filters to apply
	 * @return the list of students that match the list of filters. If no student matches the filters, it returns
	 * an empty list.
     */
	List<Student> getByFilter(final StudentFilter studentFilter);


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
	 * Add the grade for a given student and course. If success, the student is unenrolled from the given course.
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
	/**
	 * Gets the list of the courses the student with the given docket can enroll in,
	 * applying the specified courseFilter
	 * @param docket The student's docket
	 * @param courseFilter The course's filter. If null, no filter is applied.
	 * @return The list of the courses the student with the given docket can enroll in;
	 *          an empty list will be returned if no course is available
	 */
	List<Course> getAvailableInscriptionCourses(final int docket, final CourseFilter courseFilter);

	/**
	 * Enrolls the student with the given docket into the course with the specified id,
	 * if the student has already approved all its corresponding correlatives.
	 *
	 * @param studentDocket The student's docket
	 * @param courseId The course id
	 * @return a Result object containing information of the operation carried out
	 */
	Result enroll(final int studentDocket, final int courseId);

	/**
	 * Unenrolls the student with the given docket of the course with the specified id.
	 *
	 * @param studentDocket The student's docket
	 * @param courseId The course id
	 * @return a Result object containing information of the operation carried out
	 */
	Result unenroll(final Integer studentDocket, final Integer courseId);

	/**
	 * Gets the collection of courses the student already approved.
	 * @param docket The student's docket
	 * @return The collection of the courses the student with the given docket has already approved;
	 *          an empty collection will be returned if no course was approved
	 */
	Collection<Course> getApprovedCourses(int docket);

	/**
	 * Check if the student corresponding to docket has approved all the necessary
	 * courses to take the course corresponding to the courseId
	 * @param docket The docket of the student
	 * @param courseId The id of the course
     * @return True if the student can take de course, False if not.
     */
	boolean checkCorrelatives(final Integer docket, final Integer courseId);
}

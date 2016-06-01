package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.FinalGrade;
import ar.edu.itba.paw.models.FinalInscription;
import ar.edu.itba.paw.models.Grade;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.shared.StudentFilter;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface StudentDao {

	/**
	 * Gets the student with the given docket.
	 * If no student exists with that username, null is returned.
	 * @param docket The student's docket
	 * @return The student with the given docket, if exists; null otherwise.
	 */
	Student getByDocket(int docket);

	/**
	 * Gets the student with the given docket containing all the grades of the courses it took.
	 * If no student exists with that docket, null is returned.
	 * @param docket The student's docket
	 * @return The student with the given docket, if exists; null otherwise.
	 */
	Student getGrades(int docket);

	/**
	 * Gets the student with the given docket containing all the grades of the courses it took in a specified semester.
	 * If no student exists with that docket, null is returned.
	 *
	 * @param docket The student's docket
	 * @param semesterIndex The number of semester
	 * @return The student with the given docket, if exists; null otherwise.
     */
	Student getGrades(int docket, int semesterIndex);

	/**
	 * Gets the courses which match to a student, given a docket.
	 * @param docket The student's docket
	 * @return the list of courses, if the student exists. If the docket doesn't match to a student,
	 * it returns null
	 */
	List<Course> getStudentCourses(int docket);

	/**
	 *
	 * @param student The student to be persisted in the database.
	 * @return The Result code of the insertion
     */
	Result create(Student student);

	/**
	 * Update student
	 * @param student The new student
	 * @return The Result code of update
	 */
	Result update(Student student);

	/**
	 * Gets the students that comply to a list of filters
	 * @param studentFilter The list of filters to apply
	 * @return the list of students that match the list of filters. If no student matches the filters, it returns
	 * an empty list.
	 */
	List<Student> getByFilter(StudentFilter studentFilter);


	/**
	 * Delete the student that matches the given docket.
	 * @param docket The student's docket
	 * @return 	OK if the student was deleted;
	 * 		ERROR_UNKNOWN else;
	 */
	Result deleteStudent(int docket);

	/**
	 * Add the grade for a given student and course;
	 * @param grade which contains the student docket, the course id and the grade
	 * @return OK if the grade was added;
	 * 		INVALID_INPUT_PARAMETERS if one or more parameters are invalid;
	 * 		ERROR_UNKNOWN else;
	 */
	Result addGrade(Grade grade);

	/**
	 * Add the final grade for a given student and course;
	 * @param grade which contains the student docket, the course id and the grade
	 * @param finalGrade Final grade to asociate with the
	 * @return OK if the grade was added;
	 * 		INVALID_INPUT_PARAMETERS if one or more parameters are invalid;
	 * 		ERROR_UNKNOWN else;
	 */
	Result addFinalGrade(Grade grade, FinalGrade finalGrade);

	/**
	 * @param newGrade The new grade values
	 * @param oldGrade The grade to be updated
	 * @return The result code of the Update
	 */
	Result editGrade(Grade newGrade, BigDecimal oldGrade);

	/**
	 * Enrolls the student with the given docket into the course with the specified id.
	 *
	 * @param studentDocket The student's docket
	 * @param courseId The course id
	 * @return a Result object containing information of the operation carried out
	 */
	Result enroll(int studentDocket, int courseId);

	/**
	 * Unenrolls the student with the given docket of the course with the specified id.
	 *
	 * @param studentDocket The student's docket
	 * @param courseId The course id
	 * @return a Result object containing information of the operation carried out
	 */
	Result unenroll(int studentDocket, int courseId);

	/**
	 * Gets the collection of courses the student already approved.
	 * @param docket The student's docket
	 * @return The collection of the courses the student with the given docket has already approved;
	 *          an empty collection will be returned if no course was approved
	 */
	Collection<Course> getApprovedCourses(int docket);

    /**
     * Gets the list of ids corresponding to the courses the student already approved.
     * @param docket The student's docket
     * @return The list of ids corresponding to the courses the student with the given docket has already approved;
     *          an empty List will be returned if no course was approved
     */
    List<Integer> getApprovedCoursesId(int docket);

	/**
	 * Gets the student's main data that matches the given dni.
	 * If no student exists with that dni, null is returned.
	 * @param dni The student's dni
	 * @return The student with the given dni, if exists; null otherwise.
	 */
	Student getByDni(int dni);

	/* +++xtest */
	/* +++xdocument */
	List<Student> getStudentsPassed(int id);


	/**
	 *
	 * @param docket The student's docket
	 * @param courseId The course's id
	 * @return A boolean indicating whether the given student passed the given course
     */
	boolean isApproved(int docket, int courseId);

	/**
	 *
	 * @return All the final inscriptions available
     */
	List<FinalInscription> getAllFinalInscriptions();

	FinalInscription getFinalInscription(int id);
}

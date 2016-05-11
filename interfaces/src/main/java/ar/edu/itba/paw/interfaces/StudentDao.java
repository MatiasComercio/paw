package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.Grade;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.Result;
import ar.edu.itba.paw.shared.StudentFilter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
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
	Student getGrades(Integer docket, Integer semesterIndex);

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
	 * @return The Result code of the insertion
     */
	Result create(Student student);

	/**
	 * Update student
	 * @param docket  The docket of the old student
	 * @param student The new student
	 * @return The Result code of update
	 */
	Result update(final Integer docket, final Integer dni, final Student student);

	/**
	 * Gets the students that comply to a list of filters
	 * @param studentFilter The list of filters to apply
	 * @return the list of students that match the list of filters. If no student matches the filters, it returns
	 * an empty list.
	 */
	List<Student> getByFilter(StudentFilter studentFilter);

    /**
     * Get student's dni
     * @param docket docket of specified student;
     * @return Returns dni of specified student or null if not exists
     */
    Integer getDniByDocket(final Integer docket);

	/**
	 * Delete the student that matches the given docket.
	 * @param docket The student's docket
	 * @return 	OK if the student was deleted;
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

	/**
	 * Enrolls the student with the given docket into the course with the specified id.
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
	Collection<Course> getApprovedCourses(final int docket);

    /**
     * Gets the list of ids corresponding to the courses the student already approved.
     * @param docket The student's docket
     * @return The list of ids corresponding to the courses the student with the given docket has already approved;
     *          an empty List will be returned if no course was approved
     */
    List<Integer> getApprovedCoursesId(final int docket);

	/**
	 * Returns true if there's an Address asociated with the student
	 * @param dni The student's docket
	 * @return Address of the specified student or null
     */
	boolean hasAddress(final Integer dni);

    /**
     * Create address of specified student
     * @param dni The student's dni
     * @param student The student containing the address to create
     */
    void createAddress(final Integer dni, final Student student);

    /**
     * Create address of specified student
     * @param dni The student's dni
     * @param student The student containing the address to update
     */
    void updateAddress(final Integer dni, final Student student);

	/**
	 * Gets the student's main data that matches the given dni.
	 * If no student exists with that dni, null is returned.
	 * @param dni The student's dni
	 * @return The student with the given dni, if exists; null otherwise.
	 */
	Student getByDni(final int dni);

	/* +++xtest */
	/* +++xdocument */
	List<Student> getStudentsPassed(int id);
}

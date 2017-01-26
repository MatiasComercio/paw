package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.StudentFilter;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

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
		* @return true if the student was inserted; else false
		*/
	boolean create(Student student);

	/**
		* Update student
		* @param student The new student
		* @return The Result code of update
		*/
	boolean update(Student student);

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
	boolean deleteStudent(int docket);

	/**
		*
		* @param address The new address to be persisted
		*/
	void editAddress(Address address);

	/**
		* Add the grade for a given student and course;
		* @param grade which contains the student docket, the course id and the grade
		* @return the created grade's id
		*/
	int addGrade(Grade grade);

	/**
		* Add the final grade for a given student and course;
		* @param grade which contains the student docket, the course id and the grade
		* @param finalGrade Final grade to asociate with the
		*/
	void addFinalGrade(Grade grade, FinalGrade finalGrade);

	/**
		* @param newGrade The new grade values
		* @param oldGrade The grade to be updated
		* @return The result code of the Update
		*/
	boolean editGrade(Grade newGrade, BigDecimal oldGrade);

	/**
		* Enrolls the student with the given docket into the course with the specified id.
		* @param studentDocket The student's docket
		* @param courseId The course id
		*/
	void enroll(int studentDocket, String courseId);

	/**
		* Unenrolls the student with the given docket of the course with the specified id.
		*
		* @param studentDocket The student's docket
		* @param courseId The course id
		*/
	void unenroll(int studentDocket, int courseId);

	/**
		* Gets the list of courses the student already approved. This means: a) the course is approved and not
		* all 3 finals have been taken (i.e. the student doesn't have to take the course again yet);
		* b) the course is approved and at least one final exam was approved
		*
		* @param docket The student's docket
		* @return The collection of the courses the student with the given docket has already approved;
		* an empty collection will be returned if no course was approved
		*/
	List<Course> getApprovedCourses(int docket);

	/**
		* Get the collection of the courses the student approved including final exam
		* @param docket The student's docket
		* @return The List of the courses the student with the given docket has already approved;
		*          an empty collection will be returned if no course was approved
		*/
	List<Course> getApprovedFinalCourses(int docket);

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

	/**
		*
		* @param docket The student's docket
		* @param courseId The course's id
		* @return A boolean indicating whether the given student can take the course's final exam
		*/
	boolean canTakeCourseFinal(int docket, int courseId);

	/**
		*
		* @return All the final inscriptions available
		*/
	List<FinalInscription> getAllFinalInscriptions();

	/**
		* Gets the final inscription list from the open FinalInstance.
		*
		* This was implemented in order to make a distinction between
		* final exam opportunities in different parts of the year, making possible
		* to avoid having students in multiple inscriptions for the same Course and
		* in the same FinalInstance.
		*
		* The idea is that only one FinalInstance is open at the same time for this
		* system to work correctly. Moreover, on the real SGA system there are not
		* multiple instances open.
		*
		* @return The list containing the final inscriptions
		*/
	List<FinalInscription> getAllFinalInscriptionsFromOpenInstance();

	/**
		*
		* @param procedure
		* @return
		*/
	boolean createProcedure(Procedure procedure);

	/**
		* Gets the final inscriptions for a course corresponding to the open final instance.
		*
		* @param course The course corresponding to de final inscriptions
		* @return
		*/
	List<FinalInscription> getOpenFinalInscriptionsByCourse(Course course);

	/**
		* Enrolls the given student into the final inscription
		* @param student The student
		* @param finalInscription The final inscription
		*/

	void addStudentFinalInscription(Student student, FinalInscription finalInscription);

	/**
		* Remove the given student from the Final Inscription instance
		*
		* @param student The student
		* @param finalInscription The final inscription
		*/
	void deleteStudentFinalInscription(Student student, FinalInscription finalInscription);
}

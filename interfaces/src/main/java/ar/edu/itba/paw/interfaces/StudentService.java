package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.models.*;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.shared.CourseFilter;
import ar.edu.itba.paw.shared.StudentFilter;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface StudentService {

    /**
     * Gets the students that comply to a list of filters
     *
     * @param studentFilter The list of filters to apply
     * @return the list of students that match the list of filters. If no student matches the filters, it returns
     * an empty list.
     */
    List<Student> getByFilter(StudentFilter studentFilter);

    /**
     * @param student The student to be persisted in the database.
     * @return true if the student was inserted; else false
     */
    boolean create(Student student);

    /**
     * Gets the student's main data that matches the given docket.
     * If no student exists with that docket, null is returned.
     *
     * @param docket The student's docket
     * @return The student with the given docket, if exists; null otherwise.
     */
    Student getByDocket(int docket);

    /**
     * Update student
     *
     * @param newStudent The new student
     * @param oldStudent The old student
     * @return true if the student was updated; else false
     */
    boolean update(Student newStudent, Student oldStudent);

    /**
     * Delete the student that matches the given docket.
     *
     * @param docket The student's docket
     * @return true if the student was deleted; false else
     */
    boolean deleteStudent(int docket);

    /**
     *
     * @param student The docket of the student whose address will be edited. It is assumed that the student exists.
     * @param address The new address
     */
    void editAddress(int student, Address address);

    /**
     * Gets the student with the given docket containing all the grades of the courses they took.
     * If no student exists with that docket, null is returned.
     *
     * @param docket The student's docket
     * @return The student with the given docket, if exists; null otherwise.
     */
    Student getGrades(int docket);

    /**
     * Add the grade for a given student and course. If success, the student is unenrolled from the given course.
     *
     * @param grade which contains the student docket, the course id and the grade
     * @return the new grade's id
     */
    int addGrade(Grade grade);

    /**
     * @param newGrade The new grade values
     * @param oldGrade The grade to be updated
     * @return The result code of the Update
     */
    boolean editGrade(Grade newGrade, BigDecimal oldGrade);


    /**
     * Gets the list of the courses the student with the given docket is enroll in,
     * applying the specified courseFilter
     *
     * @param docket       The student's docket
     * @param courseFilter The course's filter. If null, no filter is applied.
     * @return the list of courses, if the student exists. If the docket doesn't match to a student,
     * it returns null
     */
    List<Course> getStudentCourses(int docket, CourseFilter courseFilter);



    /**
     * Gets the list of the courses the student with the given docket can enroll in,
     * applying the specified courseFilter
     *
     * @param docket       The student's docket
     * @param courseFilter The course's filter. If null, no filter is applied.
     * @return The list of the courses the student with the given docket can enroll in;
     * an empty list will be returned if no course is available
     */
    List<Course> getAvailableInscriptionCourses(int docket, CourseFilter courseFilter);


    /**
     * Enrolls the given student into the specified course,
     * if the student has already approved all its corresponding correlatives.
     * @param student The student
     * @param course The course
     * @return True if the enroll was made, false otherwise
     */
    boolean enroll(Student student, Course course);

    /**
     * Unenrolls the student with the given docket of the course with the specified id.
     *
     * @param studentDocket The student's docket
     * @param courseId      The course id
     */
    void unenroll(int studentDocket, int courseId);

    /**
     * Gets the list of courses the student already approved. This means: a) the course is approved and not all
     * all 3 finals have been failed (i.e. the student doesn't have to take the course again);
     * b) the course is approved and at least one final exam was approved
     *
     * @param docket The student's docket
     * @return The collection of the courses the student with the given docket has already approved;
     * an empty collection will be returned if no course was approved
     */
    List<Course> getApprovedCourses(int docket);


    /**
     * Gets the student's main data that matches the given dni.
     * If no student exists with that dni, null is returned.
     *
     * @param dni The student's dni
     * @return The student with the given dni, if exists; null otherwise.
     */
    Student getByDni(int dni);

    /**
     * Check if the student corresponding to docket has approved all the necessary
     * courses to take the course corresponding to the courseId
     *
     * @param docket   The docket of the student
     * @param courseId The id of the course
     * @return True if the student can take de course, False if not.
     */
    boolean checkCorrelatives(int docket, String courseId);

    /**
     * Check if the student corresponding to docket has approved all the necessary
     * courses and finals to take the final exame of the course corresponding to
     * the courseId
     *
     * @param docket   The docket of the student
     * @param courseId The id of the course
     * @return True if the student can take de course, False if not.
     */
    boolean checkFinalCorrelatives(int docket, String courseId);

    /**
     * Get the representation of a student's transcript
     *
     * @param docket
     * @return A list containing lists in which all the grades of a semester are placed
     */
    Collection<Collection<TranscriptGrade>> getTranscript(int docket);

    /**
     * Get the total credits of the plan.
     *
     * @return Integer indicating the total credits of the plan.
     */
    int getTotalPlanCredits();

    /**
     * Get the total amount of passed credits given a student.
     *
     * @param docket The student's docket.
     * @return Integer indicating the amount of credits passed.
     */
    Integer getPassedCredits(int docket);

    /**
     * Get the final exams inscriptions available for the given student
     *
     * @param student The student.
     * @return The final inscriptions
     */
    List<FinalInscription> getAvailableFinalInscriptions(Student student);

    /**
     * Adds the given student to the Final Inscription instance
     * @param student
     * @param finalInscription
     * @return True if the inscription was successful, false otherwise
     */
    boolean addStudentFinalInscription(Student student, FinalInscription finalInscription);

    /**
     * Get the final exams inscriptions that a student is taking
     *
     * @param student The student
     * @return The final inscriptions
     */
    List<FinalInscription> getFinalInscriptionsTaken(Student student);

    /**
     * Remove the given student from the Final Inscription instance
     *
     * @param student The student
     * @param finalInscription The final inscription
     */
    void deleteStudentFinalInscription(Student student, FinalInscription finalInscription);

    /**
     * Add a final grade
     *
     * @param id     The FinalInscriptionId
     * @param docket The student's docket
     * @param grade  The grade
     * @return
     */
    boolean addFinalGrade(Integer id, Integer docket, BigDecimal grade);


    boolean existsEmail(String email);


    /**
     * @param procedure
     * @return
     */
    boolean createProcedure(Procedure procedure);

    /**
     * @param docket
     * @return
     */
    List<Procedure> getProcedures(int docket);

}

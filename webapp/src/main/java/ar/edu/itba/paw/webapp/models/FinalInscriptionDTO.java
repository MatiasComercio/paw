package ar.edu.itba.paw.webapp.models;

import ar.edu.itba.paw.models.FinalInscription;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public class FinalInscriptionDTO {
  private CourseDTO course;
  private List<StudentIndexDTO> students;

  private int id;
  private LocalDateTime finalExamDate;
  private int vacancy;
  private int maxVacancy;
  private FinalInscription.FinalInscriptionState state;

  public FinalInscriptionDTO() {

  }

  public CourseDTO getCourse() {
    return course;
  }

  public void setCourse(CourseDTO course) {
    this.course = course;
  }

  public Collection<StudentIndexDTO> getStudents() {
    return students;
  }

  public void setStudents(List<StudentIndexDTO> students) {
    this.students = students;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public LocalDateTime getFinalExamDate() {
    return finalExamDate;
  }

  public void setFinalExamDate(LocalDateTime finalExamDate) {
    this.finalExamDate = finalExamDate;
  }

  public int getVacancy() {
    return vacancy;
  }

  public void setVacancy(int vacancy) {
    this.vacancy = vacancy;
  }

  public int getMaxVacancy() {
    return maxVacancy;
  }

  public void setMaxVacancy(int maxVacancy) {
    this.maxVacancy = maxVacancy;
  }

  public FinalInscription.FinalInscriptionState getState() {
    return state;
  }

  public void setState(FinalInscription.FinalInscriptionState state) {
    this.state = state;
  }
}

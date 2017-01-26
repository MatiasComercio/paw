package ar.edu.itba.paw.webapp.models;

import ar.edu.itba.paw.models.FinalInscription;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FinalInscriptionIndexDTO {

  //private CourseDTO course;
  private String courseId;

  private int id;
  private LocalDateTime finalExamDate;
  private int vacancy;
  private int maxVacancy;
  private FinalInscription.FinalInscriptionState state;

  public FinalInscriptionIndexDTO() {
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
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

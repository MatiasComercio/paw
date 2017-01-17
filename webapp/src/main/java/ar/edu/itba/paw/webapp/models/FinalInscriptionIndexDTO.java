package ar.edu.itba.paw.webapp.models;

import ar.edu.itba.paw.models.FinalInscription;

import java.time.LocalDate;

public class FinalInscriptionIndexDTO {

  private CourseDTO course;

  private int id;
  private LocalDate finalExamDate;
  private int vacancy;
  private int maxVacancy;
  private FinalInscription.FinalInscriptionState state;

  public FinalInscriptionIndexDTO() {
  }

  public CourseDTO getCourse() {
    return course;
  }

  public void setCourse(CourseDTO course) {
    this.course = course;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public LocalDate getFinalExamDate() {
    return finalExamDate;
  }

  public void setFinalExamDate(LocalDate finalExamDate) {
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

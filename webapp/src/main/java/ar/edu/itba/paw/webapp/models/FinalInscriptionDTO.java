package ar.edu.itba.paw.webapp.models;

import ar.edu.itba.paw.models.FinalInscription;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;
import java.util.List;

public class FinalInscriptionDTO {

  private Integer id;
  private CourseDTO course;
  private List<StudentIndexDTO> history;
  private int vacancy;
  private FinalInscription.FinalInscriptionState state;

  @NotNull
  @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
  private LocalDateTime finalExamDate;

  @NotNull
  @Min(0)
  private int maxVacancy;

  public FinalInscriptionDTO() {

  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public CourseDTO getCourse() {
    return course;
  }

  public void setCourse(CourseDTO course) {
    this.course = course;
  }

  public List<StudentIndexDTO> getHistory() {
    return history;
  }

  public void setHistory(List<StudentIndexDTO> history) {
    this.history = history;
  }

  public int getVacancy() {
    return vacancy;
  }

  public void setVacancy(int vacancy) {
    this.vacancy = vacancy;
  }

  public FinalInscription.FinalInscriptionState getState() {
    return state;
  }

  public void setState(FinalInscription.FinalInscriptionState state) {
    this.state = state;
  }

  public LocalDateTime getFinalExamDate() {
    return finalExamDate;
  }

  public void setFinalExamDate(LocalDateTime finalExamDate) {
    this.finalExamDate = finalExamDate;
  }

  public int getMaxVacancy() {
    return maxVacancy;
  }

  public void setMaxVacancy(int maxVacancy) {
    this.maxVacancy = maxVacancy;
  }

}

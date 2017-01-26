package ar.edu.itba.paw.webapp.models;

import ar.edu.itba.paw.models.FinalGrade;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TranscriptGradeDTO {

  private Integer id;
  private Integer docket;
  private String courseName;
  private BigDecimal grade;
  private LocalDateTime modified;
  private boolean taking;
  private List<FinalGrade> finalGrades;
  private String courseCodId;

  public TranscriptGradeDTO() {
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getDocket() {
    return docket;
  }

  public void setDocket(Integer docket) {
    this.docket = docket;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public BigDecimal getGrade() {
    return grade;
  }

  public void setGrade(BigDecimal grade) {
    this.grade = grade;
  }

  public LocalDateTime getModified() {
    return modified;
  }

  public void setModified(LocalDateTime modified) {
    this.modified = modified;
  }

  public boolean isTaking() {
    return taking;
  }

  public void setTaking(boolean taking) {
    this.taking = taking;
  }

  public List<FinalGrade> getFinalGrades() {
    return finalGrades;
  }

  public void setFinalGrades(List<FinalGrade> finalGrades) {
    this.finalGrades = finalGrades;
  }

  public String getCourseCodId() {
    return courseCodId;
  }

  public void setCourseCodId(String courseCodId) {
    this.courseCodId = courseCodId;
  }
}

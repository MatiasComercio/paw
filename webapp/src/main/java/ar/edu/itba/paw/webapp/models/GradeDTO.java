package ar.edu.itba.paw.webapp.models;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GradeDTO {


  private Integer id;

  //private Boolean taking;
  //private List<FinalGrade> finalGrades;

  @NotNull
  @Pattern(regexp="\\d{2}\\.\\d{2}")
  private String courseId;

  @NotNull
  @Min(0)
  @Max(10)
  private BigDecimal grade;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime modified;

  public GradeDTO() {
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
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

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}

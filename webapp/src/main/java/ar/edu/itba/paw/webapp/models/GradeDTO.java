package ar.edu.itba.paw.webapp.models;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.FinalGrade;
import ar.edu.itba.paw.models.users.Student;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class GradeDTO {

  private Course course;
  private BigDecimal grade;
  private LocalDateTime modified;
  private Boolean taking;
  private List<FinalGrade> finalGrades;
  private Student student;


  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
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

  public Boolean getTaking() {
    return taking;
  }

  public void setTaking(Boolean taking) {
    this.taking = taking;
  }

  public List<FinalGrade> getFinalGrades() {
    return finalGrades;
  }

  public void setFinalGrades(List<FinalGrade> finalGrades) {
    this.finalGrades = finalGrades;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }
}

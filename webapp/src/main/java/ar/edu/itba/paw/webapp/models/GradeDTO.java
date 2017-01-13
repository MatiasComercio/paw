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



}

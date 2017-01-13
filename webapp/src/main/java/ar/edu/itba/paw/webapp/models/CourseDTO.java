package ar.edu.itba.paw.webapp.models;

import javax.validation.constraints.*;

public class CourseDTO {

  @NotNull
  @Pattern(regexp="\\d{2}\\.\\d{2}")
  private String courseId;

  @NotNull
  @Size(min=2, max=50)
  private String name;

  @NotNull
  @Min(1)
  @Max(20)
  private int credits;

  @NotNull
  @Min(1)
  private int semester;

//  private Set<Course> correlatives;
//  private Set<Course> upperCorrelatives;
//  private List<Student> students;
//  private List<Student> approvedStudents;


  public CourseDTO() {
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setCredits(int credits) {
    this.credits = credits;
  }

  public void setSemester(int semester) {
    this.semester = semester;
  }

  public String getCourseId() {
    return courseId;
  }

  public String getName() {
    return name;
  }

  public int getCredits() {
    return credits;
  }

  public int getSemester() {
    return semester;
  }
}

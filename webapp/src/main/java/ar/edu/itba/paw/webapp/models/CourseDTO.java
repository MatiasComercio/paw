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


  public CourseDTO() {

  }


  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getCredits() {
    return credits;
  }

  public void setCredits(int credits) {
    this.credits = credits;
  }

  public int getSemester() {
    return semester;
  }

  public void setSemester(int semester) {
    this.semester = semester;
  }

  @Override
  public String toString() {
    return "CourseDTO{" +
            "courseId='" + courseId + '\'' +
            ", name='" + name + '\'' +
            ", credits=" + credits +
            ", semester=" + semester +
            '}';
  }
}

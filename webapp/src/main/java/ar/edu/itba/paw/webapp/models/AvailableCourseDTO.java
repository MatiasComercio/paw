package ar.edu.itba.paw.webapp.models;


public class AvailableCourseDTO {

  private CourseDTO course;
  private Boolean available;

  public AvailableCourseDTO() {
  }

  public AvailableCourseDTO(CourseDTO course, Boolean available) {
    this.course = course;
    this.available = available;
  }

  public CourseDTO getCourse() {
    return course;
  }

  public void setCourse(CourseDTO course) {
    this.course = course;
  }

  public Boolean getAvailable() {
    return available;
  }

  public void setAvailable(Boolean available) {
    this.available = available;
  }
}

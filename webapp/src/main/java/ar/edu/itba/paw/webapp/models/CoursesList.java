package ar.edu.itba.paw.webapp.models;

import java.util.List;

public class CoursesList {

  private List<CourseDTO> courses;

  public CoursesList() {}

  public List<CourseDTO> getCourses() {
    return courses;
  }

  public void setCourses(List<CourseDTO> courses) {
    this.courses = courses;
  }
}

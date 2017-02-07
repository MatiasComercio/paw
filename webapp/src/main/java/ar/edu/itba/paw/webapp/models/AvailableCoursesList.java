package ar.edu.itba.paw.webapp.models;


import java.util.List;

public class AvailableCoursesList {
  private List<AvailableCourseDTO> courses;

  public AvailableCoursesList() {
  }

  public AvailableCoursesList(List<AvailableCourseDTO> courses) {
    this.courses = courses;
  }

  public List<AvailableCourseDTO> getCourses() {
    return courses;
  }

  public void setCourses(List<AvailableCourseDTO> courses) {
    this.courses = courses;
  }
}

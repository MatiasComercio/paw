package ar.edu.itba.paw.webapp.models;

import java.util.List;

public class StudentsList {
  private List<StudentIndexDTO> students;

  public StudentsList(List<StudentIndexDTO> students) {
    this.students = students;
  }

  public StudentsList() {
    // Just for Jersey =)
  }

  public List<StudentIndexDTO> getStudents() {
    return students;
  }

  public void setStudents(List<StudentIndexDTO> students) {
    this.students = students;
  }
}

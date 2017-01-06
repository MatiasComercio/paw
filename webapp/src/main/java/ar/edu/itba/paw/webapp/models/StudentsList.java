package ar.edu.itba.paw.webapp.models;

import java.util.List;

public class StudentsList {
  private List<StudentDTO> students;

  public StudentsList(List<StudentDTO> students) {
    this.students = students;
  }

  public StudentsList() {
    // Just for Jersey =)
  }

  public List<StudentDTO> getStudents() {
    return students;
  }

  public void setStudents(List<StudentDTO> students) {
    this.students = students;
  }
}

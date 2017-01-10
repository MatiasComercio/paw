package ar.edu.itba.paw.webapp.models;

import ar.edu.itba.paw.models.Course;
import ar.edu.itba.paw.models.users.Student;
import java.util.List;
import java.util.Set;

public class CourseDTO {

  private String courseId;
  private String name;
  private int credits;
  private int semester;

  private Set<Course> correlatives;
  private Set<Course> upperCorrelatives;
  private List<Student> students;
  private List<Student> approvedStudents;

}

package ar.edu.itba.paw.webapp.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class InscriptionDTO {

  @NotNull
  @Pattern(regexp="\\d{2}\\.\\d{2}")
  private String courseId;

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

}

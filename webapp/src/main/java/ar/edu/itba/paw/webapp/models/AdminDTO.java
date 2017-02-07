package ar.edu.itba.paw.webapp.models;

import javax.validation.constraints.Size;

public class AdminDTO {

  private Integer dni;

  @Size(max=50)
  private String firstName;

  @Size(max=50)
  private String lastName;

  public AdminDTO() {
  }

  public Integer getDni() {
    return dni;
  }

  public void setDni(Integer dni) {
    this.dni = dni;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}

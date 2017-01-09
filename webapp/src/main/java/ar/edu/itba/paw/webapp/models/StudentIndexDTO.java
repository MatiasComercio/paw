package ar.edu.itba.paw.webapp.models;

public class StudentIndexDTO {

  private int docket;
  private String firstName;
  private String lastName;

  public StudentIndexDTO(int docket, String firstName, String lastName) {
    this.docket = docket;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public StudentIndexDTO() {
    // Just for Jersey
  }

  public int getDocket() {
    return docket;
  }

  public void setDocket(int docket) {
    this.docket = docket;
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

package ar.edu.itba.paw.webapp.models;

import ar.edu.itba.paw.models.Address;

public class StudentDTO {

  private int docket;
  private String firstName;
  private String lastName;
  private Address address;

  public StudentDTO(int docket, String firstName, String lastName, Address address) {
    this.docket = docket;
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
  }

  public StudentDTO() {
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

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }
}

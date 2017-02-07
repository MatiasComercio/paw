package ar.edu.itba.paw.webapp.models;

import ar.edu.itba.paw.models.users.User;

import java.time.LocalDate;

public class StudentShowDTO {

  private Integer dni;
  private String firstName;
  private String lastName;
  private User.Genre genre;
  private LocalDate birthday;
  private int docket;
  private String email;
  private AddressDTO address;

  public StudentShowDTO() {

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

  public User.Genre getGenre() {
    return genre;
  }

  public void setGenre(User.Genre genre) {
    this.genre = genre;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
  }

  public int getDocket() {
    return docket;
  }

  public void setDocket(int docket) {
    this.docket = docket;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public AddressDTO getAddress() {
    return address;
  }

  public void setAddress(AddressDTO address) {
    this.address = address;
  }
}

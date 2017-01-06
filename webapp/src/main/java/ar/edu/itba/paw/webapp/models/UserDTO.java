package ar.edu.itba.paw.webapp.models;

import ar.edu.itba.paw.models.RoleClass;
import ar.edu.itba.paw.models.users.User;

import java.time.LocalDate;

public class UserDTO {

  private int dni;
  private String firstName;
  private String lastName;
  private User.Genre genre;
  private LocalDate birthday;
  private String email;
  private AddressDTO address;
  private String password;
  private RoleClass role;

  public UserDTO() {
    // Just for Jersey =)
  }

  public int getDni() {
    return dni;
  }

  public void setDni(int dni) {
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public RoleClass getRole() {
    return role;
  }

  public void setRole(RoleClass role) {
    this.role = role;
  }
}

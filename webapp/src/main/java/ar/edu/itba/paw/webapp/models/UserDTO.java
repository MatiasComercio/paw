package ar.edu.itba.paw.webapp.models;

import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.Procedure;
import ar.edu.itba.paw.models.RoleClass;
import ar.edu.itba.paw.models.users.User;

import java.time.LocalDate;
import java.util.List;

public class UserDTO {

  private Integer id_seq;
  private int dni;
  private String firstName;
  private String lastName;
  private User.Genre genre;
  private LocalDate birthday;
  private String email;
  private Address address;
  private String password;
  private RoleClass role;
  private List<Procedure> procedures;

  public UserDTO() {
    // Just for Jersey =)
  }

  public UserDTO(Integer id_seq, int dni, String firstName, String lastName, User.Genre genre, LocalDate birthday, String email, Address address, String password, RoleClass role, List<Procedure> procedures) {
    this.id_seq = id_seq;
    this.dni = dni;
    this.firstName = firstName;
    this.lastName = lastName;
    this.genre = genre;
    this.birthday = birthday;
    this.email = email;
    this.address = address;
    this.password = password;
    this.role = role;
    this.procedures = procedures;
  }

  public void setId_seq(Integer id_seq) {
    this.id_seq = id_seq;
  }

  public void setDni(int dni) {
    this.dni = dni;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setGenre(User.Genre genre) {
    this.genre = genre;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRole(RoleClass role) {
    this.role = role;
  }

  public void setProcedures(List<Procedure> procedures) {
    this.procedures = procedures;
  }
}

package ar.edu.itba.paw.webapp.models;

import ar.edu.itba.paw.models.RoleClass;
import ar.edu.itba.paw.models.users.User;
import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class UserDTO {

  @Digits(integer=8, fraction=0)
  @Min(1)
  @NotNull
  private int dni;

  @NotNull
  @Size(min=2, max=50)
  private String firstName;

  @NotNull
  @Size(min=2, max=50)
  private String lastName;

  private User.Genre genre;

  // @DateTimeFormat(pattern="yyyy-MM-dd")
  private LocalDate birthday;

  @NotBlank
  //@Size(min=8, max=32)
  private String password;

  private AddressDTO address;

  // Just for GET
  private RoleClass role;
  private String email;

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

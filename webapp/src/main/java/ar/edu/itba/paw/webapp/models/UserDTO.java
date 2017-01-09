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

  @Digits(integer=8, fraction=0, message = "{userDTO.dni.digits}")
  @Min(value = 1, message = "{userDTO.dni.min}")
  @NotNull(message = "{userDTO.dni.notNull}")
  private Integer dni;

  @NotNull(message = "{userDTO.firstName.notNull}")
  @Size(min=2, max=50, message = "{userDTO.firstName.size}")
  private String firstName;

  @NotNull(message = "{userDTO.lastName.notNull}")
  @Size(min=2, max=50, message = "{userDTO.lastName.size}")
  private String lastName;

  private User.Genre genre;

  // @DateTimeFormat(pattern="yyyy-MM-dd")
  private LocalDate birthday;

  @NotBlank(message = "{userDTO.password.notBlank}")
  @Size(min=8, max=32, message = "{userDTO.password.size}")
  private String password;

  private AddressDTO address;

  // Just for GET
  private RoleClass role;
  private String email;

  public UserDTO() {
    // Just for Jersey =)
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

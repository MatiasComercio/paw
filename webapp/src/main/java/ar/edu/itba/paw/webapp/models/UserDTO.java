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

  @Digits(integer=8, fraction=0, message = "DNI must have 8 digits\n")
  @Min(value = 1, message = "DNI must be greater than or equal to 1\n")
  @NotNull(message = "DNI must not be null\n")
  private int dni;

  @NotNull(message = "firstName must not be null\n")
  @Size(min=2, max=50, message = "firstName must have between 2 and 50 characters\n")
  private String firstName;

  @NotNull(message = "lastName must not be null\n")
  @Size(min=2, max=50, message = "lastName must have between 2 and 50 characters\n")
  private String lastName;

  private User.Genre genre;

  // @DateTimeFormat(pattern="yyyy-MM-dd")
  private LocalDate birthday;

  @NotBlank(message = "password must not be null\n")
  @Size(min=8, max=32, message = "password must have between 8 and 32 characters\n")
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

package ar.edu.itba.paw.webapp.models;

import ar.edu.itba.paw.models.users.User;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class UserNewDTO {

  @Digits(integer=8, fraction=0)
  @Min(value = 1)
  @NotNull()
  private Integer dni;

  @NotNull()
  @Size(min=2, max=50)
  private String firstName;

  @NotNull()
  @Size(min=2, max=50)
  private String lastName;

  private User.Genre genre;

  @DateTimeFormat(pattern="yyyy-MM-dd")
  private LocalDate birthday;

  @NotBlank()
  @Size(min=8, max=32)
  private String password;

  @Valid
  @NotNull
  private AddressDTO address;

  public UserNewDTO() {
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

}

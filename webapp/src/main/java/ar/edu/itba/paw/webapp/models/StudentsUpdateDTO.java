package ar.edu.itba.paw.webapp.models;

import ar.edu.itba.paw.models.users.User;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

public class StudentsUpdateDTO {

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

  //TODO: Validate genre
  private User.Genre genre;

  @XmlJavaTypeAdapter(LocalDateAdapter.class) //TODO: Improve, see if we can validate if the date is incorrect, so an error is returned, instead of mapping it to null
  private LocalDate birthday;

  public StudentsUpdateDTO() {

  }

  public StudentsUpdateDTO(Integer dni, String firstName, String lastName, User.Genre genre, LocalDate birthday) {
    this.dni = dni;
    this.firstName = firstName;
    this.lastName = lastName;
    this.genre = genre;
    this.birthday = birthday;
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
}

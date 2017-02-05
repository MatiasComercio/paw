package ar.edu.itba.paw.webapp.models;

import ar.edu.itba.paw.models.users.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

public class AdminNewDTO {
  @NotNull
  @Max(99999999)
  @Min(10000000)
  private Integer dni;

  @NotNull
  @Size(min=2, max=50)
  private String firstName;

  @NotNull
  @Size(min=2, max=50)
  private String lastName;

  private User.Genre genre;

  @DateTimeFormat(pattern="yyyy-MM-dd")
  @XmlJavaTypeAdapter(LocalDateAdapter.class)
  private LocalDate birthday;

	@Valid
	@NotNull
	private AddressDTO address;

  private String email;

  public AdminNewDTO() {
    // Required by Jersey
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}

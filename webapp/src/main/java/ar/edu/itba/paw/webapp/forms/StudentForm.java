package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.models.users.User;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class StudentForm {

    /* When the student is created the docket is asigned by the database, not the user */
    private final static Integer EMPTY_DOCKET = 0;

    @NotNull
    @Min(1)
    private Integer dni;
    //@NumberFormat(style = Style.NUMBER)

    @NotNull
    @Size(min=2, max=50)
    private String firstName;

    @NotNull
    @Size(min=2, max=50)
    private String lastName;

    private User.Genre genre;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birthday;

    private Address address;
    @NotNull
    @Size(min=2, max=50)
    private String country;
    @NotNull
    @Size(min=2, max=50)
    private String city;
    @NotNull
    @Size(min=2, max=50)
    private String neighborhood;
    @NotNull
    @Size(min=2, max=100)
    private String street;

    @NotNull
    @Min(0)
    private Integer number;

    @Min(0)
    private Integer floor;
    @Size(max=10)
    private String door;
    @Min(0)
    private Long telephone;
    @Min(0)
    private Integer zipCode;


    public StudentForm(){}

    public Student build(){

        this.address = new Address.Builder(country, city, neighborhood, street, number).floor(floor).
                door(door).telephone(telephone).zipCode(zipCode).build();
        return new Student.Builder(EMPTY_DOCKET, dni).firstName(firstName).lastName(lastName).genre(genre).
                birthday(birthday).address(address).build();
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getDoor() {
        return door;
    }

    public void setDoor(String door) {
        this.door = door;
    }

    public Long getTelephone() {
        return telephone;
    }

    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }
}

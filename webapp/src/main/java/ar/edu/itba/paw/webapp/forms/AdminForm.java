package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.users.Admin;
import ar.edu.itba.paw.models.users.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class AdminForm {
    @NotNull
    @Min(1)
    private Integer dni;

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

    public AdminForm() {}

    public Admin build() {
        this.address = new Address.Builder(country, city, neighborhood, street, number).floor(floor).
                door(door).telephone(telephone).zipCode(zipCode).build();
        return new Admin.Builder(dni).firstName(firstName).lastName(lastName).genre(genre).
                birthday(birthday).address(address).build();
    }

    public void loadFromAdmin(Admin admin){
        this.dni = admin.getDni();
        this.firstName = admin.getFirstName();
        this.lastName = admin.getLastName();
//        String genre = User.Genre.getGenre(admin.getGenre()); /* +++xdoing */
//        this.genre = genre == null ? null : genre.equals("M") ? User.Genre.M : genre.equals("F") ? User.Genre.F : null;
        this.genre = admin.getGenre();
        this.birthday = admin.getBirthday();
        this.address = admin.getAddress();
        this.country = admin.getAddress().getCountry();
        this.city = admin.getAddress().getCity();
        this.neighborhood = admin.getAddress().getNeighborhood();
        this.street = admin.getAddress().getStreet();
        this.number = admin.getAddress().getNumber();
        this.floor = admin.getAddress().getFloor();
        this.door = admin.getAddress().getDoor();
        this.telephone = admin.getAddress().getTelephone();
        this.zipCode = admin.getAddress().getZipCode();

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

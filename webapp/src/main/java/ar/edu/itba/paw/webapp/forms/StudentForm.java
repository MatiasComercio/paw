package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.models.Address;
import ar.edu.itba.paw.models.users.Student;
import ar.edu.itba.paw.models.users.User;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class StudentForm {

    /* When the student is created the docket is asigned by the database, not the user */
    private final static Integer EMPTY_DOCKET = 0;
    @Digits(integer=8, fraction=0)
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

    private Integer id_seq;
    private int docket;
    private String email;
    private String password;
    private Integer address_id_seq;


    public StudentForm(){}

    public Student build(){

        this.address = new Address.Builder(address_id_seq, dni, country, city, neighborhood, street, number).floor(floor).
                door(door).telephone(telephone).zipCode(zipCode).build();
        return new Student.Builder(EMPTY_DOCKET, dni).id_seq(id_seq).firstName(firstName).lastName(lastName).genre(genre). // +++xcheck: EMPTY_DOCKET
                birthday(birthday).address(address).email(email).password(password).build();
    }

    public void loadFromStudent(Student student){
        this.id_seq = student.getId_seq();
        this.dni = student.getDni();
        this.docket = student.getDocket();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
//        String genre = User.Genre.getGenre(student.getGenre()); /* +++xdoing */
//        this.genre = genre == null ? null : genre.equals("M") ? User.Genre.M : genre.equals("F") ? User.Genre.F : null;
        this.genre = student.getGenre();
        this.birthday = student.getBirthday();
        this.email = student.getEmail();
        this.password = student.getPassword();
        this.address = student.getAddress();
        this.address_id_seq = student.getAddress().getId_seq();
        this.country = student.getAddress().getCountry();
        this.city = student.getAddress().getCity();
        this.neighborhood = student.getAddress().getNeighborhood();
        this.street = student.getAddress().getStreet();
        this.number = student.getAddress().getNumber();
        this.floor = student.getAddress().getFloor();
        this.door = student.getAddress().getDoor();
        this.telephone = student.getAddress().getTelephone();
        this.zipCode = student.getAddress().getZipCode();

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
        this.firstName = firstName.trim();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName.trim();
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
        this.country = country.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city.trim();
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood.trim();
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street.trim();
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
        this.door = door.trim();
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

    public Integer getId_seq() {
        return id_seq;
    }

    public void setId_seq(final Integer id_seq) {
        this.id_seq = id_seq;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Integer getAddress_id_seq() {
        return address_id_seq;
    }

    public void setAddress_id_seq(final Integer address_id_seq) {
        this.address_id_seq = address_id_seq;
    }
}

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

    @NotNull
    @Min(1)
    private int dni;

    @NotNull
    @Size(min=2, max=50)
    private String firstName;

    @NotNull
    @Size(min=2, max=50)
    private String lastName;

    private User.Genre genre;

    @DateTimeFormat(pattern="yyyy/MM/dd/")
    private LocalDate birthday;

    @Size(min=0, max=50)
    private String email;

    @NotNull
    @Min(1)
    private int docket;

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
    private String number;

    @Min(0)
    private String floor;
    private String door;
    private String telephone;
    private String zipCode;


    public StudentForm(){}

    public Student build(){
        this.number = number.equals("") ? String.valueOf(Integer.MIN_VALUE) : number;
        this.floor = floor.equals("") ? String.valueOf(Integer.MIN_VALUE) : floor;

        System.out.println("telephone: " + telephone);
        if(telephone.equals("")){
            System.out.println("telephone es vacio");
        }
        if(telephone == null){
            System.out.println("telephone es null");
        }
        this.telephone = telephone.equals("") ? String.valueOf(Long.MIN_VALUE) : telephone;

        System.out.println("Cambio: telephone es " + telephone);
        this.zipCode = zipCode.equals("") ? String.valueOf(Integer.MIN_VALUE) : zipCode;

        this.address = new Address.Builder(country, city, neighborhood, street, Integer.valueOf(number)).floor(Integer.valueOf(floor)).
                door(door).telephone(Long.valueOf(telephone)).zipCode(Integer.valueOf(zipCode)).build();
        return new Student.Builder(docket, dni).firstName(firstName).lastName(lastName).genre(genre).birthday(birthday)
                .email(email).address(address).build();
        //return new Student.Builder(docket, dni).firstName(firstName).lastName(lastName).build();

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

    public int getDocket() {
        return docket;
    }

    public void setDocket(int docket) {
        this.docket = docket;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getDoor() {
        return door;
    }

    public void setDoor(String door) {
        this.door = door;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}

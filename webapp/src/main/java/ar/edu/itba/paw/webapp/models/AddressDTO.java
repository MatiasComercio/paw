package ar.edu.itba.paw.webapp.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddressDTO {

  private Integer dni;

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

  public AddressDTO() {
    // Just for Jersey =)
  }

  public Integer getDni() {
    return dni;
  }

  public void setDni(Integer dni) {
    this.dni = dni;
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

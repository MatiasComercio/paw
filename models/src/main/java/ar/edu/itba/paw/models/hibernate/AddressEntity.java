package ar.edu.itba.paw.models.hibernate;

import javax.persistence.*;

//@Entity
//@Table(name = "address")
public class AddressEntity {
	private int dni;
	private String country;
	private String city;
	private String neighborhood;
	private String street;
	private int number;
	private int floor;
	private String door;
	private long telephone;
	private int zipCode;
	private UsersEntity usersByDni;

	@Id
	@Column(name = "dni", nullable = false)
	public int getDni() {
		return dni;
	}

	public void setDni(final int dni) {
		this.dni = dni;
	}

	@Basic
	@Column(name = "country", nullable = false, length = 50)
	public String getCountry() {
		return country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	@Basic
	@Column(name = "city", nullable = false, length = 50)
	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	@Basic
	@Column(name = "neighborhood", nullable = false, length = 50)
	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(final String neighborhood) {
		this.neighborhood = neighborhood;
	}

	@Basic
	@Column(name = "street", nullable = false, length = 100)
	public String getStreet() {
		return street;
	}

	public void setStreet(final String street) {
		this.street = street;
	}

	@Basic
	@Column(name = "number", nullable = false)
	public int getNumber() {
		return number;
	}

	public void setNumber(final int number) {
		this.number = number;
	}

	@Basic
	@Column(name = "floor", nullable = true)
	public int getFloor() {
		return floor;
	}

	public void setFloor(final int floor) {
		this.floor = floor;
	}

	@Basic
	@Column(name = "door", nullable = true, length = 10)
	public String getDoor() {
		return door;
	}

	public void setDoor(final String door) {
		this.door = door;
	}

	@Basic
	@Column(name = "telephone", nullable = true)
	public long getTelephone() {
		return telephone;
	}

	public void setTelephone(final long telephone) {
		this.telephone = telephone;
	}

	@Basic
	@Column(name = "zip_code", nullable = true)
	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(final int zipCode) {
		this.zipCode = zipCode;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final AddressEntity that = (AddressEntity) o;

		if (dni != that.dni) return false;
		if (number != that.number) return false;
		if (floor != that.floor) return false;
		if (telephone != that.telephone) return false;
		if (zipCode != that.zipCode) return false;
		if (country != null ? !country.equals(that.country) : that.country != null) return false;
		if (city != null ? !city.equals(that.city) : that.city != null) return false;
		if (neighborhood != null ? !neighborhood.equals(that.neighborhood) : that.neighborhood != null) return false;
		if (street != null ? !street.equals(that.street) : that.street != null) return false;
		if (door != null ? !door.equals(that.door) : that.door != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = dni;
		result = 31 * result + (country != null ? country.hashCode() : 0);
		result = 31 * result + (city != null ? city.hashCode() : 0);
		result = 31 * result + (neighborhood != null ? neighborhood.hashCode() : 0);
		result = 31 * result + (street != null ? street.hashCode() : 0);
		result = 31 * result + number;
		result = 31 * result + floor;
		result = 31 * result + (door != null ? door.hashCode() : 0);
		result = 31 * result + (int) (telephone ^ (telephone >>> 32));
		result = 31 * result + zipCode;
		return result;
	}

	@OneToOne
	@JoinColumn(name = "dni", referencedColumnName = "dni", nullable = false)
	public UsersEntity getUsersByDni() {
		return usersByDni;
	}

	public void setUsersByDni(final UsersEntity usersByDni) {
		this.usersByDni = usersByDni;
	}
}

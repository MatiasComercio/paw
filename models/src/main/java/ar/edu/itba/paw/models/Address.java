package ar.edu.itba.paw.models;

import ar.edu.itba.paw.models.users.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "address")
public class Address implements Serializable {

	@Id
	@OneToOne
	@JoinColumn(name = "dni", referencedColumnName = "dni", nullable = false)
	private User userByDni;

	@Basic
	@Column(name = "country", nullable = false, length = 50)
	private String country;

	@Basic
	@Column(name = "city", nullable = false, length = 50)
	private String city;

	@Basic
	@Column(name = "neighborhood", nullable = false, length = 50)
	private String neighborhood;

	@Basic
	@Column(name = "street", nullable = false, length = 100)
	private String street;

	@Basic
	@Column(name = "number", nullable = false)
	private Integer number;

	@Basic
	@Column(name = "floor")
	private Integer floor;

	@Basic
	@Column(name = "door", length = 10)
	private String door;

	@Basic
	@Column(name = "telephone")
	private Long telephone;

	@Basic
	@Column(name = "zip_code")
	private Integer zipCode;

	private Address(final Builder builder) {
		this.country = builder.country;
		this.city = builder.city;
		this.neighborhood = builder.neighborhood;
		this.street = builder.street;
		this.number = builder.number;
		this.floor = builder.floor;
		this.door = builder.door;
		this.telephone = builder.telephone;
		this.zipCode = builder.zipCode;
	}

	protected Address() {
		// Just for Hibernate
	}

	public String getCountry() {
		return country;
	}

	public String getCity() {
		return city;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public String getStreet() {
		return street;
	}

	public Integer getNumber() {
		return number;
	}

	public Integer getFloor() {
		return floor;
	}

	public String getDoor() {
		return door;
	}

	public Long getTelephone() {
		return telephone;
	}

	public Integer getZipCode() {
		return zipCode;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final Address that = (Address) o;

		if (!Objects.equals(number, that.number)) return false;
		if (!Objects.equals(floor, that.floor)) return false;
		if (!Objects.equals(telephone, that.telephone)) return false;
		if (!Objects.equals(zipCode, that.zipCode)) return false;
		if (country != null ? !country.equals(that.country) : that.country != null) return false;
		if (city != null ? !city.equals(that.city) : that.city != null) return false;
		if (neighborhood != null ? !neighborhood.equals(that.neighborhood) : that.neighborhood != null) return false;
		if (street != null ? !street.equals(that.street) : that.street != null) return false;
		if (door != null ? !door.equals(that.door) : that.door != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = 0;
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

	public static class Builder {
		private String country;
		private String city;
		private String neighborhood;
		private String street;
		private Integer number;

		private Integer floor;
		private String door = "";
		private Long telephone;
		private Integer zipCode;

		public Builder(final String country, final String city,
		               final String neighborhood, final String street, final Integer number) {
			this.country = country == null ? "" : country;
			this.city = city == null ? "" : city;
			this.neighborhood = neighborhood == null ? "" : neighborhood;
			this.street = street == null ? "" : street;
			this.number = number;
		}

		public Builder floor(final Integer floor) {
			this.floor = floor;
			return this;
		}

		public Builder door(final String door) {
			if (door != null) {
				this.door = door;
			}
			return this;
		}

		public Builder telephone(final Long telephone) {
			this.telephone = telephone;
			return this;
		}

		public Builder zipCode(final Integer zipCode) {
			this.zipCode = zipCode;
			return this;
		}

		public Address build() {
			return new Address(this);
		}
	}
}

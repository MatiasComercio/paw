package ar.edu.itba.paw.models;

public class Address {
	private final String country;
	private final String city;
	private final String neighborhood;
	private final String street;
	private final Integer number;
	private final Integer floor;
	private final String door;
	private final Long telephone;
	private final Integer zipCode;

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

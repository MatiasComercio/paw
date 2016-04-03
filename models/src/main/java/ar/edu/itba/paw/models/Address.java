package ar.edu.itba.paw.models;

public class Address {
	private final String country;
	private final String city;
	private final String neighborhood;
	private final String street;
	private final String number;
	private final int floor;
	private final String door;
	private final long telephone;
	private final int zipCode;

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

	public String getNumber() {
		return number;
	}

	public int getFloor() {
		return floor;
	}

	public String getDoor() {
		return door;
	}

	public long getTelephone() {
		return telephone;
	}

	public int getZipCode() {
		return zipCode;
	}

	public static class Builder {
		private final String country;
		private final String city;
		private final String neighborhood;
		private final String street;
		private final String number;

		private int floor;
		private String door;
		private long telephone;
		private int zipCode;

		public Builder(final String country, final String city,
		               final String neighborhood, final String street, final String number) {
			this.country = country;
			this.city = city;
			this.neighborhood = neighborhood;
			this.street = street;
			this.number = number;
		}

		public Builder floor(final int floor) {
			this.floor = floor;
			return this;
		}

		public Builder door(final String door) {
			this.door = door;
			return this;
		}

		public Builder telephone(final long telephone) {
			this.telephone = telephone;
			return this;
		}

		public Builder zipCode(final int zipCode) {
			this.zipCode = zipCode;
			return this;
		}

		public Address build() {
			return new Address(this);
		}
	}
}

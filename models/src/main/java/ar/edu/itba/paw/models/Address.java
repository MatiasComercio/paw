package ar.edu.itba.paw.models;

public class Address {
	private final String country;
	private final String city;
	private final String neighborhood;
	private final String street;
	private final String number;
	private final String floor;
	private final String door;
	private final String telephone;
	private final String zipCode;

	private Address(final Builder builder) {
		this.country = builder.country;
		this.city = builder.city;
		this.neighborhood = builder.neighborhood;
		this.street = builder.street;
		this.number = builder.number == Integer.MIN_VALUE ? "" : String.valueOf(builder.number);
		this.floor = builder.floor == Integer.MIN_VALUE ? "" : String.valueOf(builder.floor);
		this.door = builder.door;
		this.telephone = builder.telephone == Long.MIN_VALUE ? "" : String.valueOf(builder.telephone);
		this.zipCode = builder.zipCode == Integer.MIN_VALUE ? "" : String.valueOf(builder.zipCode);
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

	public String getFloor() {
		return floor;
	}

	public String getDoor() {
		return door;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getZipCode() {
		return zipCode;
	}

	public static class Builder {
		private String country;
		private String city;
		private String neighborhood;
		private String street;
		private int number;

		private int floor;
		private String door = "";
		private long telephone;
		private int zipCode;

		public Builder(final String country, final String city,
		               final String neighborhood, final String street, final int number) {
			this.country = country == null ? "" : country;
			this.city = city == null ? "" : city;
			this.neighborhood = neighborhood == null ? "" : neighborhood;
			this.street = street == null ? "" : street;
			this.number = number;
		}

		public Builder floor(final int floor) {
			this.floor = floor;
			return this;
		}

		public Builder door(final String door) {
			if (door != null) {
				this.door = door;
			}
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

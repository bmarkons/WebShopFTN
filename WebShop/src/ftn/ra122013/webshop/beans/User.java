package ftn.ra122013.webshop.beans;

import java.io.Serializable;

public abstract class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7335028490752549032L;
	private String username;
	private String password;
	private String address;
	private String country;
	private String email;
	private String name;
	private String surname;
	private String telephone;
	private String type;

	public User(String username, String password, String address, String country, String email, String name,
			String surname, String telephone) {
		super();
		this.username = username;
		this.password = password;
		this.address = address;
		this.country = country;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.telephone = telephone;
	}

	/* GETTERS */
	public String getAddress() {
		return this.address;
	}

	public String getCountry() {
		return this.country;
	}

	public String getEmail() {
		return this.email;
	}

	public String getName() {
		return this.name;
	}

	public String getPassword() {
		return this.password;
	}

	public String getSurname() {
		return this.surname;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public String getUsername() {
		return this.username;
	}

	/* SETTERS */
	public void setAddress(String value) {
		this.address = value;
	}

	public void setCountry(String value) {
		this.country = value;
	}

	public void setEmail(String value) {
		this.email = value;
	}

	public void setName(String value) {
		this.name = value;
	}

	public void setPassword(String value) {
		this.password = value;
	}

	public void setSurname(String value) {
		this.surname = value;
	}

	public void setTelephone(String value) {
		this.telephone = value;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", address=" + address + ", country=" + country
				+ ", email=" + email + ", name=" + name + ", surname=" + surname + ", type=" + type + ", telephone=" + telephone + "]";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

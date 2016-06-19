package ftn.ra122013.webshop.beans;

public class Administrator extends User {


	private static final long serialVersionUID = -6738631037780389638L;

	public Administrator(String username, String password, String address, String country, String email, String name,
			String surname, String telephone) {
		super(username, password, address, country, email, name, surname, telephone);
	}
}

package ftn.ra122013.webshop.beans;
import java.util.Set;

public class Seller extends User {

	private static final long serialVersionUID = -821878942315251523L;

	public Seller(String username, String password, String address, String country, String email, String name,
			String surname, String telephone) {
		super(username, password, address, country, email, name, surname, telephone);
		setType("seller");
	}

	private Set<Store> store;

	public void addStore(Store store) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Set<Store> getStore() {
		return this.store;
	}

}

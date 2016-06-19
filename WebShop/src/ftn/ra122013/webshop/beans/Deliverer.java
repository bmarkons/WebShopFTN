package ftn.ra122013.webshop.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class Deliverer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9050378827855585941L;
	private String code;
	private ArrayList<String> countries;
	private String description;
	private String name;
	// private Set<Purchase> purchase;
	private ArrayList tariffs;

	public void addCountry(String country) {
		countries.add(country);
	}

	/* GETTERS */
	public String getCode() {
		return this.code;
	}

	public ArrayList getCountries() {
		return this.countries;
	}

	public String getDescription() {
		return this.description;
	}

	public String getName() {
		return this.name;
	}

	// public Set<Purchase> getPurchase() {
	// return this.purchase;
	// }
	public ArrayList getTariffs() {
		return this.tariffs;
	}

	/* SETTERS */
	public void setCode(String value) {
		this.code = value;
	}

	public void setDescription(String value) {
		this.description = value;
	}

	public void setName(String value) {
		this.name = value;
	}

	public void setTariffs(ArrayList value) {
		this.tariffs = value;
	}

	@Override
	public boolean equals(Object obj) {
		Deliverer del = (Deliverer) obj;
		return del.getCode().equals(code);
	}
}

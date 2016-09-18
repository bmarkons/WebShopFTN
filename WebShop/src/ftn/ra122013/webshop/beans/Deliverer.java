package ftn.ra122013.webshop.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class Deliverer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9050378827855585941L;
	private String code;
	private ArrayList<String> countries;
	private String description;
	private String name;
	private double weightRatio = -1;
	private double dimensionRatio = -1;
	private double minPrice = -1;

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	/* GETTERS */
	public double getWeightRatio() {
		return this.weightRatio;
	}

	public double getDimensionRatio() {
		return this.dimensionRatio;
	}

	public String getCode() {
		return this.code;
	}

	public ArrayList<String> getCountries() {
		return this.countries;
	}

	public String getDescription() {
		return this.description;
	}

	public String getName() {
		return this.name;
	}

	/* SETTERS */
	public void setWeightRatio(double value) {
		this.weightRatio = value;
	}

	public void setDimensionRatio(double value) {
		this.dimensionRatio = value;
	}

	public void setCode(String value) {
		this.code = value;
	}

	public void setDescription(String value) {
		this.description = value;
	}

	public void setName(String value) {
		this.name = value;
	}

	public void setCountries(ArrayList<String> countries) {
		this.countries = countries;
	}

	@Override
	public boolean equals(Object obj) {
		Deliverer del = (Deliverer) obj;
		return del.getCode().equals(code);
	}

	@Override
	public String toString() {
		return getName() + "," + getDescription() + "," + getCountries();
	}
}

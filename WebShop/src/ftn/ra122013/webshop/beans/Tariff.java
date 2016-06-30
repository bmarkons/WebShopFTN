package ftn.ra122013.webshop.beans;

import java.io.Serializable;

public class Tariff implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8366055604147540734L;
	
	private double minDimension;
	private double maxDimension;
	private double minWeight;
	private double maxWeight;
	private double price;
	
	public Tariff(double minDimension, double maxDimension, double minWeight, double maxWeight, double price) {
		super();
		this.minDimension = minDimension;
		this.maxDimension = maxDimension;
		this.minWeight = minWeight;
		this.maxWeight = maxWeight;
		this.price = price;
	}

	public double getMinDimension() {
		return minDimension;
	}

	public void setMinDimension(double minDimension) {
		this.minDimension = minDimension;
	}

	public double getMaxDimension() {
		return maxDimension;
	}

	public void setMaxDimension(double maxDimension) {
		this.maxDimension = maxDimension;
	}

	public double getMinWeight() {
		return minWeight;
	}

	public void setMinWeight(double minWeight) {
		this.minWeight = minWeight;
	}

	public double getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(double maxWeight) {
		this.maxWeight = maxWeight;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	
}

package ftn.ra122013.webshop.beans;

import java.io.Serializable;

public class Tariff implements Serializable {
	private static final long serialVersionUID = 8366055604147540734L;

	private double weightRatio;
	private double dimensionRatio;
	private double minPrice;

	public double getWeightRatio() {
		return weightRatio;
	}

	public void setWeightRatio(double weightRatio) {
		this.weightRatio = weightRatio;
	}

	public double getDimensionRatio() {
		return dimensionRatio;
	}

	public void setDimensionRatio(double dimensionRatio) {
		this.dimensionRatio = dimensionRatio;
	}

	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}
}

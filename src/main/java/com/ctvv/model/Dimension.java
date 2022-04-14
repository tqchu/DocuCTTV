package com.ctvv.model;

public class Dimension {
	private int dimensionId;
	private double length;

	public Dimension() {
	}

	public Dimension(double length, double width, double height) {
		this.length = length;
		this.width = width;
		this.height = height;
	}
	public Dimension(Dimension dimension){
		this.dimensionId = dimension.dimensionId;
		this.length = dimension.length;
		this.width = dimension.width;
		this.height = dimension.height;
	}
	public Dimension(int dimensionId, double length, double width, double height) {
		this.dimensionId = dimensionId;
		this.length = length;
		this.width = width;
		this.height = height;
	}

	private double width;
	private double height;

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public int getDimensionId() {
		return dimensionId;
	}

	public void setDimensionId(int dimensionId) {
		this.dimensionId = dimensionId;
	}
}

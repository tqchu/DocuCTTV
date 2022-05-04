package com.ctvv.model;

public class ImportDetail {
	private int importId;
	private int productId;
	private String productName;
	private int quantity;
	private int price;
	private double tax;

	public ImportDetail(int importId, int productId, String productName, int quantity, int price, double tax) {
		this.importId = importId;
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
		this.tax = tax;
	}

	public ImportDetail(int productId, String productName, int quantity, int price, double tax) {
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
		this.tax = tax;
	}

	public int getImportId() {
		return importId;
	}

	public void setImportId(int importId) {
		this.importId = importId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}
}

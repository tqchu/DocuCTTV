package com.ctvv.model;

import java.time.LocalDate;

public class Import {
	private int productId;
	private int price;
	private LocalDate importDay;
	private int quantity;

	public Import(){

	}
	public Import(Import pImport){
		this.productId = pImport.productId;
		this.price = pImport.price;
		this.importDay = pImport.importDay;
		this.quantity = pImport.quantity;
	}
	public Import(int productId, int price, LocalDate importDay, int quantity) {
		this.productId = productId;
		this.price = price;
		this.importDay = importDay;
		this.quantity = quantity;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public LocalDate getImportDay() {
		return importDay;
	}

	public void setImportDay(LocalDate importDay) {
		this.importDay = importDay;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}

package com.ctvv.model;

public class OrderDetail {
	private int orderId;
	private Product product;
	private String productName;
	private int quantity;
	private int price;

	public OrderDetail(OrderDetail orderDetail) {
		this.orderId = orderDetail.orderId;
		this.product = new Product(orderDetail.product);
		this.productName = orderDetail.productName;
		this.quantity = orderDetail.quantity;
		this.price = orderDetail.price;
	}

	public OrderDetail(int orderId, Product product, String productName, int quantity, int price) {
		this.orderId = orderId;
		this.product = new Product(product);
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
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
}

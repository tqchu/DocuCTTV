package com.ctvv.model;

public class OrderDetail {
	private String orderId;
	private int productId;
	private String productName ;
	private int quantity;
	private int price;

	public OrderDetail(OrderDetail orderDetail){
		this.orderId = orderDetail.orderId;
		this.productId = orderDetail.productId;
		this.productName = orderDetail.productName;
		this.quantity = orderDetail.quantity;
		this.price = orderDetail.price;
	}
	public OrderDetail(String orderId, int productId, String productName, int quantity, int price) {
		this.orderId = orderId;
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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
}

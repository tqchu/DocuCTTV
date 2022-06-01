package com.ctvv.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
	private String orderId;
	private int customerId;
	private String customerName;
	private String recipientName;
	private String phoneNumber;
	private String address;
	private LocalDateTime orderTime;
	private LocalDateTime confirmTime;
	private LocalDateTime shipTime;
	private LocalDateTime completedTime;
	private OrderStatus status;
	private int shippingFee;
	private long totalPrice;
	private List<OrderDetail> orderDetailList;

	public Order(Order order) {
		this.orderId = order.orderId;
		this.customerId = order.customerId;
		this.customerName = order.customerName;
		this.recipientName = order.recipientName;
		this.phoneNumber = order.phoneNumber;
		this.address = order.address;
		this.orderTime = order.orderTime;
		this.confirmTime = order.confirmTime;
		this.shipTime = order.shipTime;
		this.completedTime = order.completedTime;
		this.status = order.status;
		this.orderDetailList = new ArrayList<>(order.orderDetailList);
		this.shippingFee = order.shippingFee;
		this.totalPrice = order.totalPrice;
	}

	public Order(
			String orderId, int customerId, String customerName, String recipientName, String phoneNumber, String address,
			LocalDateTime orderTime,LocalDateTime confirmTime, LocalDateTime shipTime, LocalDateTime completedTime, OrderStatus status,
			List<OrderDetail> orderDetailList, int shippingFee) {
		this.orderId = orderId;
		this.customerId = customerId;
		this.customerName = customerName;
		this.recipientName = recipientName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.orderTime = orderTime;
		this.confirmTime = confirmTime;
		this.shipTime = shipTime;
		this.completedTime = completedTime;
		this.status = status;
		this.orderDetailList = new ArrayList<>(orderDetailList);
		this.shippingFee = shippingFee;
		setTotalPrice();
	}

	public Order() {

	}

	private void setTotalPrice() {
		totalPrice = 0;
		for (OrderDetail orderDetail : orderDetailList) {
			totalPrice += (long) orderDetail.getPrice()* orderDetail.getQuantity();
		}
		totalPrice += shippingFee;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDateTime getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(LocalDateTime orderTime) {
		this.orderTime = orderTime;
	}

	public LocalDateTime getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(LocalDateTime confirmTime) {
		this.confirmTime = confirmTime;
	}

	public LocalDateTime getShipTime() { return shipTime;}

	public void setShipTime(LocalDateTime shipTime) {
		this.shipTime = shipTime;
	}

	public LocalDateTime getCompletedTime() {
		return completedTime;
	}

	public void setCompletedTime(LocalDateTime completedTime) {
		this.completedTime = completedTime;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public List<OrderDetail> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<OrderDetail> orderDetailList) {
		this.orderDetailList = new ArrayList<>(orderDetailList);
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public int getShippingFee() {
		return shippingFee;
	}

	public void setShippingFee(int shippingFee) {
		this.shippingFee = shippingFee;
	}

	public long getTotalPrice() {
		return totalPrice;
	}


	public enum OrderStatus {
		PENDING,
		TO_SHIP,
		TO_RECEIVE,
		COMPLETED,
		CANCELED
	}
}

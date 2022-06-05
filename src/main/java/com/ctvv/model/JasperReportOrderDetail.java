package com.ctvv.model;

import java.util.ArrayList;
import java.util.List;

public class JasperReportOrderDetail {
	private int stt;
	private String productName;
	private int quantity;
	private int price;
	private long totalPrice;

	public JasperReportOrderDetail(int stt, String productName, int quantity, int price,
	                                long totalPrice) {
		this.stt = stt;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
		this.totalPrice = totalPrice;
	}

	public int getStt() {
		return stt;
	}

	public void setStt(int stt) {
		this.stt = stt;
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
	public long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}
	public static List<JasperReportOrderDetail> getJROrderDetailList(List<OrderDetail> orderDetailList){
		List<JasperReportOrderDetail> jrList = new ArrayList<>();
		int stt = 1;
		for (OrderDetail orderDetail :orderDetailList) {
			int quantity = orderDetail.getQuantity();
			String productName = orderDetail.getProductName();
			int price = orderDetail.getPrice();
			long totalPrice =  ((long) quantity* price );
			jrList.add(new JasperReportOrderDetail(stt++, productName, quantity,price,totalPrice));
		}
		return jrList;
	}
}

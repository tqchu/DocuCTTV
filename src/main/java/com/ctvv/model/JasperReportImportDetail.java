package com.ctvv.model;

import java.util.ArrayList;
import java.util.List;

public class JasperReportImportDetail {
	private int stt;
	private String productName;
	private int quantity;
	private int price;
	private String tax;
	private long totalPrice;

	public JasperReportImportDetail(int stt, String productName, int quantity, int price, String tax,
	                                long totalPrice) {
		this.stt = stt;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
		this.tax = tax;
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

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}
	public static List<JasperReportImportDetail> getJRImportDetailList(List<ImportDetail> importDetailList){
		List<JasperReportImportDetail> jrList = new ArrayList<>();
		int stt = 1;
		for (ImportDetail importDetail :importDetailList) {
			int quantity = importDetail.getQuantity();
			String productName = importDetail.getProductName();
			int price = importDetail.getPrice();
			double tax = importDetail.getTax();
			long totalPrice = (long) ((long) quantity* price * (1-tax));
			jrList.add(new JasperReportImportDetail(stt++, productName, quantity,price,tax*100 + "%",totalPrice));
		}
		return jrList;
	}
}

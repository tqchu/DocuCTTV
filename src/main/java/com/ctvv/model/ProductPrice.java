package com.ctvv.model;

import java.util.Objects;

public class ProductPrice {
	private ProductDetail productDetail;
	private int price;

	public ProductPrice(ProductPrice productPrice) {
		price = productPrice.price;
		productDetail = new ProductDetail(productPrice.productDetail);
	}

	public ProductPrice(ProductDetail productDetail, int price) {
		this.productDetail = productDetail;
		this.price = price;
	}

	public ProductDetail getProductDetail() {
		return productDetail;
	}

	public void setProductDetail(ProductDetail productDetail) {
		this.productDetail = productDetail;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}

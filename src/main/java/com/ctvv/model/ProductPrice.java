package com.ctvv.model;

import java.util.Objects;

public class ProductPrice {
	private int productId;
	private Material material;
	private Dimension dimension;
	private int price;

	public ProductPrice(ProductPrice productPrice) {
		productId = productPrice.productId;
		material = new Material(productPrice.material);
		dimension = new Dimension(productPrice.dimension);
		price = productPrice.price;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ProductPrice that = (ProductPrice) o;
		return productId == that.productId && material.equals(that.material) && dimension.equals(that.dimension);
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId, material, dimension);
	}

	public ProductPrice(int productId, Material material, Dimension dimension, int price) {
		this.productId = productId;
		this.material = material;
		this.dimension = dimension;
		this.price = price;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}

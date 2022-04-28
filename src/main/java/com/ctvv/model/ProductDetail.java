package com.ctvv.model;

import java.util.Objects;

public class ProductDetail {
	private int productDetailId;
	private int productId;
	private Material material;
	private Dimension dimension;

	public ProductDetail(ProductDetail productDetail) {
		productId = productDetail.productId;
		material = new Material(productDetail.material);
		dimension = new Dimension(productDetail.dimension);
		productDetailId = productDetail.productDetailId;

	}

	public ProductDetail(int productDetailId, int productId, Material material, Dimension dimension) {
		this.productDetailId = productDetailId;
		this.productId = productId;
		this.material = new Material(material);
		this.dimension = new Dimension(dimension);
	}

	public ProductDetail(int productId, Material material, Dimension dimension) {
		this.productId = productId;
		this.material = new Material(material);
		this.dimension = new Dimension(dimension);
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
		this.material = new Material(material);
	}

	public Dimension getDimension() {
		return dimension;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = new Dimension(dimension);
	}

	public int getProductDetailId() {
		return productDetailId;
	}

	public void setProductDetailId(int productDetailId) {
		this.productDetailId = productDetailId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId, material, dimension);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ProductDetail that = (ProductDetail) o;
		return productDetailId == ((ProductDetail) o).productDetailId || (productId == that.productId && material.equals(that.material) && dimension.equals(that.dimension));
	}
}

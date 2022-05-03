package com.ctvv.model;

public class ProductDimension {
	private int productId;
	private int dimensionId;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getDimensionId() {
		return dimensionId;
	}

	public ProductDimension(int productId, int dimensionId) {
		this.productId = productId;
		this.dimensionId = dimensionId;
	}

	public void setDimensionId(int dimensionId) {
		this.dimensionId = dimensionId;
	}
}

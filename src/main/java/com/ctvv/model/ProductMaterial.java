package com.ctvv.model;

public class ProductMaterial {
	private int productId;
	private int materialId;

	public ProductMaterial(int productId, int materialId) {
		this.productId = productId;
		this.materialId = materialId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getMaterialId() {
		return materialId;
	}

	public void setMaterialId(int materialId) {
		this.materialId = materialId;
	}
}

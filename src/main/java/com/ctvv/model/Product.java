package com.ctvv.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Product {

	private int productId;
	private String name;
	private int warrantyPeriod;
	private String material;
	private String dimension;
	private String description;
	private Category category;
	private List<ImagePath> imagePathList;

	public Product() {
	}


	public Product(
			int productId, String name, int warrantyPeriod, String material, String dimension, String description,
			Category category, List<ImagePath> imagePathList) {
		this.productId = productId;
		this.name = name;
		this.warrantyPeriod = warrantyPeriod;
		this.material = material;
		this.dimension = dimension;
		this.description = description;
		this.category = category;
		this.imagePathList = new ArrayList<>(imagePathList);
	}
	public Product(
			String name, int warrantyPeriod, String material, String dimension, String description,
			Category category, List<ImagePath> imagePathList) {
		this.name = name;
		this.warrantyPeriod = warrantyPeriod;
		this.material = material;
		this.dimension = dimension;
		this.description = description;
		this.category = category;
		this.imagePathList = new ArrayList<>(imagePathList);

	}

	public Product(String name, int warrantyPeriod, String material, String dimension, String description, Category category) {
		this.name = name;
		this.warrantyPeriod = warrantyPeriod;
		this.material = material;
		this.dimension = dimension;
		this.description = description;
		this.category = category;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWarrantyPeriod() {
		return warrantyPeriod;
	}

	public void setWarrantyPeriod(int warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getDimension() {
		return dimension;
	}

	public void setDimension(String dimension) {
		this.dimension = dimension;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<ImagePath> getImagePathList() {
		return imagePathList;
	}

	public void setImagePathList(List<ImagePath> imagePathList) {
		this.imagePathList = new ArrayList<>(imagePathList);

	}

	@Override
	public int hashCode() {
		return Objects.hash(productId);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return productId == product.productId;
	}

}

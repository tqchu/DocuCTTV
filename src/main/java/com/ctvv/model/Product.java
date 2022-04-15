package com.ctvv.model;

import java.util.ArrayList;
import java.util.List;

public class Product {
	public List<Dimension> getDimensionList() {
		return dimensionList;
	}

	public void setDimensionList(List<Dimension> dimensionList) {
		this.dimensionList = dimensionList;
	}

	public List<Material> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<Material> materialList) {
		this.materialList = materialList;
	}

	public List<ImagePath> getImagePathList() {
		return imagePathList;
	}

	public void setImagePathList(List<ImagePath> imagePathList) {
		this.imagePathList = imagePathList;
	}

	private int productId;
	private String name;
	private int warrantyPeriod;
	private int quantity;
	private String description;
	private Category category;
	List<Dimension> dimensionList;
	List<Material> materialList;
	List<ImagePath> imagePathList;

	public Product() {
	}
	public Product(Product product) {
		this.productId=product.productId;
		this.name = product.name;
		this.warrantyPeriod = product.warrantyPeriod;
		this.quantity = product.quantity;
		this.description = product.description;
		this.category = new Category(product.category);
		this.dimensionList = new ArrayList<>(product.dimensionList);
		this.materialList = new ArrayList<>(product.materialList);
		this.imagePathList = new ArrayList<>(product.imagePathList);
	}

	public Product(int id,
				   String name, int warrantyPeriod, int quantity, String description, Category category,
				   List<Dimension> dimensionList, List<Material> materialList, List<ImagePath> imagePathList) {
		this.productId=id;
		this.name = name;
		this.warrantyPeriod = warrantyPeriod;
		this.quantity = quantity;
		this.description = description;
		this.category = new Category(category);
		this.dimensionList = new ArrayList<>(dimensionList);
		this.materialList = new ArrayList<>(materialList);
		this.imagePathList = new ArrayList<>(imagePathList);
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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
}
package com.ctvv.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Product {

	private int productId;
	private String name;
	private int warrantyPeriod;
	private String description;
	private Category category;
	private List<ImagePath> imagePathList;
	private boolean status;
	private List<ProductPrice> productPrice;

	public Product() {
	}

	public Product(
			String name, int warrantyPeriod, String description, Category category,
			boolean status,List<ProductPrice> productPrice) {
		this.name = name;
		this.warrantyPeriod = warrantyPeriod;
		this.description = description;
		this.status = status;
		if (category != null)
			this.category = new Category(category);
		this.productPrice = new ArrayList<>(productPrice);
	}

	public Product(int id, String name, int warrantyPeriod, String description, Category category) {
		this.productId = id;
		this.name = name;
		this.warrantyPeriod = warrantyPeriod;
		this.description = description;
		if (category != null)
			this.category = new Category(category);
	}


	public Product(Product product) {
		this.productId = product.productId;
		this.name = product.name;
		this.warrantyPeriod = product.warrantyPeriod;
		this.description = product.description;
		this.status = product.status;
		this.category = new Category(product.category);
		this.imagePathList = new ArrayList<>(product.imagePathList);
		this.productPrice = new ArrayList<>(product.productPrice);
	}

	public Product(
			int id,
			String name, int warrantyPeriod, String description, boolean status,
			Category category,
			 List<ImagePath> imagePathList, ProductPrice productPrice) {
		this.productId = id;
		this.name = name;
		this.warrantyPeriod = warrantyPeriod;
		this.description = description;
		this.status = status;
		if (category != null)
			this.category = new Category(category);
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
		this.imagePathList = imagePathList;
	}

	public List<ProductPrice> getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(List<ProductPrice> productPrice) {
		this.productPrice = productPrice;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}

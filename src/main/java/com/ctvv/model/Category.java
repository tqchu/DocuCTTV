package com.ctvv.model;

import java.util.Objects;

public class Category {
	private int categoryId;
	private String categoryName;

	public Category() {
	}

	public Category(int categoryId, String categoryName) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Category category = (Category) o;
		return categoryId == category.categoryId && categoryName.equals(category.categoryName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(categoryId, categoryName);
	}

	public Category(Category category) {
		this.categoryId= category.categoryId;
		this.categoryName=category.categoryName;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}

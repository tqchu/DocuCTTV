package com.ctvv.model;

import java.util.Objects;

public class Material {
	private int materialId;
	private String materialName;

	public Material() {
	}

	public Material(Material material){
		this.materialId = material.materialId;
		this.materialName = material.materialName;
	}
	public Material(String materialName) {
		this.materialName = materialName;
	}

	public Material(int materialId, String materialName) {
		this.materialId = materialId;
		this.materialName = materialName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Material material = (Material) o;
		return materialName.equals(material.materialName) || (this.materialId==material.materialId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(materialName);
	}

	public int getMaterialId() {
		return materialId;
	}

	public void setMaterialId(int materialId) {
		this.materialId = materialId;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
}

package com.ctvv.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Import {
	private int importId;
	private String importerName;
	private int providerId;
	private String providerTaxId;
	private String providerName;
	private LocalDateTime importDate;
	private long totalPrice;
	private List<ImportDetail> importDetailList;

	public Import() {

	}

	public Import(
			int importId, String importerName, int providerId,String providerTaxId, String providerName,
			LocalDateTime importDate,
			long totalPrice,
			List<ImportDetail> importDetailList) {
		this.importId = importId;
		this.importerName = importerName;
		this.providerId = providerId;
		this.providerTaxId = providerTaxId;
		this.providerName = providerName;
		this.importDate = importDate;
		this.totalPrice = totalPrice;
		this.importDetailList = new ArrayList<>(importDetailList);

	}

	public Import(
			String importerName, int providerId, String providerName, String providerTaxId,LocalDateTime importDate,
			long totalPrice,
			List<ImportDetail> importDetailList) {
		this.importerName = importerName;
		this.providerId = providerId;
		this.providerTaxId = providerTaxId;
		this.providerName = providerName;
		this.importDate = importDate;
		this.totalPrice = totalPrice;
		this.importDetailList = new ArrayList<>(importDetailList);

	}


	public int getImportId() {
		return importId;
	}

	public void setImportId(int importId) {
		this.importId = importId;
	}

	public String getImporterName() {
		return importerName;
	}

	public void setImporterName(String importerName) {
		this.importerName = importerName;
	}

	public int getProviderId() {
		return providerId;
	}

	public void setProviderId(int providerId) {
		this.providerId = providerId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public LocalDateTime getImportDate() {
		return importDate;
	}

	public void setImportDate(LocalDateTime importDate) {
		this.importDate = importDate;
	}

	public long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<ImportDetail> getImportDetailList() {
		return importDetailList;
	}

	public void setImportDetailList(List<ImportDetail> importDetailList) {
		this.importDetailList = new ArrayList<>(importDetailList);
	}

	public String getProviderTaxId() {
		return providerTaxId;
	}

	public void setProviderTaxId(String providerTaxId) {
		this.providerTaxId = providerTaxId;
	}
}

package com.ctvv.model;

import java.util.Objects;

public class Provider {
	int providerId;
	String providerName;
	String address;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Provider provider = (Provider) o;
		return providerId == provider.providerId || providerName.equals(provider.providerName)  || phoneNumber.equals(provider.phoneNumber) || email.equals(provider.email) && taxId.equals(provider.taxId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(providerId, providerName, address, phoneNumber, email, taxId);
	}

	String phoneNumber;
	String email;
	String taxId;

	public Provider(
			int providerId, String providerName, String address, String phoneNumber, String email,
			String taxId) {
		this.providerId = providerId;
		this.providerName = providerName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.taxId = taxId;
	}

	public Provider(String providerName, String address, String phoneNumber, String email, String taxId) {
		this.providerName = providerName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.taxId = taxId;
	}

	public Provider() {

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
}

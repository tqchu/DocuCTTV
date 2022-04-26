package com.ctvv.model;

public class Provider {
    int providerId;
    String providerName;
    String address;
    String phoneNumber;
    String email;
    String taxIdNumber;

    public Provider(int providerId, String providerName, String address, String phoneNumber, String email, String taxIdNumber) {
        this.providerId = providerId;
        this.providerName = providerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.taxIdNumber = taxIdNumber;
    }

    public Provider(String providerName, String address, String phoneNumber, String email, String taxIdNumber) {
        this.providerName = providerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.taxIdNumber = taxIdNumber;
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

    public String getTaxIdNumber() {
        return taxIdNumber;
    }

    public void setTaxIdNumber(String taxIdNumber) {
        this.taxIdNumber = taxIdNumber;
    }
}

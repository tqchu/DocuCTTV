package com.ctvv.model;

public class Address {
    String detailedAddress;
    String subDistrict;
    String district;
    String city;

    public Address(int customer_id, String detailedAddress, String subDistrict, String district, String city) {
        this.detailedAddress = detailedAddress;
        this.subDistrict = subDistrict;
        this.district = district;
        this.city = city;
    }

    public Address(Address address) {
        this.detailedAddress = address.detailedAddress;
        this.subDistrict = address.subDistrict;
        this.district = address.district;
        this.city = address.city;
    }

    public String getAddress() {
        return this.detailedAddress + ", " + this.subDistrict + ", " + this.district + ", " + this.city;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setCity(String city) {
        this.city = district;
    }
}

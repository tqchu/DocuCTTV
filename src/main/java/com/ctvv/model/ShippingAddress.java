package com.ctvv.model;

public class ShippingAddress {
    String recipientName;
    String phoneNumber;
    String address;

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ShippingAddress(String recipientName, String phoneNumber, String address) {
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }


}

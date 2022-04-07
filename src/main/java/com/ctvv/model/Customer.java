package com.ctvv.model;
import java.time.LocalDate;
public class Customer {
    public enum Gender{
        FEMALE,
        MALE,
        OTHER;
        public int getValue()
        {
            if (this == FEMALE) return 0;
            else if (this == MALE) return 1;
            else return 2;
        }
    }
    private int userId;
    private String password;
    private Gender gender;
    private String fullName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    ShippingAddress address;

    public ShippingAddress getAddress() {
        return address;
    }

    public void setAddress(ShippingAddress address) {
        this.address = address;
    }

    public Customer(int user_id, String password, String fullName, String phoneNumber, Gender gender, LocalDate DoB,
                    ShippingAddress address) {
        this.userId = user_id;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.dateOfBirth = DoB;
        this.address=address;
    }

    public Customer(int user_id, String password) {
        this.userId = user_id;
        this.password = password;
    }

    public Customer(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public Customer(Customer customer) {
        this.userId = customer.userId;
        this.password = customer.password;
        this.fullName = customer.fullName;
        this.phoneNumber = customer.phoneNumber;
        this.gender = customer.gender;
        this.dateOfBirth = customer.dateOfBirth;
        this.address=new ShippingAddress(customer.address);
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return this.gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(LocalDate date) {
        this.dateOfBirth = date;
    }


}

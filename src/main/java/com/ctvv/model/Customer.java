package com.ctvv.model;

import java.util.Date;


public class Customer {
    int user_id;
    String username;
    String password;
    String fullname;
    String phonenumber;
    boolean gender;
    Date DoB;
    String address; //A customer is possible to have more than one address.

    public Customer(int user_id, String username, String password, String fullname, String phonenumber, boolean gender, Date DoB, String address) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.phonenumber = phonenumber;
        this.gender = gender;       //default: male is 1, female is 0
        this.DoB = DoB;
        this.address = address;
    }

    public Customer(int user_id, String username, String password) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
    }

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Customer(Customer customer) {
        this.user_id = customer.user_id;
        this.username = customer.username;
        this.password = customer.password;
        this.fullname = customer.fullname;
        this.phonenumber = customer.phonenumber;
        this.gender = customer.gender;
        this.DoB = customer.DoB;
        this.address = customer.address;
    }

    public int getUser_id() {
        return this.user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public boolean getGender() {
        return this.gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getDoB() {
        return this.DoB;
    }

    public void setDoB(Date date) {
        this.DoB = date;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}

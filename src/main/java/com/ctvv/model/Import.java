package com.ctvv.model;
import java.time.LocalDate;
public class Import {
    private int productId;
    private int price;
    private LocalDate localDate;
    private int quantity;
    public Import(){
    }
    public Import(Import pImport){
        this.productId = pImport.productId;
        this.price = pImport.price;
        this.localDate = pImport.localDate;
        this.quantity = pImport.quantity;
    }
    public Import(int productId, int price, LocalDate localDate, int quantity) {
        this.productId = productId;
        this.price = price;
        this.localDate = localDate;
        this.quantity = quantity;
    }
    public int getProductId() {
        return productId;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public LocalDate getLocalDate() {
        return localDate;
    }
    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
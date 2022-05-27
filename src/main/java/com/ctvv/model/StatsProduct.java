package com.ctvv.model;

public class StatsProduct {
    private Product product;
    private int importQuantity;
    private int soldQuantity;
    private long revenue;

    public StatsProduct(Product product,  int importQuantity,int soldQuantity, long revenue) {
        this.product = product;
        this.soldQuantity = soldQuantity;
        this.importQuantity = importQuantity;
        this.revenue = revenue;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity(int soldQuantity) {
        this.soldQuantity = soldQuantity;
    }

    public int getImportQuantity() {
        return importQuantity;
    }

    public void setImportQuantity(int importQuantity) {
        this.importQuantity = importQuantity;
    }

    public long getRevenue() {
        return revenue;
    }

    public void setRevenue(long revenue) {
        this.revenue = revenue;
    }
}

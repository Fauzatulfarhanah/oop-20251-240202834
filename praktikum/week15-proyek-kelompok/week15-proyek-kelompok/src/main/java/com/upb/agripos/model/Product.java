package com.upb.agripos.model;
import java.math.BigDecimal;

public class Product {
    private String code;
    private String name;
    private String category;
    private BigDecimal price;
    private int stock;

    public Product() {}
    public Product(String code, String name, String category, BigDecimal price, int stock) {
        this.code = code;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }
    // Getters & Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    
    @Override public String toString() { return name; }
}
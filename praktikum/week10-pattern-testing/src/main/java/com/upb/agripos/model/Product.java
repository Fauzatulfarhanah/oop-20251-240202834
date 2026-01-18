package com.upb.agripos.model;

public class Product {
    private final String code;
    private final String name;
    private final double price;
    private int stock;

    //  constructor Week 9 
    public Product(String code, String name, double price, int stock) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    //  constructor tambahan untuk Week 10 (MVC)
    public Product(String code, String name) {
        this.code = code;
        this.name = name;
        this.price = 0.0;
        this.stock = 0;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    public void reduceStock(int qty) {
        this.stock -= qty;
    }
}

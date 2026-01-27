package com.upb.agripos.model;

import java.math.BigDecimal;

public class TransactionItem {
    private int id;
    private String productCode;
    private String productName;
    private int quantity;
    private BigDecimal priceAtSale;
    private BigDecimal subtotal;

    public TransactionItem() {}

    public TransactionItem(Product product, int quantity) {
        this.productCode = product.getCode();
        this.productName = product.getName();
        this.quantity = quantity;
        this.priceAtSale = product.getPrice();
        this.subtotal = product.getPrice().multiply(new BigDecimal(quantity));
    }

    public BigDecimal getSubtotal() {
        return priceAtSale.multiply(new BigDecimal(quantity));
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public BigDecimal getPriceAtSale() { return priceAtSale; }
    public void setPriceAtSale(BigDecimal priceAtSale) { this.priceAtSale = priceAtSale; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
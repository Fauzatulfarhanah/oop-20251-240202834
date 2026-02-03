package com.upb.agripos.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity harus lebih dari 0");
        }

        for (CartItem item : items) {
            if (item.getProduct().getCode().equals(product.getCode())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }

        items.add(new CartItem(product, quantity));
    }

    public void removeItem(String productCode) {
        items.removeIf(item -> item.getProduct().getCode().equals(productCode));
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public int getTotalItems() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}

package com.upb.agripos.service;

import com.upb.agripos.model.Cart;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;
import java.util.List;

public class CartService {
    private final Cart cart;

    public CartService() {
        this.cart = new Cart();
    }

    public void addToCart(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Produk tidak boleh null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity harus lebih dari 0");
        }
        if (quantity > product.getStock()) {
            throw new IllegalArgumentException("Stok tidak mencukupi");
        }

        cart.addItem(product, quantity);
    }

    public void removeFromCart(String productCode) {
        cart.removeItem(productCode);
    }

    public List<CartItem> getCartItems() {
        return cart.getItems();
    }

    public double calculateTotal() {
        return cart.getTotal();
    }

    public int getTotalItems() {
        return cart.getTotalItems();
    }

    public void clearCart() {
        cart.clear();
    }

    public boolean isCartEmpty() {
        return cart.isEmpty();
    }
}

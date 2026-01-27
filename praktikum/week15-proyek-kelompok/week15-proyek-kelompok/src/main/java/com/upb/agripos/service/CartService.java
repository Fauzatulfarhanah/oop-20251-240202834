package com.upb.agripos.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;

public class CartService {
    private Map<String, CartItem> items = new HashMap<>();

    public void addToCart(Product p, int qty) {
        String code = p.getCode();
        if (items.containsKey(code)) {
            CartItem existing = items.get(code);
            existing.setQuantity(existing.getQuantity() + qty);
        } else {
            items.put(code, new CartItem(p, qty));
        }
    }
    public void updateQuantity(String code, int qty) {
        if (items.containsKey(code)) {
            if (qty <= 0) items.remove(code);
            else items.get(code).setQuantity(qty);
        }
    }
    public void removeFromCart(String code) { items.remove(code); }
    public void clearCart() { items.clear(); }
    public List<CartItem> getCartItems() { return new ArrayList<>(items.values()); }
    public BigDecimal getTotal() {
        return items.values().stream().map(CartItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
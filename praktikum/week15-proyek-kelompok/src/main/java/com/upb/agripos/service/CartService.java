package com.upb.agripos.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upb.agripos.exception.OutOfStockException;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;

public class CartService {
    private Map<String, CartItem> items = new HashMap<>();

    public void addToCart(Product product, int qty) throws Exception {
        if (qty <= 0) {
            throw new Exception("Jumlah harus lebih dari 0");
        }
        
        if (qty > product.getStock()) {
            throw new OutOfStockException("Stok tidak cukup! Tersedia: " + product.getStock());
        }
        
        String code = product.getCode();
        
        if (items.containsKey(code)) {
            CartItem existing = items.get(code);
            int newQty = existing.getQuantity() + qty;
            
            if (newQty > product.getStock()) {
                throw new OutOfStockException("Stok tidak cukup! Tersedia: " + product.getStock());
            }
            
            existing.setQuantity(newQty);
        } else {
            items.put(code, new CartItem(product, qty));
        }
    }
    
    // TAMBAHKAN METHOD INI (yang hilang)
    public void removeFromCart(String productCode) {
        items.remove(productCode);
    }
    
    // TAMBAHKAN METHOD INI JUGA
    public void updateQuantity(String productCode, int newQty) throws Exception {
        if (!items.containsKey(productCode)) {
            throw new Exception("Produk tidak ada di keranjang");
        }
        
        if (newQty <= 0) {
            removeFromCart(productCode);
        } else {
            CartItem item = items.get(productCode);
            
            if (newQty > item.getProduct().getStock()) {
                throw new OutOfStockException("Stok tidak cukup! Tersedia: " + 
                    item.getProduct().getStock());
            }
            
            item.setQuantity(newQty);
        }
    }

    public List<CartItem> getCartItems() { 
        return new ArrayList<>(items.values()); 
    }
    
    public void clearCart() { 
        items.clear(); 
    }
    
    public BigDecimal getTotal() {
        return items.values().stream()
            .map(CartItem::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public int getTotalItems() {
        return items.values().stream()
            .mapToInt(CartItem::getQuantity)
            .sum();
    }
    
    public boolean isEmpty() {
        return items.isEmpty();
    }
}
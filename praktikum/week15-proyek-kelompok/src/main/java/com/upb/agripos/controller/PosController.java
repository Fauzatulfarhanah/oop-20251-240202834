package com.upb.agripos.controller;

import java.math.BigDecimal;
import java.util.List;

import com.upb.agripos.model.*;
import com.upb.agripos.service.*;

public class PosController {
    private final ProductService productService;
    private final CartService cartService;
    private final TransactionService transactionService;
    private final PaymentService paymentService;
    private final AuthService authService;

    public PosController(ProductService ps, CartService cs, 
                        TransactionService ts, PaymentService pys,
                        AuthService as) {
        this.productService = ps;
        this.cartService = cs;
        this.transactionService = ts;
        this.paymentService = pys;
        this.authService = as;
    }

    // Product Operations
    public List<Product> loadProducts() throws Exception { 
        return productService.getAllProducts(); 
    }
    
    public void addProduct(String code, String name, String category, 
                          BigDecimal price, int stock) throws Exception {
        Product product = new Product(code, name, category, price, stock);
        productService.addProduct(product);
    }
    
    public void updateProduct(Product product) throws Exception {
        productService.updateProduct(product);
    }
    
    public void deleteProduct(String code) throws Exception { 
        productService.deleteProduct(code); 
    }

    // Cart Operations
    public void addToCart(Product p, int qty) throws Exception { 
        cartService.addToCart(p, qty); 
    }
    
    public void removeFromCart(String productCode) {
        cartService.removeFromCart(productCode);
    }
    
    public void updateCartQuantity(String productCode, int newQty) throws Exception {
        cartService.updateQuantity(productCode, newQty);
    }
    
    public List<CartItem> getCartItems() { 
        return cartService.getCartItems(); 
    }
    
    public BigDecimal getCartTotal() { 
        return cartService.getTotal(); 
    }
    
    public void clearCart() { 
        cartService.clearCart(); 
    }

    // Transaction Operations
    public Transaction checkout(PaymentMethod paymentMethod) throws Exception {
        User currentUser = authService.getCurrentUser();
        
        if (currentUser == null) {
            throw new Exception("User belum login!");
        }
        
        List<CartItem> items = cartService.getCartItems();
        Transaction transaction = transactionService.processTransaction(
            items, paymentMethod, currentUser
        );
        
        cartService.clearCart();
        return transaction;
    }
    
    public Receipt generateReceipt(Transaction transaction) {
        return paymentService.generateReceipt(transaction);
    }
    
    // Auth helper methods
    public AuthService getAuthService() {
        return authService;
    }
}
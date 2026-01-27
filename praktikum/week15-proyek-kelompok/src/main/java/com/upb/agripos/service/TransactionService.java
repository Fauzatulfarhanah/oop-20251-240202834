package com.upb.agripos.service;

import com.upb.agripos.dao.TransactionDAO;
import com.upb.agripos.exception.OutOfStockException;
import com.upb.agripos.exception.PaymentFailedException;
import com.upb.agripos.model.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    private TransactionDAO transactionDAO;
    private ProductService productService;
    
    public TransactionService(TransactionDAO transactionDAO, ProductService productService) {
        this.transactionDAO = transactionDAO;
        this.productService = productService;
    }
    
    public Transaction processTransaction(List<CartItem> cartItems, PaymentMethod paymentMethod, User cashier) throws Exception {
        if (cartItems == null || cartItems.isEmpty()) {
            throw new Exception("Keranjang belanja kosong!");
        }
        
        for (CartItem item : cartItems) {
            if (!productService.validateStock(item.getProduct().getCode(), item.getQuantity())) {
                throw new OutOfStockException("Stok tidak cukup untuk: " + item.getProduct().getName());
            }
        }
        
        Transaction transaction = new Transaction();
        transaction.setId(generateTransactionId());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setCashier(cashier);
        transaction.setPaymentMethod(paymentMethod);
        
        List<TransactionItem> transItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            transItems.add(new TransactionItem(cartItem.getProduct(), cartItem.getQuantity()));
        }
        transaction.setItems(transItems);
        transaction.setTotalAmount(transaction.calculateTotal());
        
        if (!paymentMethod.processPayment(transaction.getTotalAmount())) {
            throw new PaymentFailedException("Pembayaran gagal diproses");
        }
        
        transactionDAO.save(transaction);
        
        for (CartItem item : cartItems) {
            productService.reduceStock(item.getProduct().getCode(), item.getQuantity());
        }
        
        return transaction;
    }
    
    private String generateTransactionId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String datePart = LocalDateTime.now().format(formatter);
        return "TRX" + datePart + String.format("%03d", System.currentTimeMillis() % 1000);
    }

    public void saveTransaction(Transaction t) throws Exception { transactionDAO.save(t); }
    public Transaction getTransactionById(String id) throws Exception { return transactionDAO.findById(id); }
}
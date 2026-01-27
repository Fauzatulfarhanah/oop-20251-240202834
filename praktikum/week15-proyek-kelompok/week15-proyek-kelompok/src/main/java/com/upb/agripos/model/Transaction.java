package com.upb.agripos.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Transaction {
    private String id;
    private LocalDateTime transactionDate;
    private User cashier;
    private PaymentMethod paymentMethod;
    private BigDecimal totalAmount;
    private List<TransactionItem> items;
    private String status;

    // Getters & Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public LocalDateTime getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDateTime transactionDate) { this.transactionDate = transactionDate; }
    public User getCashier() { return cashier; }
    public void setCashier(User cashier) { this.cashier = cashier; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public List<TransactionItem> getItems() { return items; }
    public void setItems(List<TransactionItem> items) { this.items = items; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
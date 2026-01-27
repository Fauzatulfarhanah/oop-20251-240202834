package com.upb.agripos.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Receipt {
    private Transaction transaction;
    private LocalDateTime generatedAt;

    public Receipt(Transaction transaction) {
        this.transaction = transaction;
        this.generatedAt = LocalDateTime.now();
    }

    public String formatReceipt() {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        
        sb.append("====================================\n");
        sb.append("          AGRI-POS STRUK BELANJA     \n");
        sb.append("====================================\n");
        sb.append("No. Transaksi: ").append(transaction.getId()).append("\n");
        sb.append("Tanggal: ").append(transaction.getTransactionDate().format(formatter)).append("\n");
        sb.append("Kasir: ").append(transaction.getCashier().getName()).append("\n");
        sb.append("------------------------------------\n");
        
        for (TransactionItem item : transaction.getItems()) {
            sb.append(String.format("%-20s %2dx %,10.0f\n", 
                item.getProductName(), item.getQuantity(), item.getPriceAtSale()));
            sb.append(String.format("%34s %,10.0f\n", "", item.getSubtotal()));
        }
        
        sb.append("====================================\n");
        sb.append(String.format("TOTAL: %26s\n", String.format("Rp %,10.0f", transaction.getTotalAmount())));
        sb.append("Metode: ").append(transaction.getPaymentMethod().getMethodName()).append("\n");
        sb.append("====================================\n");
        sb.append("   Terima Kasih Atas Kunjungan Anda\n");
        sb.append("====================================\n");
        
        return sb.toString();
    }

    public Transaction getTransaction() { return transaction; }
    public LocalDateTime getGeneratedAt() { return generatedAt; }
}
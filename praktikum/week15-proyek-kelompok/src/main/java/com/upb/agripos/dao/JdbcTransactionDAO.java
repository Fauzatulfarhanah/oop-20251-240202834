package com.upb.agripos.dao;

import com.upb.agripos.model.Transaction;
import com.upb.agripos.model.TransactionItem;
import com.upb.agripos.util.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransactionDAO implements TransactionDAO {
    
    private Connection getConnection() throws Exception {
        return DatabaseConnection.getInstance().getConnection();
    }
    
    @Override
    public void save(Transaction transaction) throws Exception {
        Connection conn = getConnection();
        try {
            conn.setAutoCommit(false);
            
            String sqlTrans = "INSERT INTO transactions (id, transaction_date, cashier_id, " +
                            "payment_method, total_amount, status, notes) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sqlTrans)) {
                pstmt.setString(1, transaction.getId());
                pstmt.setTimestamp(2, Timestamp.valueOf(transaction.getTransactionDate()));
                pstmt.setInt(3, transaction.getCashier().getId());
                pstmt.setString(4, transaction.getPaymentMethod().getMethodName());
                pstmt.setBigDecimal(5, transaction.getTotalAmount());
                pstmt.setString(6, transaction.getStatus());
                pstmt.setString(7, transaction.getNotes());
                pstmt.executeUpdate();
            }
            
            String sqlItems = "INSERT INTO transaction_items (transaction_id, product_code, " +
                            "product_name, quantity, price_at_sale, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
            
            try (PreparedStatement pstmt = conn.prepareStatement(sqlItems)) {
                for (TransactionItem item : transaction.getItems()) {
                    pstmt.setString(1, transaction.getId());
                    pstmt.setString(2, item.getProductCode());
                    pstmt.setString(3, item.getProductName());
                    pstmt.setInt(4, item.getQuantity());
                    pstmt.setBigDecimal(5, item.getPriceAtSale());
                    pstmt.setBigDecimal(6, item.getSubtotal());
                    pstmt.executeUpdate();
                }
            }
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override public Transaction findById(String id) { return null; }
    @Override public List<Transaction> findByDate(LocalDate date) { return new ArrayList<>(); }
    @Override public List<Transaction> findByPeriod(LocalDate s, LocalDate e) { return new ArrayList<>(); }
}
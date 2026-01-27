package com.upb.agripos.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.upb.agripos.dao.TransactionDAO;
import com.upb.agripos.model.Transaction;

public class ReportService {
    private TransactionDAO transactionDAO;
    
    public ReportService(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }
    
    public List<Transaction> getDailySales(LocalDate date) throws Exception {
        return transactionDAO.findByDate(date);
    }
    
    public List<Transaction> getSalesByPeriod(LocalDate start, LocalDate end) throws Exception {
        return transactionDAO.findByPeriod(start, end);
    }
    
    public BigDecimal calculateTotalRevenue(List<Transaction> transactions) {
        return transactions.stream()
            .map(Transaction::getTotalAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public int countTransactions(List<Transaction> transactions) {
        return transactions.size();
    }
}
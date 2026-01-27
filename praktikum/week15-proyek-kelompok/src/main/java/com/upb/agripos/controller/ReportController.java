package com.upb.agripos.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.upb.agripos.model.Transaction;
import com.upb.agripos.service.ReportService;

public class ReportController {
    private ReportService reportService;
    
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }
    
    public List<Transaction> getDailySalesReport(LocalDate date) throws Exception {
        return reportService.getDailySales(date);
    }
    
    public List<Transaction> getPeriodSalesReport(LocalDate start, LocalDate end) throws Exception {
        return reportService.getSalesByPeriod(start, end);
    }
    
    public BigDecimal calculateRevenue(List<Transaction> transactions) {
        return reportService.calculateTotalRevenue(transactions);
    }
}
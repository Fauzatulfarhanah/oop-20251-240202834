package com.upb.agripos.model;

import java.math.BigDecimal;

public class CashPayment implements PaymentMethod {
    private BigDecimal amountPaid;
    private BigDecimal change;
    
    public CashPayment() {
        this.amountPaid = BigDecimal.ZERO;
    }
    
    public CashPayment(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }
    
    @Override
    public boolean processPayment(BigDecimal amount) throws Exception {
        if (amountPaid.compareTo(amount) < 0) {
            throw new Exception("Uang tidak cukup! Kurang: Rp " + 
                amount.subtract(amountPaid));
        }
        this.change = amountPaid.subtract(amount);
        return true;
    }
    
    @Override
    public String getMethodName() {
        return "CASH";
    }
    
    public BigDecimal calculateChange(BigDecimal amount) {
        return amountPaid.subtract(amount);
    }
    
    public BigDecimal getAmountPaid() { return amountPaid; }
    public void setAmountPaid(BigDecimal amountPaid) { this.amountPaid = amountPaid; }
    public BigDecimal getChange() { return change; }
}
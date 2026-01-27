package com.upb.agripos.model;

import java.math.BigDecimal;

public class EWalletPayment implements PaymentMethod {
    private String provider; // GoPay, OVO, Dana, dll
    private String accountNumber;
    
    public EWalletPayment() {}
    
    public EWalletPayment(String provider, String accountNumber) {
        this.provider = provider;
        this.accountNumber = accountNumber;
    }
    
    @Override
    public boolean processPayment(BigDecimal amount) throws Exception {
        if (provider == null || provider.isEmpty()) {
            throw new Exception("Provider e-wallet harus dipilih!");
        }
        if (accountNumber == null || accountNumber.isEmpty()) {
            throw new Exception("Nomor akun e-wallet harus diisi!");
        }
        System.out.println("Pembayaran " + provider + " berhasil untuk " + accountNumber + ": Rp " + amount);
        return true;
    }
    
    @Override
    public String getMethodName() {
        return "EWALLET";
    }
    
    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
}
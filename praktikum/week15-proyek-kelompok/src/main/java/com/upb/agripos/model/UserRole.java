package com.upb.agripos.model;

public enum UserRole {
    CASHIER("Kasir"),
    ADMIN("Administrator");
    
    private String displayName;
    
    UserRole(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
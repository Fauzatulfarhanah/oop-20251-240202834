package com.upb.agripos.controller;

import com.upb.agripos.model.User;
import com.upb.agripos.model.UserRole;
import com.upb.agripos.service.AuthService;

public class AuthController {
    private AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    public User login(String username, String password) throws Exception {
        return authService.authenticate(username, password);
    }
    
    public void logout() {
        authService.logout();
    }
    
    public User getCurrentUser() {
        return authService.getCurrentUser();
    }
    
    public boolean isAdmin() {
        User user = getCurrentUser();
        return user != null && user.getRole() == UserRole.ADMIN;
    }
    
    public boolean isCashier() {
        User user = getCurrentUser();
        return user != null && user.getRole() == UserRole.CASHIER;
    }
}
package com.upb.agripos.service;

import com.upb.agripos.dao.UserDAO;
import com.upb.agripos.exception.AuthenticationException;
import com.upb.agripos.model.User;
import com.upb.agripos.model.UserRole;

public class AuthService {
    private UserDAO userDAO;
    private User currentUser;
    
    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    
    public User authenticate(String username, String password) throws Exception {
        if (username == null || username.trim().isEmpty()) {
            throw new AuthenticationException("Username tidak boleh kosong");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new AuthenticationException("Password tidak boleh kosong");
        }
        boolean valid = userDAO.validateCredentials(username, password);
        if (!valid) {
            throw new AuthenticationException("Username atau password salah");
        }
        currentUser = userDAO.findByUsername(username);
        return currentUser;
    }
    
    public boolean validateRole(User user, UserRole requiredRole) {
        return user != null && user.getRole() == requiredRole;
    }
    
    public void logout() { currentUser = null; }
    public User getCurrentUser() { return currentUser; }
    public boolean isLoggedIn() { return currentUser != null; }
}
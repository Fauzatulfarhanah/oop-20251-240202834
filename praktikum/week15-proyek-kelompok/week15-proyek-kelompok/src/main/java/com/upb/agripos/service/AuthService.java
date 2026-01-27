package com.upb.agripos.service;

import com.upb.agripos.dao.UserDAO;
import com.upb.agripos.model.User;

public class AuthService {
    private UserDAO userDAO;
    private User currentUser;
    
    public AuthService(UserDAO userDAO) { this.userDAO = userDAO; }
    
    public User authenticate(String username, String password) throws Exception {
        if(userDAO.validateCredentials(username, password)) {
            currentUser = userDAO.findByUsername(username);
            return currentUser;
        }
        throw new Exception("Login Gagal");
    }
    public User getCurrentUser() { return currentUser; }
}
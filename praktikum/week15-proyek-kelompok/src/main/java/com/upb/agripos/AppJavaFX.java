package com.upb.agripos;

import com.upb.agripos.controller.AuthController;
import com.upb.agripos.controller.PosController;
import com.upb.agripos.controller.ReportController;
import com.upb.agripos.dao.JdbcProductDAO;
import com.upb.agripos.dao.JdbcTransactionDAO;
import com.upb.agripos.dao.JdbcUserDAO;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.TransactionDAO;
import com.upb.agripos.dao.UserDAO;
import com.upb.agripos.service.AuthService;
import com.upb.agripos.service.CartService;
import com.upb.agripos.service.PaymentService;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.service.ReportService;
import com.upb.agripos.service.TransactionService;
import com.upb.agripos.view.LoginView;

import javafx.application.Application;

public class AppJavaFX {
    private static PosController posController;
    private static AuthController authController;
    private static ReportController reportController;

    public static void main(String[] args) {
        try {
            // 1. Initialize DAOs (Anggota 1)
            ProductDAO productDAO = new JdbcProductDAO();
            UserDAO userDAO = new JdbcUserDAO();
            TransactionDAO transactionDAO = new JdbcTransactionDAO();
            
            // 2. Initialize Services (Anggota 2)
            ProductService productService = new ProductService(productDAO);
            CartService cartService = new CartService();
            AuthService authService = new AuthService(userDAO);
            TransactionService transactionService = new TransactionService(transactionDAO, productService);
            PaymentService paymentService = new PaymentService();
            ReportService reportService = new ReportService(transactionDAO);
            
            // 3. Initialize Controllers (Anggota 3)
            posController = new PosController(productService, cartService, transactionService, paymentService, authService);
            authController = new AuthController(authService);
            reportController = new ReportController(reportService);
            
            // 4. Launch UI
            Application.launch(LoginView.class, args);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static PosController getController() { return posController; }
    public static AuthController getAuthController() { return authController; }
    public static ReportController getReportController() { return reportController; }
}
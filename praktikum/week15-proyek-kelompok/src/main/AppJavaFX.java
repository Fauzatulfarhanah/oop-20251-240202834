package com.upb.agripos;

import com.upb.agripos.controller.*;
import com.upb.agripos.dao.*;
import com.upb.agripos.service.*;
import com.upb.agripos.view.LoginView;

import javafx.application.Application;
import javafx.stage.Stage;

public class AppJavaFX extends Application {
    private static PosController posController;
    private static AuthController authController;
    private static ReportController reportController;

    public static void main(String[] args) {
        System.out.println("=== AGRI-POS WEEK 15 - PROYEK KELOMPOK ===");
        System.out.println("=== ANGGOTA 3: CONTROLLER & VIEW LAYER ===");
        
        try {
            // Initialize DAOs (akan dibuat oleh Anggota 1)
            System.out.println("1. Initializing DAOs...");
            ProductDAO productDAO = new JdbcProductDAO();
            UserDAO userDAO = new JdbcUserDAO();
            TransactionDAO transactionDAO = new JdbcTransactionDAO();
            
            // Initialize Services (akan dibuat oleh Anggota 2)
            System.out.println("2. Initializing Services...");
            ProductService productService = new ProductService(productDAO);
            CartService cartService = new CartService();
            AuthService authService = new AuthService(userDAO);
            TransactionService transactionService = new TransactionService(
                transactionDAO, productService
            );
            PaymentService paymentService = new PaymentService();
            ReportService reportService = new ReportService(transactionDAO);
            
            // Initialize Controllers
            System.out.println("3. Initializing Controllers...");
            posController = new PosController(
                productService, cartService, transactionService, 
                paymentService, authService
            );
            authController = new AuthController(authService);
            reportController = new ReportController(reportService);
            
            System.out.println("4. Launching application...");
            launch(args);
            
        } catch (Exception e) {
            System.err.println("Error initializing application:");
            e.printStackTrace();
        }
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Create and configure LoginView
        LoginView loginView = new LoginView();
        loginView.setAuthController(authController);
        
        // Start the login view
        loginView.start(primaryStage);
        
        System.out.println("5. Application started successfully!");
    }
    
    public static PosController getController() { 
        return posController; 
    }
    
    public static AuthController getAuthController() { 
        return authController; 
    }
    
    public static ReportController getReportController() {
        return reportController;
    }
}

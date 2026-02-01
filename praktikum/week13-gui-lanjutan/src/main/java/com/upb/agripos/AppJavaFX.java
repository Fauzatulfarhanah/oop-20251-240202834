package com.upb.agripos;

import java.sql.Connection;
import java.sql.DriverManager;
import com.upb.agripos.controller.ProductController;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.ProductDAOImpl;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.view.ProductTableView;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class AppJavaFX extends Application {
    
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/agripos";
    private static final String DB_USER = "postgres";
    private static final String DB_PASS = "farhanah";

    private Connection connection;

    @Override
    public void start(Stage primaryStage) {
        System.out.println("=================================");
        System.out.println("WEEK 13 - TABLEVIEW VERSION");
        System.out.println("Loading ProductTableView...");
        System.out.println("=================================");
        
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("Database Connected");

            ProductDAO productDAO = new ProductDAOImpl(connection);
            ProductService productService = new ProductService(productDAO);
            ProductController productController = new ProductController(productService);

            System.out.println("Creating ProductTableView instance...");
            ProductTableView view = new ProductTableView(productController);
            
            System.out.println("Starting ProductTableView...");
            view.start(primaryStage);
            
            System.out.println("ProductTableView loaded successfully!");

            primaryStage.setOnCloseRequest(event -> closeConnection());

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            String msg = "Database Error: " + e.getMessage();
            showAlert("Error", msg);
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        closeConnection();
    }

    private void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database Closed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("Week 13 - Fauzatul Farhanah (240202834)");
        System.out.println("GUI Lanjutan - TableView + Hapus Produk");
        System.out.println("=========================================");
        launch(args);
    }
}
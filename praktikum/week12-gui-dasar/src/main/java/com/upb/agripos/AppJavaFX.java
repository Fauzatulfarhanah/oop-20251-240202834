package com.upb.agripos;

import java.sql.Connection;
import java.sql.DriverManager;

import com.upb.agripos.controller.ProductController;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.ProductDAOImpl;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.view.ProductFormView;

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
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("Database Connected Successfully");

            ProductDAO productDAO = new ProductDAOImpl(connection);
            ProductService productService = new ProductService(productDAO);
            ProductController productController = new ProductController(productService);

            ProductFormView view = new ProductFormView(productController);
            view.start(primaryStage);

            primaryStage.setOnCloseRequest(event -> closeConnection());

        } catch (Exception e) {
            String msg = "Database Error: " + e.getMessage();
            showErrorAlert("Connection Failed", msg);
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
                System.out.println("Database Connection Closed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        System.out.println("Agri-POS JavaFX - Fauzatul Farhanah (240202834)");
        launch(args);
    }
}
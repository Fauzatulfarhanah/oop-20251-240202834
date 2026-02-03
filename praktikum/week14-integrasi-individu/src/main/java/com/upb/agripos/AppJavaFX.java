package com.upb.agripos;

import com.upb.agripos.controller.PosController;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.JdbcProductDAO;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.service.CartService;
import com.upb.agripos.view.PosView;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;

public class AppJavaFX extends Application {
    private Connection connection;

    @Override
    public void start(Stage primaryStage) {
        System.out.println("=======================================");
        System.out.println("Hello World, I am Fauzatul Farhanah-240202834");
        System.out.println("Week 14 - Integrasi Individu");
        System.out.println("Agri-POS: OOP + Database + GUI");
        System.out.println("=======================================\n");

        try {
            String url = "jdbc:postgresql://localhost:5432/agripos";
            String user = "postgres";
            String pass = "farhanah";
            
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Database Connected");

            ProductDAO productDAO = new JdbcProductDAO(connection);
            ProductService productService = new ProductService(productDAO);
            CartService cartService = new CartService();
            
            PosController posController = new PosController(productService, cartService);

            PosView posView = new PosView(posController);
            posView.start(primaryStage);

            primaryStage.setOnCloseRequest(event -> cleanup());

        } catch (Exception e) {
            showAlert("Error", "Gagal: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        cleanup();
    }

    private void cleanup() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("\n=======================================");
                System.out.println("Database Closed");
                System.out.println("Credit by: 240202834 - Fauzatul Farhanah");
                System.out.println("=======================================");
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
        launch(args);
    }
}

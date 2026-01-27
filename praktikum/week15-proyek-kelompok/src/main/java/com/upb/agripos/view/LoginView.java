package com.upb.agripos.view;

import com.upb.agripos.controller.AuthController;
import com.upb.agripos.model.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginView extends Application {
    private AuthController authController;
    private TextField txtUsername;
    private PasswordField txtPassword;
    private Stage primaryStage;
    
    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.authController = com.upb.agripos.AppJavaFX.getAuthController();
        
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #e8f5e9;");
        
        Label title = new Label("AGRI-POS LOGIN");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");
        
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(15);
        form.setAlignment(Pos.CENTER);
        
        txtUsername = new TextField();
        txtUsername.setPromptText("Username");
        txtPassword = new PasswordField();
        txtPassword.setPromptText("Password");
        
        form.add(new Label("Username:"), 0, 0);
        form.add(txtUsername, 1, 0);
        form.add(new Label("Password:"), 0, 1);
        form.add(txtPassword, 1, 1);
        
        Button btnLogin = new Button("LOGIN");
        btnLogin.setPrefWidth(250);
        btnLogin.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white; -fx-font-weight: bold;");
        btnLogin.setOnAction(e -> handleLogin());
        
        Label info = new Label("Demo Login:\nAdmin: admin/admin123\nKasir: kasir1/kasir123");
        info.setStyle("-fx-text-fill: #666; -fx-font-size: 11px;");
        
        root.getChildren().addAll(title, form, btnLogin, info);
        stage.setScene(new Scene(root, 500, 400));
        stage.setTitle("Agri-POS - Login");
        stage.show();
    }
    
    private void handleLogin() {
        try {
            User user = authController.login(txtUsername.getText(), txtPassword.getText());
            new Alert(Alert.AlertType.INFORMATION, "Selamat datang, " + user.getName()).showAndWait();
            openMainWindow();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
        }
    }
    
    private void openMainWindow() throws Exception {
        new PosView().start(new Stage());
        primaryStage.close();
    }
}
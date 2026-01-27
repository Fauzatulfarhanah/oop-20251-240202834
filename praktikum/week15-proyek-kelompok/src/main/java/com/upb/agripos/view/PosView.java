package com.upb.agripos.view;

import java.math.BigDecimal;
import java.util.List;

import com.upb.agripos.controller.AuthController;
import com.upb.agripos.controller.PosController;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.CashPayment;
import com.upb.agripos.model.EWalletPayment;
import com.upb.agripos.model.PaymentMethod;
import com.upb.agripos.model.Product;
import com.upb.agripos.model.Receipt;
import com.upb.agripos.model.Transaction;
import com.upb.agripos.model.User;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PosView extends Application {
    private PosController controller;
    private AuthController authController;
    
    private TableView<Product> productTable;
    private ObservableList<Product> productData;
    
    private TableView<CartItem> cartTable;
    private ObservableList<CartItem> cartData;
    
    private TextField txtCode, txtName, txtCategory, txtPrice, txtStock, txtQuantity;
    private Label lblTotal, lblItemCount, lblUserInfo;
    
    private ComboBox<String> cmbPaymentMethod;
    private TextField txtCashAmount, txtEWalletProvider, txtEWalletAccount;
    private VBox paymentDetailsBox;

    @Override
    public void start(Stage primaryStage) {
        this.controller = com.upb.agripos.AppJavaFX.getController();
        this.authController = com.upb.agripos.AppJavaFX.getAuthController();

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(15));
        
        // Header
        VBox header = createHeader();
        root.setTop(header);
        
        // Center: Split Pane
        SplitPane splitPane = new SplitPane();
        
        if (authController.isAdmin()) {
            splitPane.getItems().addAll(createProductPanel(), createCartPanel());
        } else {
            splitPane.getItems().add(createCartPanel());
        }
        
        splitPane.setDividerPositions(0.55);
        root.setCenter(splitPane);
        
        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setTitle("Agri-POS System - " + 
            authController.getCurrentUser().getName());
        primaryStage.setScene(scene);
        primaryStage.show();
        
        if (authController.isAdmin()) {
            loadProducts();
        }
    }
    
    private VBox createHeader() {
        VBox header = new VBox(10);
        header.setPadding(new Insets(0, 0, 15, 0));
        
        Label title = new Label("AGRI-POS - Sistem Kasir Pertanian");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: #2e7d32;");
        
        User user = authController.getCurrentUser();
        lblUserInfo = new Label("👤 " + user.getName() + " | " + 
            user.getRole().getDisplayName());
        lblUserInfo.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");
        
        Button btnLogout = new Button("Logout");
        btnLogout.setOnAction(e -> handleLogout());
        
        HBox titleBox = new HBox(20);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        titleBox.getChildren().addAll(title, new Region(), lblUserInfo, btnLogout);
        HBox.setHgrow(titleBox.getChildren().get(1), Priority.ALWAYS);
        
        header.getChildren().add(titleBox);
        return header;
    }

    private VBox createProductPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        
        Label lblTitle = new Label("📦 Manajemen Produk (Admin Only)");
        lblTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        productTable = new TableView<>();
        productData = FXCollections.observableArrayList();
        productTable.setItems(productData);
        
        TableColumn<Product, String> colCode = new TableColumn<>("Kode");
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colCode.setPrefWidth(80);
        
        TableColumn<Product, String> colName = new TableColumn<>("Nama Produk");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setPrefWidth(150);
        
        TableColumn<Product, String> colCategory = new TableColumn<>("Kategori");
        colCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colCategory.setPrefWidth(100);
        
        TableColumn<Product, BigDecimal> colPrice = new TableColumn<>("Harga");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colPrice.setPrefWidth(100);
        
        TableColumn<Product, Integer> colStock = new TableColumn<>("Stok");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colStock.setPrefWidth(60);
        
        productTable.getColumns().addAll(colCode, colName, colCategory, colPrice, colStock);

        // Form Input
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        
        txtCode = new TextField();
        txtCode.setPromptText("Kode");
        txtName = new TextField();
        txtName.setPromptText("Nama");
        txtCategory = new TextField();
        txtCategory.setPromptText("Kategori");
        txtPrice = new TextField();
        txtPrice.setPromptText("Harga");
        txtStock = new TextField();
        txtStock.setPromptText("Stok");

        form.add(new Label("Kode:"), 0, 0);
        form.add(txtCode, 1, 0);
        form.add(new Label("Kategori:"), 2, 0);
        form.add(txtCategory, 3, 0);
        form.add(new Label("Nama:"), 0, 1);
        form.add(txtName, 1, 1);
        form.add(new Label("Harga:"), 2, 1);
        form.add(txtPrice, 3, 1);
        form.add(new Label("Stok:"), 0, 2);
        form.add(txtStock, 1, 2);
        
        HBox buttons = new HBox(10);
        Button btnAdd = new Button("Tambah");
        Button btnDelete = new Button("Hapus");
        Button btnRefresh = new Button("Refresh");
        
        btnAdd.setOnAction(e -> handleAddProduct());
        btnDelete.setOnAction(e -> handleDeleteProduct());
        btnRefresh.setOnAction(e -> loadProducts());
        
        buttons.getChildren().addAll(btnAdd, btnDelete, btnRefresh);
        
        panel.getChildren().addAll(lblTitle, productTable, form, buttons);
        VBox.setVgrow(productTable, Priority.ALWAYS);
        return panel;
    }

    private VBox createCartPanel() {
        VBox panel = new VBox(10);
        panel.setPadding(new Insets(10));
        
        Label lblTitle = new Label("🛒 Keranjang Belanja");
        lblTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        // Product Selection (untuk Kasir)
        if (!authController.isAdmin()) {
            HBox productSelect = new HBox(10);
            Button btnLoadProducts = new Button("Lihat Daftar Produk");
            btnLoadProducts.setOnAction(e -> showProductSelection());
            productSelect.getChildren().add(btnLoadProducts);
            panel.getChildren().add(productSelect);
        }
        
        cartTable = new TableView<>();
        cartData = FXCollections.observableArrayList();
        cartTable.setItems(cartData);
        
        TableColumn<CartItem, String> colProduct = new TableColumn<>("Produk");
        colProduct.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleStringProperty(
                data.getValue().getProduct().getName()
            ));
        
        TableColumn<CartItem, Integer> colQty = new TableColumn<>("Qty");
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        
        TableColumn<CartItem, BigDecimal> colPrice = new TableColumn<>("Harga");
        colPrice.setCellValueFactory(data -> 
            new javafx.beans.property.SimpleObjectProperty<>(
                data.getValue().getProduct().getPrice()
            ));
        
        TableColumn<CartItem, BigDecimal> colSub = new TableColumn<>("Subtotal");
        colSub.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        
        cartTable.getColumns().addAll(colProduct, colQty, colPrice, colSub);

        // Quantity input (hanya untuk Admin yang punya tabel produk)
        if (authController.isAdmin()) {
            HBox qtyBox = new HBox(10);
            qtyBox.setAlignment(Pos.CENTER_LEFT);
            txtQuantity = new TextField("1");
            txtQuantity.setPrefWidth(60);
            Button btnAddToCart = new Button("➕ Tambah ke Keranjang");
            btnAddToCart.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white;");
            btnAddToCart.setOnAction(e -> handleAddToCart());
            qtyBox.getChildren().addAll(new Label("Jumlah:"), txtQuantity, btnAddToCart);
            panel.getChildren().add(qtyBox);
        }
        
        // Payment Method Selection
        VBox paymentBox = createPaymentMethodBox();
        
        // Summary
        VBox summary = new VBox(10);
        summary.setPadding(new Insets(10));
        summary.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; -fx-background-color: #f5f5f5;");
        
        lblItemCount = new Label("Item: 0");
        lblItemCount.setStyle("-fx-font-size: 14px;");
        
        lblTotal = new Label("TOTAL: Rp 0");
        lblTotal.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #c62828;");
        
        Button btnClear = new Button("Kosongkan Keranjang");
        btnClear.setOnAction(e -> handleClearCart());
        
        Button btnCheckout = new Button("💳 PROSES CHECKOUT");
        btnCheckout.setMaxWidth(Double.MAX_VALUE);
        btnCheckout.setPrefHeight(50);
        btnCheckout.setStyle("-fx-background-color: #1565c0; -fx-text-fill: white; " +
                           "-fx-font-weight: bold; -fx-font-size: 16px;");
        btnCheckout.setOnAction(e -> handleCheckout());
        
        summary.getChildren().addAll(lblItemCount, lblTotal, btnClear, btnCheckout);
        
        panel.getChildren().addAll(lblTitle, cartTable, paymentBox, summary);
        VBox.setVgrow(cartTable, Priority.ALWAYS);
        return panel;
    }
    
    private VBox createPaymentMethodBox() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-border-color: #aaa; -fx-border-width: 1;");
        
        Label lblPayment = new Label("Metode Pembayaran:");
        lblPayment.setStyle("-fx-font-weight: bold;");
        
        cmbPaymentMethod = new ComboBox<>();
        cmbPaymentMethod.getItems().addAll("Tunai (Cash)", "E-Wallet");
        cmbPaymentMethod.setValue("Tunai (Cash)");
        cmbPaymentMethod.setOnAction(e -> updatePaymentFields());
        
        paymentDetailsBox = new VBox(5);
        
        // Cash fields
        txtCashAmount = new TextField();
        txtCashAmount.setPromptText("Jumlah uang dibayar");
        
        // E-Wallet fields
        txtEWalletProvider = new TextField();
        txtEWalletProvider.setPromptText("Provider (GoPay/OVO/Dana)");
        txtEWalletAccount = new TextField();
        txtEWalletAccount.setPromptText("Nomor Akun");
        
        updatePaymentFields();
        
        box.getChildren().addAll(lblPayment, cmbPaymentMethod, paymentDetailsBox);
        return box;
    }
    
    private void updatePaymentFields() {
        paymentDetailsBox.getChildren().clear();
        
        String method = cmbPaymentMethod.getValue();
        if (method.startsWith("Tunai")) {
            paymentDetailsBox.getChildren().add(new Label("Uang Dibayar:"));
            paymentDetailsBox.getChildren().add(txtCashAmount);
        } else {
            paymentDetailsBox.getChildren().addAll(
                new Label("Provider E-Wallet:"),
                txtEWalletProvider,
                new Label("Nomor Akun:"),
                txtEWalletAccount
            );
        }
    }

    // ============= EVENT HANDLERS =============
    
    private void loadProducts() {
        try {
            productData.setAll(controller.loadProducts());
        } catch (Exception e) {
            showError("Error Load", e.getMessage());
        }
    }

    private void handleAddProduct() {
        try {
            String code = txtCode.getText();
            String name = txtName.getText();
            String category = txtCategory.getText().isEmpty() ? "Umum" : txtCategory.getText();
            BigDecimal price = new BigDecimal(txtPrice.getText());
            int stock = Integer.parseInt(txtStock.getText());
            
            controller.addProduct(code, name, category, price, stock);
            showInfo("Sukses", "Produk berhasil ditambahkan!");
            clearProductForm();
            loadProducts();
        } catch (Exception e) {
            showError("Gagal Tambah Produk", e.getMessage());
        }
    }

    private void handleDeleteProduct() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Pilih Produk", "Pilih produk yang akan dihapus!");
            return;
        }
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, 
            "Hapus produk " + selected.getName() + "?");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            try {
                controller.deleteProduct(selected.getCode());
                loadProducts();
            } catch (Exception e) {
                showError("Gagal Hapus", e.getMessage());
            }
        }
    }

    private void handleAddToCart() {
        Product selected = productTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showWarning("Pilih Produk", "Pilih produk dari tabel!");
            return;
        }
        
        try {
            int qty = Integer.parseInt(txtQuantity.getText());
            controller.addToCart(selected, qty);
            updateCartDisplay();
            showInfo("Berhasil", "Produk ditambahkan ke keranjang!");
        } catch (Exception e) {
            showError("Gagal", e.getMessage());
        }
    }
    
    private void handleClearCart() {
        if (cartData.isEmpty()) return;
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Kosongkan keranjang?");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            controller.clearCart();
            updateCartDisplay();
        }
    }

    private void handleCheckout() {
        if (cartData.isEmpty()) {
            showWarning("Keranjang Kosong", "Tambahkan produk terlebih dahulu!");
            return;
        }
        
        try {
            // Buat payment method berdasarkan pilihan
            PaymentMethod paymentMethod = createPaymentMethod();
            
            // Proses transaksi
            Transaction transaction = controller.checkout(paymentMethod);
            
            // Generate & tampilkan struk
            Receipt receipt = controller.generateReceipt(transaction);
            showReceipt(receipt);
            
            updateCartDisplay();
            if (authController.isAdmin()) {
                loadProducts(); // Refresh stok
            }
            
        } catch (Exception e) {
            showError("Checkout Gagal", e.getMessage());
        }
    }
    
    private PaymentMethod createPaymentMethod() throws Exception {
        String method = cmbPaymentMethod.getValue();
        
        if (method.startsWith("Tunai")) {
            String amountStr = txtCashAmount.getText();
            if (amountStr.isEmpty()) {
                throw new Exception("Masukkan jumlah uang dibayar!");
            }
            BigDecimal amount = new BigDecimal(amountStr);
            return new CashPayment(amount);
        } else {
            String provider = txtEWalletProvider.getText();
            String account = txtEWalletAccount.getText();
            return new EWalletPayment(provider, account);
        }
    }
    
    private void showReceipt(Receipt receipt) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Struk Pembayaran");
        alert.setHeaderText("Transaksi Berhasil!");
        
        TextArea textArea = new TextArea(receipt.formatReceipt());
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefRowCount(20);
        textArea.setStyle("-fx-font-family: monospace;");
        
        alert.getDialogPane().setContent(textArea);
        alert.getDialogPane().setPrefWidth(500);
        alert.showAndWait();
    }
    
    private void showProductSelection() {
        // Simplified: show dialog with product list for cashier
        try {
            List<Product> products = controller.loadProducts();
            
            Dialog<Product> dialog = new Dialog<>();
            dialog.setTitle("Pilih Produk");
            
            ListView<Product> listView = new ListView<>();
            listView.getItems().addAll(products);
            
            dialog.getDialogPane().setContent(listView);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            
            dialog.showAndWait().ifPresent(product -> {
                TextInputDialog qtyDialog = new TextInputDialog("1");
                qtyDialog.setTitle("Jumlah");
                qtyDialog.setHeaderText("Masukkan jumlah untuk " + product.getName());
                
                qtyDialog.showAndWait().ifPresent(qtyStr -> {
                    try {
                        int qty = Integer.parseInt(qtyStr);
                        controller.addToCart(product, qty);
                        updateCartDisplay();
                    } catch (Exception e) {
                        showError("Gagal", e.getMessage());
                    }
                });
            });
            
        } catch (Exception e) {
            showError("Error", e.getMessage());
        }
    }
    
    private void handleLogout() {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Logout dari sistem?");
        if (confirm.showAndWait().get() == ButtonType.OK) {
            authController.logout();
            
            try {
                LoginView loginView = new LoginView();
                Stage loginStage = new Stage();
                loginView.start(loginStage);
                
                Stage currentStage = (Stage) lblUserInfo.getScene().getWindow();
                currentStage.close();
            } catch (Exception e) {
                showError("Error", "Gagal logout: " + e.getMessage());
            }
        }
    }

    private void updateCartDisplay() {
        cartData.setAll(controller.getCartItems());
        lblItemCount.setText("Item: " + cartData.size());
        lblTotal.setText(String.format("TOTAL: Rp %,.0f", controller.getCartTotal()));
    }

    private void clearProductForm() {
        txtCode.clear();
        txtName.clear();
        txtCategory.clear();
        txtPrice.clear();
        txtStock.clear();
    }

    private void showError(String title, String msg) {
        new Alert(Alert.AlertType.ERROR, msg).showAndWait();
    }

    private void showInfo(String title, String msg) {
        new Alert(Alert.AlertType.INFORMATION, msg).showAndWait();
    }

    private void showWarning(String title, String msg) {
        new Alert(Alert.AlertType.WARNING, msg).showAndWait();
    }
}
package com.upb.agripos.view;

import com.upb.agripos.controller.ProductController;
import com.upb.agripos.model.Product;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * ProductFormView - JavaFX View (MVC View)
 * GUI untuk form input produk dan menampilkan daftar produk
 * Sesuai dengan spesifikasi Week 12
 */
public class ProductFormView {
    private final ProductController controller;
    
    // GUI Components
    private TextField txtCode;
    private TextField txtName;
    private TextField txtPrice;
    private TextField txtStock;
    private Button btnAdd;
    private Button btnRefresh;
    private ListView<String> listView;

    public ProductFormView(ProductController controller) {
        this.controller = controller;
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Agri-POS - Form Input Produk");

        // ===== BAGIAN FORM INPUT =====
        GridPane formGrid = new GridPane();
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(20, 20, 20, 20));

        // Label dan TextField untuk Kode Produk
        Label lblCode = new Label("Kode Produk:");
        txtCode = new TextField();
        txtCode.setPromptText("Contoh: P001");
        formGrid.add(lblCode, 0, 0);
        formGrid.add(txtCode, 1, 0);

        // Label dan TextField untuk Nama Produk
        Label lblName = new Label("Nama Produk:");
        txtName = new TextField();
        txtName.setPromptText("Contoh: Pupuk Organik");
        formGrid.add(lblName, 0, 1);
        formGrid.add(txtName, 1, 1);

        // Label dan TextField untuk Harga
        Label lblPrice = new Label("Harga:");
        txtPrice = new TextField();
        txtPrice.setPromptText("Contoh: 50000");
        formGrid.add(lblPrice, 0, 2);
        formGrid.add(txtPrice, 1, 2);

        // Label dan TextField untuk Stok
        Label lblStock = new Label("Stok:");
        txtStock = new TextField();
        txtStock.setPromptText("Contoh: 100");
        formGrid.add(lblStock, 0, 3);
        formGrid.add(txtStock, 1, 3);

        // ===== TOMBOL AKSI =====
        btnAdd = new Button("Tambah Produk");
        btnAdd.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        btnAdd.setPrefWidth(120);
        
        btnRefresh = new Button("Refresh List");
        btnRefresh.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnRefresh.setPrefWidth(120);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(btnAdd, btnRefresh);
        formGrid.add(buttonBox, 0, 4, 2, 1);

        // ===== AREA TAMPILAN DATA =====
        Label lblList = new Label("Daftar Produk:");
        lblList.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        
        listView = new ListView<>();
        listView.setPrefHeight(200);
        listView.setStyle("-fx-font-family: 'Courier New';");

        // ===== LAYOUT UTAMA =====
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getChildren().addAll(
            createTitle(),
            formGrid,
            new Separator(),
            lblList,
            listView
        );

        // ===== EVENT HANDLERS =====
        setupEventHandlers();

        // ===== SCENE DAN STAGE =====
        Scene scene = new Scene(mainLayout, 500, 550);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Load data awal
        refreshProductList();
    }

    /**
     * Membuat title header aplikasi
     */
    private Label createTitle() {
        Label title = new Label("FORM INPUT PRODUK AGRI-POS");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2E7D32;");
        title.setAlignment(Pos.CENTER);
        title.setMaxWidth(Double.MAX_VALUE);
        return title;
    }

    /**
     * Setup Event Handlers untuk tombol-tombol
     * Implementasi Event-Driven Programming
     */
    private void setupEventHandlers() {
        // Event Handler untuk Tombol Tambah Produk
        // Mengikuti Sequence Diagram: View → Controller → Service → DAO → DB
        btnAdd.setOnAction(event -> {
            // Ambil input dari TextField
            String code = txtCode.getText();
            String name = txtName.getText();
            String price = txtPrice.getText();
            String stock = txtStock.getText();

            // Panggil controller untuk handle logic
            controller.add(code, name, price, stock);

            // Clear form setelah tambah
            clearForm();

            // Refresh list untuk update tampilan
            refreshProductList();
        });

        // Event Handler untuk Tombol Refresh
        btnRefresh.setOnAction(event -> {
            refreshProductList();
        });
    }

    /**
     * Refresh daftar produk dari database
     * Update tampilan UI setelah operasi CRUD
     */
    private void refreshProductList() {
        listView.getItems().clear();
        
        java.util.List<Product> products = controller.getAllProducts();
        
        if (products.isEmpty()) {
            listView.getItems().add("-- Belum ada data produk --");
        } else {
            for (Product p : products) {
                listView.getItems().add(p.toString());
            }
        }
    }

    /**
     * Clear semua field input
     */
    private void clearForm() {
        txtCode.clear();
        txtName.clear();
        txtPrice.clear();
        txtStock.clear();
        txtCode.requestFocus();
    }
}
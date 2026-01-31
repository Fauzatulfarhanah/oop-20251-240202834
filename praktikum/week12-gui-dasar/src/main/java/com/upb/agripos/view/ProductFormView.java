package com.upb.agripos.view;

import com.upb.agripos.controller.ProductController;
import com.upb.agripos.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProductFormView {
    private final ProductController controller;
    
    private TextField txtCode;
    private TextField txtName;
    private TextField txtPrice;
    private TextField txtStock;
    private Button btnAdd;
    private Button btnRefresh;
    private TableView<Product> tableView;
    private ObservableList<Product> productList;

    public ProductFormView(ProductController controller) {
        this.controller = controller;
        this.productList = FXCollections.observableArrayList();
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Agri-POS - Manajemen Produk");

        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));
        
        Label title = createTitle();
        GridPane formGrid = createFormGrid();
        HBox buttonBox = createButtonBox();
        Label lblList = new Label("Daftar Produk:");
        lblList.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        tableView = createTableView();

        mainLayout.getChildren().addAll(
            title,
            formGrid,
            buttonBox,
            new Separator(),
            lblList,
            tableView
        );

        setupEventHandlers();
        refreshProductList();

        Scene scene = new Scene(mainLayout, 700, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Label createTitle() {
        Label title = new Label("FORM INPUT PRODUK AGRI-POS");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2E7D32;");
        title.setAlignment(Pos.CENTER);
        title.setMaxWidth(Double.MAX_VALUE);
        return title;
    }

    private GridPane createFormGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 20, 10, 20));

        Label lblCode = new Label("Kode Produk:");
        txtCode = new TextField();
        txtCode.setPromptText("Contoh: P001");
        grid.add(lblCode, 0, 0);
        grid.add(txtCode, 1, 0);

        Label lblName = new Label("Nama Produk:");
        txtName = new TextField();
        txtName.setPromptText("Contoh: Pupuk Organik");
        grid.add(lblName, 0, 1);
        grid.add(txtName, 1, 1);

        Label lblPrice = new Label("Harga:");
        txtPrice = new TextField();
        txtPrice.setPromptText("Contoh: 50000");
        grid.add(lblPrice, 0, 2);
        grid.add(txtPrice, 1, 2);

        Label lblStock = new Label("Stok:");
        txtStock = new TextField();
        txtStock.setPromptText("Contoh: 100");
        grid.add(lblStock, 0, 3);
        grid.add(txtStock, 1, 3);

        return grid;
    }

    private HBox createButtonBox() {
        btnAdd = new Button("Tambah Produk");
        btnAdd.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        btnAdd.setPrefWidth(120);
        
        btnRefresh = new Button("Refresh List");
        btnRefresh.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnRefresh.setPrefWidth(120);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(btnAdd, btnRefresh);
        return buttonBox;
    }

    private TableView<Product> createTableView() {
        TableView<Product> table = new TableView<>();
        table.setPrefHeight(300);

        TableColumn<Product, String> colCode = new TableColumn<>("Kode");
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colCode.setPrefWidth(100);

        TableColumn<Product, String> colName = new TableColumn<>("Nama Produk");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setPrefWidth(250);

        TableColumn<Product, Double> colPrice = new TableColumn<>("Harga");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colPrice.setPrefWidth(150);

        TableColumn<Product, Integer> colStock = new TableColumn<>("Stok");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colStock.setPrefWidth(100);

        table.getColumns().addAll(colCode, colName, colPrice, colStock);
        table.setItems(productList);

        return table;
    }

    private void setupEventHandlers() {
        btnAdd.setOnAction(event -> {
            String code = txtCode.getText();
            String name = txtName.getText();
            String price = txtPrice.getText();
            String stock = txtStock.getText();

            controller.add(code, name, price, stock);
            clearForm();
            refreshProductList();
        });

        btnRefresh.setOnAction(event -> {
            refreshProductList();
        });
    }

    private void refreshProductList() {
        productList.clear();
        productList.addAll(controller.getAllProducts());
    }

    private void clearForm() {
        txtCode.clear();
        txtName.clear();
        txtPrice.clear();
        txtStock.clear();
        txtCode.requestFocus();
    }
}
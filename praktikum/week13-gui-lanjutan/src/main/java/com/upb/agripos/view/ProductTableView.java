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

public class ProductTableView {
    private final ProductController controller;
    
    private TextField txtCode;
    private TextField txtName;
    private TextField txtPrice;
    private TextField txtStock;
    private Button btnAdd;
    private Button btnDelete;
    private Button btnRefresh;
    private TableView<Product> tableView;
    private ObservableList<Product> productList;

    public ProductTableView(ProductController controller) {
        this.controller = controller;
        this.productList = FXCollections.observableArrayList();
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Agri-POS - Kelola Produk");

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
        loadData();

        Scene scene = new Scene(mainLayout, 700, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Label createTitle() {
        Label title = new Label("KELOLA PRODUK AGRI-POS");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        title.setAlignment(Pos.CENTER);
        title.setMaxWidth(Double.MAX_VALUE);
        return title;
    }

    private GridPane createFormGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10));

        Label lblCode = new Label("Kode:");
        txtCode = new TextField();
        txtCode.setPromptText("P001");
        grid.add(lblCode, 0, 0);
        grid.add(txtCode, 1, 0);

        Label lblName = new Label("Nama:");
        txtName = new TextField();
        txtName.setPromptText("Nama Produk");
        grid.add(lblName, 0, 1);
        grid.add(txtName, 1, 1);

        Label lblPrice = new Label("Harga:");
        txtPrice = new TextField();
        txtPrice.setPromptText("50000");
        grid.add(lblPrice, 0, 2);
        grid.add(txtPrice, 1, 2);

        Label lblStock = new Label("Stok:");
        txtStock = new TextField();
        txtStock.setPromptText("100");
        grid.add(lblStock, 0, 3);
        grid.add(txtStock, 1, 3);

        return grid;
    }

    private HBox createButtonBox() {
        btnAdd = new Button("Tambah Produk");
        btnAdd.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnAdd.setPrefWidth(120);

        btnDelete = new Button("Hapus Produk");
        btnDelete.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        btnDelete.setPrefWidth(120);

        btnRefresh = new Button("Refresh");
        btnRefresh.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        btnRefresh.setPrefWidth(120);

        HBox box = new HBox(10);
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(btnAdd, btnDelete, btnRefresh);
        return box;
    }

    private TableView<Product> createTableView() {
        TableView<Product> table = new TableView<>();
        table.setPrefHeight(300);

        TableColumn<Product, String> colCode = new TableColumn<>("Kode");
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colCode.setPrefWidth(100);

        TableColumn<Product, String> colName = new TableColumn<>("Nama");
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
        btnAdd.setOnAction(e -> {
            String code = txtCode.getText();
            String name = txtName.getText();
            String price = txtPrice.getText();
            String stock = txtStock.getText();

            controller.add(code, name, price, stock);
            clearForm();
            loadData();
        });

        btnDelete.setOnAction(e -> {
            Product selected = tableView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Konfirmasi Hapus");
                confirm.setHeaderText(null);
                String msg = "Hapus produk: " + selected.getName() + "?";
                confirm.setContentText(msg);
                
                if (confirm.showAndWait().get() == ButtonType.OK) {
                    controller.delete(selected.getCode());
                    loadData();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Peringatan");
                alert.setHeaderText(null);
                alert.setContentText("Pilih produk yang akan dihapus!");
                alert.showAndWait();
            }
        });

        btnRefresh.setOnAction(e -> loadData());
    }

    private void loadData() {
        productList.clear();
        productList.addAll(controller.load());
    }

    private void clearForm() {
        txtCode.clear();
        txtName.clear();
        txtPrice.clear();
        txtStock.clear();
        txtCode.requestFocus();
    }
}
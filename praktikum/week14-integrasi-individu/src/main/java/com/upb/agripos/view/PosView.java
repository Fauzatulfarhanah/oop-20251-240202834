package com.upb.agripos.view;

import com.upb.agripos.controller.PosController;
import com.upb.agripos.model.Product;
import com.upb.agripos.model.CartItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PosView {
    private final PosController controller;
    private TextField txtCode, txtName, txtPrice, txtStock;
    private Button btnAdd, btnDelete, btnRefresh;
    private TableView<Product> productTable;
    private ObservableList<Product> productList;
    private ComboBox<Product> cmbProduct;
    private Spinner<Integer> spnQuantity;
    private Button btnAddToCart, btnClearCart;
    private ListView<String> cartListView;
    private Label lblTotal;

    public PosView(PosController controller) {
        this.controller = controller;
        this.productList = FXCollections.observableArrayList();
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Agri-POS - Aplikasi Kasir Pertanian");
        TabPane tabPane = new TabPane();
        Tab tabProduct = new Tab("Kelola Produk", createProductTab());
        Tab tabCashier = new Tab("Kasir", createCashierTab());
        tabProduct.setClosable(false);
        tabCashier.setClosable(false);
        tabPane.getTabs().addAll(tabProduct, tabCashier);
        Scene scene = new Scene(tabPane, 800, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
        loadProductData();
    }

    private VBox createProductTab() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        Label title = new Label("KELOLA PRODUK");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        GridPane formGrid = createProductForm();
        HBox buttonBox = createProductButtons();
        productTable = createProductTable();
        layout.getChildren().addAll(title, formGrid, buttonBox, new Separator(), new Label("Daftar Produk:"), productTable);
        setupProductHandlers();
        return layout;
    }

    private GridPane createProductForm() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Kode:"), 0, 0);
        txtCode = new TextField();
        grid.add(txtCode, 1, 0);
        grid.add(new Label("Nama:"), 0, 1);
        txtName = new TextField();
        grid.add(txtName, 1, 1);
        grid.add(new Label("Harga:"), 0, 2);
        txtPrice = new TextField();
        grid.add(txtPrice, 1, 2);
        grid.add(new Label("Stok:"), 0, 3);
        txtStock = new TextField();
        grid.add(txtStock, 1, 3);
        return grid;
    }

    private HBox createProductButtons() {
        btnAdd = new Button("Tambah Produk");
        btnAdd.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnDelete = new Button("Hapus Produk");
        btnDelete.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        btnRefresh = new Button("Refresh");
        btnRefresh.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        HBox box = new HBox(10, btnAdd, btnDelete, btnRefresh);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private TableView<Product> createProductTable() {
        TableView<Product> table = new TableView<>();
        table.setPrefHeight(250);
        TableColumn<Product, String> colCode = new TableColumn<>("Kode");
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        TableColumn<Product, String> colName = new TableColumn<>("Nama");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Product, Double> colPrice = new TableColumn<>("Harga");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn<Product, Integer> colStock = new TableColumn<>("Stok");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        table.getColumns().addAll(colCode, colName, colPrice, colStock);
        table.setItems(productList);
        return table;
    }

    private void setupProductHandlers() {
        btnAdd.setOnAction(e -> {
            controller.addProduct(txtCode.getText(), txtName.getText(), txtPrice.getText(), txtStock.getText());
            clearProductForm();
            loadProductData();
            refreshCashierProducts();
        });
        btnDelete.setOnAction(e -> {
            Product selected = productTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setContentText("Hapus: " + selected.getName() + "?");
                if (confirm.showAndWait().get() == ButtonType.OK) {
                    controller.deleteProduct(selected.getCode());
                    loadProductData();
                    refreshCashierProducts();
                }
            }
        });
        btnRefresh.setOnAction(e -> { loadProductData(); refreshCashierProducts(); });
    }

    private VBox createCashierTab() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        Label title = new Label("KASIR - TRANSAKSI PENJUALAN");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        HBox selectBox = createProductSelector();
        HBox actionBox = createCashierButtons();
        cartListView = new ListView<>();
        cartListView.setPrefHeight(250);
        lblTotal = new Label("TOTAL: Rp 0");
        lblTotal.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        layout.getChildren().addAll(title, new Label("Pilih Produk:"), selectBox, actionBox, new Separator(), new Label("Keranjang:"), cartListView, lblTotal);
        setupCashierHandlers();
        return layout;
    }

    private HBox createProductSelector() {
        cmbProduct = new ComboBox<>();
        cmbProduct.setPrefWidth(300);
        spnQuantity = new Spinner<>(1, 100, 1);
        spnQuantity.setPrefWidth(80);
        return new HBox(10, cmbProduct, new Label("Jumlah:"), spnQuantity);
    }

    private HBox createCashierButtons() {
        btnAddToCart = new Button("Tambah ke Keranjang");
        btnAddToCart.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        btnClearCart = new Button("Kosongkan Keranjang");
        btnClearCart.setStyle("-fx-background-color: #ff9800; -fx-text-fill: white;");
        HBox box = new HBox(10, btnAddToCart, btnClearCart);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private void setupCashierHandlers() {
        btnAddToCart.setOnAction(e -> {
            Product selected = cmbProduct.getValue();
            if (selected != null) {
                controller.addToCart(selected, spnQuantity.getValue());
                updateCartDisplay();
            }
        });
        btnClearCart.setOnAction(e -> { controller.clearCart(); updateCartDisplay(); });
    }

    private void loadProductData() {
        productList.clear();
        productList.addAll(controller.loadProducts());
    }

    private void refreshCashierProducts() {
        cmbProduct.getItems().clear();
        cmbProduct.getItems().addAll(controller.loadProducts());
    }

    private void updateCartDisplay() {
        cartListView.getItems().clear();
        for (CartItem item : controller.getCartService().getCartItems()) {
            cartListView.getItems().add(item.toString());
        }
        lblTotal.setText("TOTAL: Rp " + controller.getCartService().calculateTotal());
    }

    private void clearProductForm() {
        txtCode.clear();
        txtName.clear();
        txtPrice.clear();
        txtStock.clear();
    }
}

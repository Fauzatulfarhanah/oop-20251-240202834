package com.upb.agripos.view;

import com.upb.agripos.controller.ProductController;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ProductFormView {

    private TextField txtCode = new TextField();
    private TextField txtName = new TextField();
    private TextField txtPrice = new TextField();
    private TextField txtStock = new TextField();

    private TextArea txtAreaList = new TextArea();
    private Button btnAdd = new Button("Tambah Produk");

    private ProductController controller;

    public ProductFormView() {
        // === INJEKSI DEPENDENCY (DIP) ===
        ProductService service = new ProductService(new JdbcProductDAO());
        controller = new ProductController(service);

        btnAdd.setOnAction(e -> handleAddProduct());
    }

    public Parent getView() {
        txtAreaList.setEditable(false);

        VBox layout = new VBox(10,
                new Label("Kode Produk"), txtCode,
                new Label("Nama Produk"), txtName,
                new Label("Harga"), txtPrice,
                new Label("Stok"), txtStock,
                btnAdd,
                new Label("Daftar Produk"),
                txtAreaList
        );

        layout.setPadding(new Insets(15));
        return layout;
    }

    // === EVENT HANDLER ===
    private void handleAddProduct() {
        try {
            Product p = new Product(
                    txtCode.getText(),
                    txtName.getText(),
                    "", // category dikosongkan (model dari week sebelumnya)
                    Double.parseDouble(txtPrice.getText()),
                    Integer.parseInt(txtStock.getText())
            );

            controller.addProduct(p);

            txtAreaList.appendText(
                    p.getCode() + " - " + p.getName() + "\n"
            );

            clearForm();
        } catch (Exception ex) {
            showAlert(ex.getMessage());
        }
    }

    private void clearForm() {
        txtCode.clear();
        txtName.clear();
        txtPrice.clear();
        txtStock.clear();
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

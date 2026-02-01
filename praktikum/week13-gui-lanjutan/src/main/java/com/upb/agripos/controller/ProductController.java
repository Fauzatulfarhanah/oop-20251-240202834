package com.upb.agripos.controller;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;
import javafx.scene.control.Alert;
import java.util.List;
import java.util.ArrayList;

public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    public void add(String code, String name, String priceText, String stockText) {
        try {
            if (code.trim().isEmpty() || name.trim().isEmpty() || 
                priceText.trim().isEmpty() || stockText.trim().isEmpty()) {
                showAlert("Error", "Semua field harus diisi!", Alert.AlertType.ERROR);
                return;
            }

            double price;
            int stock;
            try {
                price = Double.parseDouble(priceText);
                stock = Integer.parseInt(stockText);
            } catch (NumberFormatException e) {
                showAlert("Error", "Harga dan Stok harus angka!", Alert.AlertType.ERROR);
                return;
            }

            Product product = new Product(code, name, price, stock);
            productService.insert(product);

            showAlert("Sukses", "Produk ditambahkan!", Alert.AlertType.INFORMATION);

        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            String msg = "Gagal tambah: " + e.getMessage();
            showAlert("Error", msg, Alert.AlertType.ERROR);
        }
    }

    public List<Product> load() {
        try {
            return productService.findAll();
        } catch (Exception e) {
            String msg = "Gagal load: " + e.getMessage();
            showAlert("Error", msg, Alert.AlertType.ERROR);
            return new ArrayList<>();
        }
    }

    public void delete(String code) {
        try {
            productService.delete(code);
            showAlert("Sukses", "Produk dihapus!", Alert.AlertType.INFORMATION);
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            String msg = "Gagal hapus: " + e.getMessage();
            showAlert("Error", msg, Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

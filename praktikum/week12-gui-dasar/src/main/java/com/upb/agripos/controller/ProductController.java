package com.upb.agripos.controller;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;

import javafx.scene.control.Alert;

/**
 * ProductController - MVC Controller
 * Mengatur interaksi antara View dan Service
 * Sesuai dengan Sequence Diagram: View → Controller → Service → DAO → DB
 */
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Handler untuk UC-01: Tambah Produk
     * Dipanggil dari View saat tombol Tambah diklik
     * Mengikuti Activity Diagram: validasi input → panggil service → update tampilan
     */
    public void add(String code, String name, String priceText, String stockText) {
        try {
            // Validasi input kosong
            if (code.trim().isEmpty() || name.trim().isEmpty() || 
                priceText.trim().isEmpty() || stockText.trim().isEmpty()) {
                showAlert("Error", "Semua field harus diisi!", Alert.AlertType.ERROR);
                return;
            }

            // Parse input
            double price;
            int stock;
            try {
                price = Double.parseDouble(priceText);
                stock = Integer.parseInt(stockText);
            } catch (NumberFormatException e) {
                showAlert("Error", "Harga dan Stok harus berupa angka!", Alert.AlertType.ERROR);
                return;
            }

            // Buat objek Product
            Product product = new Product(code, name, price, stock);

            // Panggil service untuk insert (sesuai Sequence Diagram)
            productService.insert(product);

            // Tampilkan pesan sukses
            showAlert("Sukses", "Produk berhasil ditambahkan!", Alert.AlertType.INFORMATION);

        } catch (IllegalArgumentException e) {
            showAlert("Error Validasi", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error Database", "Gagal menambahkan produk: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Mengambil semua produk dari database
     */
    public java.util.List<Product> getAllProducts() {
        try {
            return productService.findAll();
        } catch (Exception e) {
            showAlert("Error", "Gagal mengambil data: " + e.getMessage(), Alert.AlertType.ERROR);
            return new java.util.ArrayList<>();
        }
    }

    /**
     * Helper method untuk menampilkan alert
     */
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
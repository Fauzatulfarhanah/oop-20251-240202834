package com.upb.agripos.controller;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.service.CartService;
import javafx.scene.control.Alert;
import java.util.List;
import java.util.ArrayList;

public class PosController {
    private final ProductService productService;
    private final CartService cartService;

    public PosController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    public void addProduct(String code, String name, String priceText, String stockText) {
        try {
            if (code.trim().isEmpty() || name.trim().isEmpty() || 
                priceText.trim().isEmpty() || stockText.trim().isEmpty()) {
                showAlert("Error", "Semua field harus diisi!", Alert.AlertType.ERROR);
                return;
            }

            double price = Double.parseDouble(priceText);
            int stock = Integer.parseInt(stockText);

            Product product = new Product(code, name, price, stock);
            productService.insert(product);
            showAlert("Sukses", "Produk ditambahkan!", Alert.AlertType.INFORMATION);

        } catch (NumberFormatException e) {
            showAlert("Error", "Harga dan Stok harus angka!", Alert.AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Error", "Gagal tambah: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public List<Product> loadProducts() {
        try {
            return productService.findAll();
        } catch (Exception e) {
            showAlert("Error", "Gagal load: " + e.getMessage(), Alert.AlertType.ERROR);
            return new ArrayList<>();
        }
    }

    public void deleteProduct(String code) {
        try {
            productService.delete(code);
            showAlert("Sukses", "Produk dihapus!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void addToCart(Product product, int quantity) {
        try {
            cartService.addToCart(product, quantity);
            showAlert("Sukses", "Ditambahkan ke keranjang!", Alert.AlertType.INFORMATION);
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public CartService getCartService() {
        return cartService;
    }

    public void clearCart() {
        cartService.clearCart();
        showAlert("Info", "Keranjang dikosongkan!", Alert.AlertType.INFORMATION);
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

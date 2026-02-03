package com.upb.agripos.service;

import java.util.List;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.model.Product;

public class ProductService {
    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public void insert(Product product) throws Exception {
        if (product.getCode() == null || product.getCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Kode produk tidak boleh kosong");
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nama produk tidak boleh kosong");
        }
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Harga tidak boleh negatif");
        }
        if (product.getStock() < 0) {
            throw new IllegalArgumentException("Stok tidak boleh negatif");
        }
        
        productDAO.insert(product);
    }

    public List<Product> findAll() throws Exception {
        return productDAO.findAll();
    }

    public Product findByCode(String code) throws Exception {
        return productDAO.findByCode(code);
    }

    public void update(Product product) throws Exception {
        productDAO.update(product);
    }

    public void delete(String code) throws Exception {
        if (code == null || code.trim().isEmpty()) {
            throw new IllegalArgumentException("Kode tidak boleh kosong");
        }
        
        Product existing = productDAO.findByCode(code);
        if (existing == null) {
            throw new IllegalArgumentException("Produk tidak ditemukan");
        }
        
        productDAO.delete(code);
    }
}

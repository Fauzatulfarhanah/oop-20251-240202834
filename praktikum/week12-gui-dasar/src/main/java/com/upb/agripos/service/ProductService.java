package com.upb.agripos.service;

import java.util.List;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.model.Product;

/**
 * ProductService - Layer Service (sesuai DIP: View tidak langsung ke DAO)
 * Menerapkan prinsip Dependency Inversion Principle dari SOLID
 */
public class ProductService {
    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    /**
     * UC-01: Tambah Produk
     * Validasi input kemudian insert ke database
     */
    public void insert(Product product) throws Exception {
        // Validasi input sesuai Activity Diagram
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
        
        // Panggil DAO untuk insert
        productDAO.insert(product);
    }

    /**
     * Mengambil semua produk dari database
     */
    public List<Product> findAll() throws Exception {
        return productDAO.findAll();
    }

    /**
     * Mencari produk berdasarkan kode
     */
    public Product findByCode(String code) throws Exception {
        return productDAO.findByCode(code);
    }

    /**
     * Update produk
     */
    public void update(Product product) throws Exception {
        productDAO.update(product);
    }

    /**
     * Hapus produk berdasarkan kode
     */
    public void delete(String code) throws Exception {
        productDAO.delete(code);
    }
}
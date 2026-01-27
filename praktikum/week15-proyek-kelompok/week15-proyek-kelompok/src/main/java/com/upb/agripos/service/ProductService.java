package com.upb.agripos.service;

import java.util.List;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.exception.OutOfStockException;
import com.upb.agripos.exception.ValidationException;
import com.upb.agripos.model.Product;

public class ProductService {
    private ProductDAO dao;
    public ProductService(ProductDAO dao) { this.dao = dao; }

    public List<Product> getAllProducts() throws Exception { return dao.findAll(); }
    
    public void addProduct(Product p) throws Exception {
        if(dao.findByCode(p.getCode()) != null) throw new ValidationException("Kode ada!");
        dao.insert(p);
    }
    public void updateProduct(Product p) throws Exception { dao.update(p); }
    public void deleteProduct(String code) throws Exception { dao.delete(code); }
    
    public boolean validateStock(String code, int qty) throws Exception {
        Product p = dao.findByCode(code);
        return p != null && p.getStock() >= qty;
    }
    public void reduceStock(String code, int qty) throws Exception {
        Product p = dao.findByCode(code);
        int newStock = p.getStock() - qty;
        if(newStock < 0) throw new OutOfStockException("Stok minus");
        dao.updateStock(code, newStock);
    }
}
package com.upb.agripos.service;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.model.Product;

public class ProductService {

    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    // Ganti nama method-nya dari 'insert' jadi 'addProduct'
public void addProduct(Product product) throws Exception {
    if (product.getCode().isEmpty() || product.getName().isEmpty()) {
        throw new Exception("Kode dan Nama Produk wajib diisi!");
    }
    productDAO.insert(product);

    }
}

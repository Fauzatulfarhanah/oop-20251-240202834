package com.upb.agripos.controller;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;

public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // UC-01 Tambah Produk
    public void addProduct(Product product) throws Exception {
        productService.addProduct(product);
    }
}

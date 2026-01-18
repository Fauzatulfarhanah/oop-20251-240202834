package main.java.com.upb.agripos;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    private final Map<Product, Integer> items = new HashMap<>();

    // tambah produk
    public void addProduct(Product product, int qty) 
        throws InvalidQuantityException, OutOfStockException {

        if (qty <= 0) {
            throw new InvalidQuantityException(
                "Quantity harus lebih dari 0."
            );
        }

        // menambahkan pengecekan supaya OutOfStockException bisa dilempar
    if (product.getStock() == 0) {
        throw new OutOfStockException("Produk " + product.getName() + " sedang habis.");
    }
    

        items.put(product, items.getOrDefault(product, 0) + qty);
    }

    // hapus produk
    public void removeProduct(Product product)
            throws ProductNotFoundException {

        if (!items.containsKey(product)) {
            throw new ProductNotFoundException(
                "Produk tidak ada dalam keranjang."
            );
        }

        items.remove(product);
    }

    // checkout
    public void checkout()
            throws InsufficientStockException {

        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int qty = entry.getValue();

            if (product.getStock() < qty) {
                throw new InsufficientStockException(
                    "Stok tidak cukup untuk produk: " + product.getName()
                );
            }
        }

        // jika semua stok cukup
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            entry.getKey().reduceStock(entry.getValue());
        }

        items.clear();
    }
  

}


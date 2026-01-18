package main.java.com.upb.agripos;

public class MainExceptionDemo {

    public static void main(String[] args) {

        System.out.println("Hello, I am Fauzatul Farhanah - [240202834] (Week9)");

        ShoppingCart cart = new ShoppingCart();
        Product p1 = new Product("P01", "Pupuk Organik", 25000, 3);
        Product p2 = new Product("P02", "Benih Padi", 25000, 0);


        // kasus 1: quantity salah
        try {
            cart.addProduct(p1, -1);
        } catch (InvalidQuantityException | OutOfStockException e) {
            System.out.println("Kesalahan: " + e.getMessage());
        }

        // kasus 2: hapus produk yang belum ada
        try {
            cart.removeProduct(p1);
        } catch (ProductNotFoundException e) {
            System.out.println("Kesalahan: " + e.getMessage());
        }

        // kasus 3: stok tidak cukup
        try {
            cart.addProduct(p1, 5);
            cart.checkout();
        } catch (InvalidQuantityException | OutOfStockException | InsufficientStockException e) {
             System.out.println("Kesalahan: " + e.getMessage());
            }

        // kasus 4: Produk habis (stok = 0) tidak bisa ditambahkan ke keranjang
        try {
            cart.addProduct(p2, 1);
        } catch (InvalidQuantityException | OutOfStockException e) {
             System.out.println("Kesalahan: " + e.getMessage());
        }
        finally {
            System.out.println("Proses selesai.");
}

    }
}

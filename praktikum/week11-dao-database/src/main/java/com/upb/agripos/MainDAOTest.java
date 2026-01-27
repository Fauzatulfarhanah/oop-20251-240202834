package com.upb.agripos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.ProductDAOImpl;
import com.upb.agripos.model.Product;

public class MainDAOTest {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/agripos";
        String user = "postgres";
        String pass = "farhanah"; 

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            ProductDAO dao = new ProductDAOImpl(conn);

            System.out.println("=== 1. INSERT DATA ===");
            dao.insert(new Product("P03", "Pupuk Urea", 25000, 10));
            dao.insert(new Product("P04", "Beras ", 50000, 20));
            System.out.println("Data berhasil dimasukkan.\n");

            System.out.println("=== 2. UPDATE DATA ===");
            dao.update(new Product("P01", "Pupuk Organik Premium", 30000, 8));
            System.out.println("Data P01 berhasil diupdate.\n");

            System.out.println("=== 3. FIND BY CODE (P01) ===");
            Product p = dao.findByCode("P01");
            if (p != null) {
                System.out.println("Nama: " + p.getName() + " | Harga: " + p.getPrice());
            }
            System.out.println();

            System.out.println("=== 4. READ ALL DATA ===");
            List<Product> products = dao.findAll();
            for (Product item : products) {
                System.out.println(item.getCode() + " - " + item.getName() + " (" + item.getStock() + ")");
            }
            System.out.println();

            System.out.println("=== 5. DELETE DATA (P02) ===");
            dao.delete("P02");
            System.out.println("Data P02 berhasil dihapus.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package com.upb.agripos;

import com.upb.agripos.config.DatabaseConnection;
import com.upb.agripos.controller.ProductController;
import com.upb.agripos.model.Product;
import com.upb.agripos.view.ConsoleView;

public class AppMVC {
    public static void main(String[] args) {
        System.out.println("Hello, I am Fauzatul Farhanah-[240202834] (Week10)");

        // Singleton dipanggil
        DatabaseConnection.getInstance();

        // MVC
        Product product = new Product("P01", "Pupuk Organik");
        ConsoleView view = new ConsoleView();
        ProductController controller =
                new ProductController(product, view);

        controller.showProduct();
    }
}

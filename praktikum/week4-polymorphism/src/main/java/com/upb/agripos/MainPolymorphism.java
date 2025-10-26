package main.java.com.upb.agripos;

import main.java.com.upb.agripos.model.*;
import main.java.com.upb.agripos.util.CreditBy;

public class MainPolymorphism {
    public static void main(String[] args) {
        Produk[] daftarProduk = {
            new Benih("BNH-001", "Benih Padi IR64", 25000, 100, "IR64"),
            new Pupuk("PPK-101", "Pupuk Urea", 350000, 40, "Urea"),
            new AlatPertanian("ALT-501", "Cangkul Baja", 90000, 15, "Baja"),
            new VitaminTanaman("VT-001", "Vit-Grow", 30000, 40, "Nitrogen tinggi", "Meningkatkan pertumbuhan daun"),
            new ObatHama("OBH-301", "Insektisida Regent", 45000, 60, "Fipronil", "Ulat Grayak"),
            new MediaTanam("MT-001", "Cocopeat Premium", 15000, 80, "Cocopeat")
        };

        for (Produk p : daftarProduk) {
            System.out.println(p.getInfo()); // Dynamic Binding
        }

        CreditBy.print("<240202834>", "<Fauzatul Farhanah>");
    }
}
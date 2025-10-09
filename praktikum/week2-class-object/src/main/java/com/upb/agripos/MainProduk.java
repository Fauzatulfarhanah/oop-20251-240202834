package main.java.com.upb.agripos;

import main.java.com.upb.agripos.util.CreditBy;
import main.java.com.upb.agripos.model.Produk;

public class MainProduk {
    public static void main(String[] args) {
        Produk p1 = new Produk("KMI-001", "AirPods", 2100000, 150);
        Produk p2 = new Produk("SMR-002", "Smartwatch", 1500000, 98);
        Produk p3 = new Produk("FTB-003", "FitBand", 2000000, 119);
 
        System.out.println("=== Daftar Produk ===");
        System.out.println("Kode: " + p1.getKode() + ", Nama: " + p1.getNama() + ", Harga: " + p1.getHarga() + ", Stok: " + p1.getStok());
        System.out.println("Kode: " + p2.getKode() + ", Nama: " + p2.getNama() + ", Harga: " + p2.getHarga() + ", Stok: " + p2.getStok());
        System.out.println("Kode: " + p3.getKode() + ", Nama: " + p3.getNama() + ", Harga: " + p3.getHarga() + ", Stok: " + p3.getStok());
        
        System.out.println("\n=== Perubahan Jumlah Stok ===");
        p1.kurangiStok(40);
        p2.tambahStok(5);
        p3.kurangiStok(19);

        System.out.println(p1.getNama() + " Sisa Stok (-40): " + p1.getStok()) ;
        System.out.println(p2.getNama() + " Sisa Stok (+5): " + p2.getStok()) ;
        System.out.println(p3.getNama() + " Sisa Stok (-19): " + p3.getStok()) ;

        // Tampilkan identitas mahasiswa
        CreditBy.print("240202834", "Fauzatul Farhanah");
    }
}
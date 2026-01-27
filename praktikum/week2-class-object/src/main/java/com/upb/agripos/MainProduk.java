package main.java.com.upb.agripos;

import main.java.com.upb.agripos.model.Produk;
import main.java.com.upb.agripos.util.CreditBy;

public class MainProduk {
    public static void main(String[] args) {
        Produk p1 = new Produk("BNH-001", "Benih Padi", 25000, 100);
        Produk p2 = new Produk("PPK-002", "Pupuk Urea", 35000, 40);
        Produk p3 = new Produk("ALT-003", "Cangkul Baja", 90000, 15);
 
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
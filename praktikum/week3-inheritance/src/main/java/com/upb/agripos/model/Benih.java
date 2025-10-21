package com.upb.agripos.model;

public class Benih extends Produk {
    private String varietas; // atribut khusus Benih

    public Benih(String kode, String nama, double harga, int stok, String varietas) {
        super(kode, nama, harga, stok);
        this.varietas = varietas;
    }

    public String getVarietas() { return varietas; }
    public void setVarietas(String varietas) { this.varietas = varietas; }

    //method tambahan
        public void deskripsi() {
        System.out.println("======== Deskripsi Produk Benih ========");
        System.out.println("Kode: " + getKode());
        System.out.println("Nama: " + getNama());
        System.out.println("Harga: Rp" + getHarga());
        System.out.println("Stok: " + getStok() + " unit");
        System.out.println("Varietas: " + varietas);
        System.out.println("========================================");
        System.out.println();
       
    }
}
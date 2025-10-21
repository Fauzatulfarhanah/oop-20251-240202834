package com.upb.agripos.model;

public class AlatPertanian extends Produk {
    private String material; // atribut khusus Alat Pertanian

    public AlatPertanian(String kode, String nama, double harga, int stok, String material) {
        super(kode, nama, harga, stok);
        this.material = material;
    }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

        public void deskripsi() {
        System.out.println("=== Deskripsi Produk Alat Pertanian ===");
        System.out.println("Kode: " + getKode());
        System.out.println("Nama: " + getNama());
        System.out.println("Harga: Rp" + getHarga());
        System.out.println("Stok: " + getStok() + " unit");
        System.out.println("Material: " + material);
        System.out.println("========================================");
        System.out.println();
    }

}
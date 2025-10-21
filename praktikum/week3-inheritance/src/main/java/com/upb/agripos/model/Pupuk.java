package com.upb.agripos.model;

public class Pupuk extends Produk {
    private String jenis; // atribut khusus Pupuk

    public Pupuk(String kode, String nama, double harga, int stok, String jenis) {
        super(kode, nama, harga, stok);
        this.jenis = jenis;
    }

    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }

    // method tambahan
    public void deskripsi() {
        System.out.println("======== Deskripsi Produk Pupuk ========");
        System.out.println("Kode: " + getKode());
        System.out.println("Nama: " + getNama());
        System.out.println("Harga: Rp" + getHarga());
        System.out.println("Stok: " + getStok() + " unit");
        System.out.println("Jenis: " + jenis);
        System.out.println("========================================");
        System.out.println();
        
    }
}
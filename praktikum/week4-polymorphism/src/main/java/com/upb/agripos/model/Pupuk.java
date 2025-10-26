package main.java.com.upb.agripos.model;

public class Pupuk extends Produk {
    private String jenis; // atribut khusus Pupuk

    public Pupuk(String kode, String nama, double harga, int stok, String jenis) {
        super(kode, nama, harga, stok);
        this.jenis = jenis;
    }

    // Getter & Setter
    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }

    // === Overriding untuk polymorphism ===
    @Override
    public String getInfo() {
        return "[Pupuk]\n" + super.getInfo() + "\n" + "Jenis: " + jenis + "\n";
    }

}

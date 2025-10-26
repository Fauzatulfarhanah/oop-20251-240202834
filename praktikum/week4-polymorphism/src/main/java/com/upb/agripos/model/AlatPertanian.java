package main.java.com.upb.agripos.model;

public class AlatPertanian extends Produk {
    private String kategori;

    public AlatPertanian(String kode, String nama, double harga, int stok, String kategori) {
        super(kode, nama, harga, stok);
        this.kategori = kategori;
    }

    @Override
    public String getInfo() {
        return "[Alat Pertanian]\n" + super.getInfo() + "\n" + "Kategori: " + kategori + "\n";
    }
}

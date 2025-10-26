package main.java.com.upb.agripos.model;

public class VitaminTanaman extends Produk {
    private String kandungan;
    private String manfaat;

    public VitaminTanaman(String kode, String nama, double harga, int stok, String kandungan, String manfaat) {
        super(kode, nama, harga, stok);
        this.kandungan = kandungan;
        this.manfaat = manfaat;
    }

    @Override
public String getInfo() {
    return "[Vitamin Tanaman]\n" +
           super.getInfo() + "\n" +
           "Kandungan : " + kandungan + "\n" +
           "Manfaat   : " + manfaat + "\n";
    }
}

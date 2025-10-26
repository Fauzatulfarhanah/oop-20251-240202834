package main.java.com.upb.agripos.model;

public class MediaTanam extends Produk {
    private String bahan; // contoh: "Cocopeat", "Sekam bakar", "Tanah humus"

    public MediaTanam(String kode, String nama, double harga, int stok, String bahan) {
        super(kode, nama, harga, stok);
        this.bahan = bahan;
    }

    public String getBahan() {
        return bahan;
    }

    public void setBahan(String bahan) {
        this.bahan = bahan;
    }

    @Override
    public String getInfo() {
        return "[Media Tanam]\n" + super.getInfo() + "\n" + "bahan: " + bahan;
               
    }
}
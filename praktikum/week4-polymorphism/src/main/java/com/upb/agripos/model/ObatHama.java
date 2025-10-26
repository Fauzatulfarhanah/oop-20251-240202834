package main.java.com.upb.agripos.model;

public class ObatHama extends Produk {
    private String bahanAktif;
    private String targetHama;

    public ObatHama(String kode, String nama, double harga, int stok, String bahanAktif, String targetHama) {
        super(kode, nama, harga, stok);
        this.bahanAktif = bahanAktif;
        this.targetHama = targetHama;
    }

    // === Overriding untuk Polymorphism ===
    @Override
    public String getInfo() {
        return "[Obat Hama]\n" +
                super.getInfo() + "\n" +
                "Bahan Aktif : " + bahanAktif + "\n" +
                "Target Hama : " + targetHama +"\n";
    }
}

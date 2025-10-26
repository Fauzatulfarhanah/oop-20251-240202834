package main.java.com.upb.agripos.model;

public class Produk {
    private String kode;
    private String nama;
    private double harga;
    private int stok;

    public Produk(String kode, String nama, double harga, int stok) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    // === Method Overloading ===
    public void tambahStok(int jumlah) {
        if (jumlah > 0) {
            stok += jumlah;
            System.out.println("[INFO] Stok bertambah sebanyak " + jumlah);
        } else {
            System.out.println("[PERINGATAN] Jumlah stok tidak boleh negatif!");
        }
    }

    public void tambahStok(double jumlah) {
        if (jumlah > 0) {
            int tambahan = (int) jumlah;
            stok += tambahan;
            System.out.println("[INFO] Stok bertambah (double): " + jumlah + " → dibulatkan jadi " + tambahan);
        } else {
            System.out.println("[PERINGATAN] Jumlah stok tidak boleh negatif!");
        }
    }

    // === Overridable Method ===
    public String getInfo() {
    return "Kode  : " + kode + "\n" +
           "Nama  : " + nama + "\n" +
           "Harga : Rp" + harga + "\n" +
           "Stok  : " + stok;
}

}

package com.upb.agripos.model.pembayaran;

import com.upb.agripos.model.kontrak.Validatable;
import com.upb.agripos.model.kontrak.Receiptable;

public class TransferBank extends Pembayaran implements Validatable, Receiptable {
    
    // Biaya tetap: Rp3500
    private static final double BIAYA_TETAP = 3500.0;
    private String kodeKonfirmasi; // Field untuk simulasi validasi

    public TransferBank(String invoiceNo, double total, String kodeKonfirmasi) {
        super(invoiceNo, total);
        this.kodeKonfirmasi = kodeKonfirmasi;
    }

    // Metode Abstrak Pembayaran
    @Override
    public double biaya() {
        return BIAYA_TETAP;
    }

    @Override
    public boolean prosesPembayaran() {
        // Pembayaran berhasil jika validasi sukses
        if (this.validasi()) {
          //  System.out.println("[TransferBank] Pembayaran diproses sebesar Rp " + String.format("%,.0f", this.totalBayar()));
            return true;
        }
        return false;
    }

    // Metode Interface Validatable
    @Override
    public boolean validasi() {
        // Contoh validasi: Kode konfirmasi harus 4 digit
        boolean isValid = kodeKonfirmasi != null && kodeKonfirmasi.length() == 4;
        // System.out.println("[TransferBank] Validasi Kode Konfirmasi (4 digit) " + (isValid ? "BERHASIL." : "GAGAL!"));
        return isValid;
    }

    // Metode Interface Receiptable
    @Override
    public String cetakStruk() {
        String status = prosesPembayaran() ? "BERHASIL" : "GAGAL (Konfirmasi Salah)";

        return "\n== STRUK PEMBAYARAN TRANSFER BANK ==\n" +
               "Invoice No.   : " + this.invoiceNo + "\n" +
               "Total Belanja : Rp " + String.format("%,.0f", this.total) + "\n" +
               "Biaya (Tetap) : Rp " + String.format("%,.0f", this.biaya()) + "\n" +
               "--------------------------------------\n" +
               "TOTAL BAYAR   : Rp " + String.format("%,.0f", this.totalBayar()) + "\n" +
               "STATUS        : " + status + "\n" +
               "--------------------------------------";
    }
}
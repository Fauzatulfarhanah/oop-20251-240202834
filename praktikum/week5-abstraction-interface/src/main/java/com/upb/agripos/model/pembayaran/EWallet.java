package com.upb.agripos.model.pembayaran;

import com.upb.agripos.model.kontrak.Validatable;
import com.upb.agripos.model.kontrak.Receiptable;

public class EWallet extends Pembayaran implements Validatable, Receiptable {
    private final String akun;
    private final String otp; // sederhana untuk simulasi

    public EWallet(String invoiceNo, double total, String akun, String otp) {
        super(invoiceNo, total);
        this.akun = akun;
        this.otp = otp;
    }

    @Override
    public double biaya() {
        return total * 0.015; // 1.5% fee
    }

    @Override
    public boolean validasi() {
        return otp != null && otp.length() == 6; // contoh validasi sederhana
    }

    @Override
    public boolean prosesPembayaran() {
        return validasi(); // jika validasi lolos, anggap berhasil
    }

    @Override
   public String cetakStruk() {
    // Panggil prosesPembayaran() untuk menentukan status
    String status = prosesPembayaran() ? "BERHASIL" : "GAGAL (Validasi Gagal)";

    return "==== STRUK PEMBAYARAN E-WALLET ====\n" +
           "Invoice No.   : " + this.invoiceNo + "\n" +
           "Akun E-Wallet : " + this.akun + "\n" + // Tambahkan detail akun
           "Total Belanja : Rp " + String.format("%,.2f", this.total) + "\n" +
           "Biaya (1.5%)  : Rp " + String.format("%,.2f", this.biaya()) + "\n" +
           "--------------------------------------\n" +
           "TOTAL BAYAR   : Rp " + String.format("%,.2f", this.totalBayar()) + "\n" +
           "STATUS        : " + status + "\n" +
           "--------------------------------------\n" ;
    }
}
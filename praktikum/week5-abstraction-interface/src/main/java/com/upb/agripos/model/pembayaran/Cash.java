package com.upb.agripos.model.pembayaran;

import com.upb.agripos.model.kontrak.Receiptable;

public class Cash extends Pembayaran implements Receiptable {
    private double tunai;

    public Cash(String invoiceNo, double total, double tunai) {
        super(invoiceNo, total);
        this.tunai = tunai;
    }

    @Override
    public double biaya() {
        return 0.0;
    }

    @Override
    public boolean prosesPembayaran() {
        // Berhasil jika uang tunai (tunai) mencukupi (>= totalBayar)
        return this.tunai >= this.totalBayar(); // sederhana: cukup uang tunai
    }

    @Override
    public String cetakStruk() {
        String status = prosesPembayaran() ? "BERHASIL" : "GAGAL (Uang Kurang)";
        double kembalian = prosesPembayaran() ? this.tunai - this.totalBayar() : 0.0;
        
        return "\n====== STRUK PEMBAYARAN TUNAI ======\n" +
               "Invoice No.    : " + this.invoiceNo + "\n" +
               "Total Belanja  : Rp " + String.format("%.0f", this.total) + "\n" +
               "Biaya (Fee)    : Rp " + String.format("%.0f", this.biaya()) + "\n" +
              "--------------------------------------\n" +
               "TOTAL BAYAR    : Rp " + String.format("%.0f", this.totalBayar()) + "\n" +
               "Tunai Diberikan: Rp " + String.format("%.0f", this.tunai) + "\n" +
               "Kembalian      : Rp " + String.format("%.0f", kembalian) + "\n" +
               "STATUS         : " + status + "\n" +
               " \n" +
               "--------------------------------------";
    }
    }
# Laporan Praktikum Minggu 5 
Topik: [Abstraction (Abstract Class & Interface)]

## Identitas
- Nama  : [Fauzatul Farhanah]
- NIM   : [240202834]
- Kelas : [3IKRA]

---

## Tujuan
- Mahasiswa mampu **menjelaskan perbedaan abstract class dan interface**.
- Mahasiswa mampu **mendesain abstract class dengan method abstrak** sesuai kebutuhan kasus.
- Mahasiswa mampu **membuat interface dan mengimplementasikannya pada class**.
- Mahasiswa mampu **menerapkan multiple inheritance melalui interface** pada rancangan kelas.
- Mahasiswa mampu **mendokumentasikan kode** (komentar kelas/method, README singkat pada folder minggu).

---

## Dasar Teori
**Abstraksi** adalah proses menyederhanakan kompleksitas dengan menampilkan elemen penting dan menyembunyikan detail implementasi.
- **Abstract class**: tidak dapat diinstansiasi, dapat memiliki method abstrak (tanpa badan) dan non-abstrak. Dapat menyimpan state (field).
- **Interface**: kumpulan kontrak (method tanpa implementasi konkret). Sejak Java 8 mendukung default method. Mendukung **multiple inheritance** (class dapat mengimplementasikan banyak interface).
- Gunakan **abstract class** bila ada _shared state_ dan perilaku dasar; gunakan **interface** untuk mendefinisikan kemampuan/kontrak lintas hierarki.

Dalam konteks Agri-POS, **Pembayaran** dapat dimodelkan sebagai abstract class dengan method abstrak `prosesPembayaran()` dan `biaya()`. Implementasi konkritnya: `Cash` dan `EWallet`. Kemudian, interface seperti `Validatable` (mis. verifikasi OTP) dan `Receiptable` (mencetak bukti) dapat diimplementasikan oleh jenis pembayaran yang relevan.

---

## Langkah Praktikum
1. **Abstract Class – Pembayaran**
   - Buat `Pembayaran` (abstract) dengan field `invoiceNo`, `total` dan method:
     - `double biaya()` (abstrak) → biaya tambahan (fee).
     - `boolean prosesPembayaran()` (abstrak) → mengembalikan status berhasil/gagal.
     - `double totalBayar()` (konkrit) → `return total + biaya();`.

2. **Subclass Konkret**
   - `Cash` → biaya = 0, proses = selalu berhasil jika `tunai >= totalBayar()`.
   - `EWallet` → biaya = 1.5% dari `total`; proses = membutuhkan validasi.
   - `TransferBank` → biaya tetap = Rp3.500; proses = berhasil jika kode konfirmasi 4 digit valid (digunakan sebagai simulasi validasi transfer bank).

3. **Interface**
   - `Validatable` → `boolean validasi();` (contoh: OTP).
   - `Receiptable` → `String cetakStruk();`

4. **Multiple Inheritance via Interface**
   - `EWallet` mengimplementasikan **dua interface**: `Validatable`, `Receiptable`.
   - `Cash` setidaknya mengimplementasikan `Receiptable`.
   - `TransferBank` mengimplementasikan **dua interface**, yaitu `Validatable` dan `Receiptable`, karena memerlukan validasi kode konfirmasi 4 digit dan juga menghasilkan struk pembayaran.

5. **Main Class**
    - Buat `MainAbstraction.java` untuk mendemonstrasikan pemakaian `Pembayaran` (polimorfik).
    - Tampilkan hasil proses dan struk. Di akhir, panggil `CreditBy.print("[NIM]", "[Nama]")`.

6. **Commit dan Push**
   - Commit dengan pesan: `week5-abstraction-interface`.
   - Push ke Github.
---

## Kode Program 

```java
// Pembayaran.java
package com.upb.agripos.model.pembayaran;

public abstract class Pembayaran {
    protected String invoiceNo;
    protected double total;

    public Pembayaran(String invoiceNo, double total) {
        this.invoiceNo = invoiceNo;
        this.total = total;
    }

    public abstract double biaya();               // fee/biaya tambahan
    public abstract boolean prosesPembayaran();   // proses spesifik tiap metode

    public double totalBayar() {
        return total + biaya();
    }

    public String getInvoiceNo() { return invoiceNo; }
    public double getTotal() { return total; }
}
```
---
// Cash.java
```java
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
```
---
```java
//EWallet.java
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
```
---

```java
//MainAbstraction.java
package com.upb.agripos;

import com.upb.agripos.model.pembayaran.*;
import com.upb.agripos.model.kontrak.*;
import com.upb.agripos.util.CreditBy;

public class MainAbstraction {
    public static void main(String[] args) {
        Pembayaran cash = new Cash("INV-001", 100000, 120000);
        Pembayaran ew = new EWallet("INV-002", 150000, "farhanahfzt", "123456");
        Pembayaran transfer = new TransferBank("INV-1003", 250000.0, "9876");

        System.out.println(((Receiptable) cash).cetakStruk());
        System.out.println(((Receiptable) ew).cetakStruk());
        System.out.println(((Receiptable) transfer).cetakStruk());

    CreditBy.print("<240202834>", "Fauzatul Farhanah");
    }
}
```
---

---
```java
//TransferBank.java
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
```
---
```java
//Receiptable.java
package com.upb.agripos.model.kontrak;

public interface Receiptable {
    String cetakStruk();
}
```
---
```java
//Validatable.java
package com.upb.agripos.model.kontrak;

public interface Validatable {
    boolean validasi(); // misal validasi OTP/ PIN
}
```
---
```java
//CreditBy.java
package com.upb.agripos.util;
// creditBy.java


public class CreditBy {
    public static void print(String nim, String nama) {
        System.out.println("\ncredit by: " + nim + " - " + nama);
    }
}
```
---
---
## Hasil Eksekusi
![alt text](<Screenshot Abstraction(Abstract Class & Interface).png>)
---

---
## Analisis
1. Jelaskan bagaimana kode berjalan. 
 => Program ini menerapkan konsep abstraksi dan interface pada sistem pembayaran.
Kelas abstrak Pembayaran berfungsi sebagai blueprint yang mendefinisikan atribut umum seperti invoiceNo dan total, serta method abstrak ``biaya()`` dan ``prosesPembayaran()`` yang harus diimplementasikan oleh subclass.
Selain itu, ada method konkret ``totalBayar()`` yang mengembalikan hasil penjumlahan antara total dan biaya tambahan.

Subclass ``Cash``, ``EWallet``, dan ``TransferBank`` masing-masing mengimplementasikan logika pembayaran yang berbeda:

- Cash: tidak memiliki biaya tambahan dan pembayaran berhasil jika uang tunai cukup.

- EWallet: memiliki biaya 1.5% dari total dan memerlukan proses validasi OTP.

- TransferBank: memiliki biaya tetap (misal Rp3.500) dan juga memerlukan validasi.

Interface ``Validatable`` dan ``Receiptable`` digunakan untuk memisahkan tanggung jawab validasi dan pencetakan struk.

- ``Validatable`` mendefinisikan method ``boolean validasi()``.

- ``Receiptable`` mendefinisikan method ``String cetakStruk()``.

Pada kelas ``MainAbstraction``, objek dari subclass (Cash, EWallet, dan TransferBank) dibuat secara polimorfik menggunakan referensi Pembayaran.
Setiap pembayaran diproses, kemudian hasil struk dicetak melalui pemanggilan ``cetakStruk()``.
Program juga menampilkan identitas menggunakan ``CreditBy``.print(nim, nama).
2. Apa perbedaan pendekatan minggu ini dibanding minggu sebelumnya. 
=>  Minggu sebelumnya materi yang dipelajari adalah polymorphism, di mana kita belajar gimana satu method bisa punya perilaku yang berbeda tergantung dari objek yang memanggilnya. Pendekatan minggu lalu lebih fokus ke implementasi dan cara kerja objek yang berbeda-beda, tapi masih dalam satu struktur pewarisan (inheritance).

Sedangkan minggu ini, pendekatannya sedikit berbeda karena kita mulai masuk ke abstraksi lewat abstract class dan interface. Fokusnya bukan lagi pada bagaimana objek bekerja, tapi lebih ke bagaimana membuat kerangka dasar (blueprint) dari suatu sistem. Dari sini kita menentukan aturan umum yang nanti harus diikuti oleh class turunannya.

Jadi bisa dibilang, minggu lalu kita belajar tentang “bagaimana objek berperilaku berbeda”, sedangkan minggu ini kita belajar “bagaimana mendesain struktur dasar supaya objek-objek itu punya aturan yang jelas.”

3. Kendala yang dihadapi dan cara mengatasinya. 
=>- Error “cannot find symbol” atau “package does not exist”. Terjadi karena struktur folder dan deklarasi package belum sesuai.
Solusi: Menyesuaikan struktur folder dengan nama package yang sesuai.

- Warning “Variable status is never read”. Muncul karena variabel status tidak digunakan.
Solusi: Memperbaiki kode agar status dipakai dalam output struk ("Status: " + status).

- Struktur output belum sesuai format yang diinginkan
 Solusi: Menyesuaikan format string dalam ``cetakStruk()`` agar hasil tampil rapi dan jelas.

---

## Kesimpulan
Dengan menerapkan abstract class dan interface, program menjadi lebih terstruktur dan mudah dikembangkan karena setiap kelas memiliki peran dan tanggung jawab yang jelas.
Konsep abstraksi membantu kita membuat kerangka dasar dari sebuah sistem tanpa harus langsung menentukan detail implementasinya, sedangkan interface memungkinkan kelas berbeda berbagi perilaku yang sama tanpa harus berada dalam satu pewarisan langsung.

---

## Quiz
1. Jelaskan perbedaan konsep dan penggunaan **abstract class** dan **interface**.  
   **Jawaban:** Abstract class digunakan untuk membuat kerangka dasar (blueprint) dari suatu kelas, yang bisa memiliki atribut dan method konkrit maupun abstrak. Artinya, sebagian kodenya bisa langsung diisi, dan sebagian lagi harus di-override oleh subclass. 
   
   Sedangkan interface digunakan untuk menentukan kontrak perilaku yang harus dimiliki oleh suatu kelas. Semua method di dalam interface bersifat abstrak (tanpa isi), dan kelas yang mengimplementasikannya wajib mendefinisikan perilaku dari method-method tersebut.

2. Mengapa **multiple inheritance** lebih aman dilakukan dengan interface pada Java?    
   **Jawaban:** Karena interface tidak memiliki implementasi kode, maka multiple inheritance melalui interface di Java lebih aman dan tidak menimbulkan konflik antar method seperti pada pewarisan ganda class biasa.

3. Pada contoh Agri-POS, bagian mana yang **paling tepat** menjadi abstract class dan mana yang menjadi interface? Jelaskan alasannya.   
   **Jawaban:**  Pada contoh Agri-POS, bagian yang paling tepat dijadikan abstract class adalah ``Pembayaran``, karena kelas ini berfungsi sebagai kerangka dasar (blueprint) untuk berbagai jenis pembayaran seperti ``Cash``, ``EWallet``, dan ``TransferBank``. Pembayaran memiliki atribut umum (``invoiceNo``, ``total``) dan method yang bisa berbeda implementasinya pada subclass (biaya(), prosesPembayaran()), sehingga cocok dijadikan abstract class.
   
   Sedangkan yang paling tepat dijadikan interface adalah ``Validatable`` dan ``Receiptable``, karena keduanya hanya berisi kontrak perilaku (seperti validasi() dan cetakStruk()) tanpa membutuhkan atribut atau implementasi langsung.

   ---

   ## Referensi
- Liang, Y. D. *Introduction to Java Programming* (Bab 14).  
- Horstmann, C. S. *Core Java Volume I – Fundamentals* (Bab 6).  
- Oracle Docs: *Abstract Methods and Classes*, *Interfaces*.  




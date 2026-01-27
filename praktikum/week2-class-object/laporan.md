# Laporan Praktikum Minggu 2
Topik: Class dan Object (Produk Elektronik)

---

## Identitas
- Nama  : [Fauzatul Farhanah]
- NIM   : [240202834]
- Kelas : [3IKRA]


---


## Tujuan Pembelajaran
- Mahasiswa mampu **menjelaskan konsep class, object, atribut, dan method** dalam OOP.  
- Mahasiswa mampu **menerapkan access modifier dan enkapsulasi** dalam pembuatan class.  
- Mahasiswa mampu **mengimplementasikan class Produk pertanian** dengan atribut dan method yang sesuai.  
- Mahasiswa mampu **mendemonstrasikan instansiasi object** serta menampilkan data produk pertanian di console.  
- Mahasiswa mampu **menyusun laporan praktikum** dengan bukti kode, hasil eksekusi, dan analisis sederhana.  

---

## Dasar Teori
1. Class adalah blueprint dari objek yang berisi atribut dan method.
2. Object merupakan instansiasi dari class yang memiliki data dan perilaku sendiri.
3. Enkapsulasi digunakan untuk melindungi data agar tidak diakses langsung dari luar class.
4. Method digunakan untuk menjalankan fungsi atau aksi dari suatu objek, seperti menambah atau mengurangi stok.
5. Package digunakan untuk mengelompokkan class agar program lebih terstruktur dan mudah dikelola.

---

## Langkah Praktikum
1. **Membuat Class Produk**
   - Buat file `Produk.java` pada package `model`.
   - Tambahkan atribut: `kode`, `nama`, `harga`, dan `stok`.
   - Gunakan enkapsulasi dengan menjadikan atribut bersifat private dan membuat getter serta setter untuk masing-masing atribut.  

2. **Membuat Class CreditBy**
   - Buat file `CreditBy.java` pada package `util`.
   - Isi class dengan method statis untuk menampilkan identitas mahasiswa di akhir output: `credit by: <NIM> - <Nama>`.

3. **Membuat Objek Produk dan Menampilkan Credit**
   - Buat file `MainProduk.java`.
   - Instansiasi minimal tiga objek produk, misalnya "Benih Padi", "Pupuk Urea", dan satu produk alat pertanian.
   - Tampilkan informasi produk melalui method getter.  
   - Panggil `CreditBy.print("<NIM>", "<Nama>")` di akhir `main` untuk menampilkan identitas.

4. **Commit dan Push**
   - Commit dengan pesan: `week2-class-object`.  

---

## Contoh Implementasi Program

### 1. Produk.java
```java
package main.java.com.upb.agripos.model;
// produk.java

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

    public String getKode() { return kode; }
    public void setKode(String kode) { this.kode = kode; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public double getHarga() { return harga; }
    public void setHarga(double harga) { this.harga = harga; }

    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }

    public void tambahStok(int jumlah) {
        if (jumlah > 0) {
            this.stok += jumlah;
        } else {
            System.out.println("Jumlah stok yang ditambahkan tidak boleh ditambahkan!");
            
        }
    }

    public void kurangiStok(int jumlah) {
        if (this.stok >= jumlah) {
            this.stok -= jumlah;
        } else {
            System.out.println("Stok tidak mencukupi!");
        }
    }
}
```

### 2. CreditBy.java
```java 
package main.java.com.upb.agripos.util;
// creditBy.java


public class CreditBy {
    public static void print(String nim, String nama) {
        System.out.println("\ncredit by: " + nim + " - " + nama);
    }
}
```
### 3. MainProduk.java
```java
package main.java.com.upb.agripos;

import main.java.com.upb.agripos.model.Produk;
import main.java.com.upb.agripos.util.CreditBy;

public class MainProduk {
    public static void main(String[] args) {
        Produk p1 = new Produk("BNH-001", "Benih Padi", 25000, 100);
        Produk p2 = new Produk("PPK-002", "Pupuk Urea", 35000, 40);
        Produk p3 = new Produk("ALT-003", "Cangkul Baja", 90000, 15);
 
        System.out.println("=== Daftar Produk ===");
        System.out.println("Kode: " + p1.getKode() + ", Nama: " + p1.getNama() + ", Harga: " + p1.getHarga() + ", Stok: " + p1.getStok());
        System.out.println("Kode: " + p2.getKode() + ", Nama: " + p2.getNama() + ", Harga: " + p2.getHarga() + ", Stok: " + p2.getStok());
        System.out.println("Kode: " + p3.getKode() + ", Nama: " + p3.getNama() + ", Harga: " + p3.getHarga() + ", Stok: " + p3.getStok());
        
        System.out.println("\n=== Perubahan Jumlah Stok ===");
        p1.kurangiStok(40);
        p2.tambahStok(5);
        p3.kurangiStok(19);

        System.out.println(p1.getNama() + " Sisa Stok (-40): " + p1.getStok()) ;
        System.out.println(p2.getNama() + " Sisa Stok (+5): " + p2.getStok()) ;
        System.out.println(p3.getNama() + " Sisa Stok (-19): " + p3.getStok()) ;

        // Tampilkan identitas mahasiswa
        CreditBy.print("240202834", "Fauzatul Farhanah");
    }
}
```
---

## Hasil Eksekusi
---
![class dan object](https://github.com/user-attachments/assets/c938d777-8825-468c-8a67-d3457f715181)

---

## Analisis

- Jelaskan bagaimana kode berjalan
   => Program dimulai dari class MainProduk yang membuat tiga objek dari class Produk. Setiap objek menyimpan data produk elektronik seperti kode, nama, harga, dan stok. Method `tambahStok()` dan `kurangiStok()` digunakan untuk mengubah jumlah stok produk, lalu hasilnya ditampilkan ke terminal.
  
- Apa perbedaan pendekatan minggu ini dibanding minggu sebelumnya
   => Pada minggu sebelumnya fokus pada pengenalan tiga paradigma (prosedural, fungsional, OOP), sedangkan minggu ini fokus pada penerapan OOP secara langsung, yaitu membuat class, object, dan memanfaatkan konsep enkapsulasi.
  
- Kendala yang dihadapi dan cara mengatasinya
  => kesalahan pada struktur package dan error ketika melakukan push ke GitHub.
Solusinya yaitu memperbaiki deklarasi package sesuai struktur folder, dan menggunakan perintah `git pull origin main --rebase` sebelum melakukan push ulang supaya repository lokal dan remote tetap sinkron.

---

## Kesimpulan
- Class adalah cetakan untuk membuat objek, sedangkan objek adalah instansiasi nyata dari class.
- Enkapsulasi (private + getter/setter) membantu melindungi data agar tidak diubah langsung dari luar class.
- Method seperti tambahStok() dan kurangiStok() memungkinkan mengatur perilaku objek sesuai kebutuhan.
- Dengan menggunakan class dan object, program menjadi lebih terstruktur dan mudah dikembangkan.
- Praktikum ini menunjukkan penerapan OOP secara langsung, berbeda dengan minggu sebelumnya yang lebih ke pengenalan paradigma pemrograman.
  
---

## Quiz
1. Mengapa atribut sebaiknya dideklarasikan sebagai private dalam class?
**Jawaban:** Atribut sebaiknya dideklarasikan sebagai private dalam class agar data tidak bisa diakses atau diubah langsung dari luar class. Dengan begitu, setiap perubahan data harus melalui getter atau setter, sehingga kita bisa untuk mengontrol, memvalidasi, dan menjaga konsistensi data. Penggunaan private juga meningkatkan keamanan program karena mencegah pihak luar memodifikasi data secara sembarangan.

2. Apa fungsi getter dan setter dalam enkapsulasi?
**Jawaban:** Getter dan setter berfungsi sebagai method untuk mengakses dan mengubah atribut private dalam sebuah class. Getter digunakan untuk mendapatkan nilai atribut, sedangkan setter digunakan untuk memberikan atau mengubah nilai atribut dengan tetap mengontrol dan memvalidasi data sehingga tetap aman dan konsisten sesuai aturan class.

3. Bagaimana cara class Produk mendukung pengembangan aplikasi POS yang lebih kompleks?
**Jawaban:** Class Produk mendukung pengembangan aplikasi POS yang lebih kompleks dengan menyediakan struktur data yang terorganisir untuk setiap produk, termasuk atribut seperti kode, nama, harga, dan stok. Dengan adanya method seperti tambahStok() dan kurangiStok(), class ini memungkinkan manajemen stok secara otomatis. Karena class dapat diinstansiasi menjadi banyak objek, aplikasi bisa mengelola berbagai produk sekaligus, memudahkan integrasi dengan fitur lain seperti transaksi, laporan penjualan, dan inventaris, sehingga program menjadi modular, mudah dikembangkan, dan lebih terstruktur.
---

## Referensi
- Liang, Y. D. *Introduction to Java Programming* (Bab 9).  
- Schildt, H. *Java: The Complete Reference*.  
- Oracle Docs: [Classes and Objects](https://docs.oracle.com/javase/tutorial/java/concepts/class.html).  

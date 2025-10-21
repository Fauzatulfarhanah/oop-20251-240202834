# Laporan Praktikum Minggu 3 
Topik: Inheritance (Kategori Produk)

## Identitas
- Nama  : [Fauzatul Farhanah]
- NIM   : [240202834]
- Kelas : [3IKRA]

---

## Tujuan
- Mahasiswa mampu **menjelaskan konsep inheritance (pewarisan class)** dalam OOP.  
- Mahasiswa mampu **membuat superclass dan subclass** untuk produk pertanian.  
- Mahasiswa mampu **mendemonstrasikan hierarki class** melalui contoh kode.  
- Mahasiswa mampu **menggunakan `super` untuk memanggil konstruktor dan method parent class**.  
- Mahasiswa mampu **membuat laporan praktikum** yang menjelaskan perbedaan penggunaan inheritance dibanding class tunggal.
  
---

## Dasar Teori  
- Inheritance (pewarisan) adalah mekanisme dalam pemrograman berorientasi objek (OOP) yang memungkinkan sebuah class mewarisi atribut dan method dari class lain.
- Superclass atau parent class adalah kelas induk yang mendefinisikan atribut dan perilaku umum yang dapat digunakan oleh kelas turunannya.
- Subclass atau child class adalah kelas turunan yang mewarisi atribut dan method dari superclass, serta dapat menambahkan atribut atau method baru sesuai kebutuhan.
- Keyword super digunakan oleh subclass untuk mengakses konstruktor atau method milik superclass.
- Inheritance mendukung prinsip reusability (penggunaan ulang kode) dan extensibility (pengembangan kode), sehingga program menjadi lebih efisien dan mudah dipelihara.

---

## Langkah Praktikum
1. **Membuat Superclass Produk** (berisi atribut umum seperti nama, harga, dan stok).   

2. **Membuat Subclass**  
   - `Benih.java` → atribut tambahan: varietas.  
   - `Pupuk.java` → atribut tambahan: jenis pupuk (Urea, NPK, dll).  
   - `AlatPertanian.java` → atribut tambahan: material (baja, kayu, plastik).

3. **Menambahkan Method Tambahan di Subclass** ( Setiap subclass memiliki minimal satu method khusus yang menampilkan informasi tambahan sesuai atributnya).

3. **Membuat Main Class**  
   - Instansiasi minimal satu objek dari tiap subclass.  
   - Tampilkan data produk dengan memanfaatkan inheritance.  

4. **Menambahkan CreditBy** (untuk menampilkan identitas mahasiswa).

5. **Commit dan Push** (Commit dengan pesan: `week3-inheritance`).  

---

## Kode Program 

### Benih.java
```
package com.upb.agripos.model;

public class Benih extends Produk {
    private String varietas; // atribut khusus Benih

    public Benih(String kode, String nama, double harga, int stok, String varietas) {
        super(kode, nama, harga, stok);
        this.varietas = varietas;
    }

    public String getVarietas() { return varietas; }
    public void setVarietas(String varietas) { this.varietas = varietas; }

    //method tambahan
        public void deskripsi() {
        System.out.println("======== Deskripsi Produk Benih ========");
        System.out.println("Kode: " + getKode());
        System.out.println("Nama: " + getNama());
        System.out.println("Harga: Rp" + getHarga());
        System.out.println("Stok: " + getStok() + " unit");
        System.out.println("Varietas: " + varietas);
        System.out.println("========================================");
        System.out.println();
       
    }
}
```

### Pupuk.java
``
package com.upb.agripos.model;

public class Pupuk extends Produk {
    private String jenis; // atribut khusus Pupuk

    public Pupuk(String kode, String nama, double harga, int stok, String jenis) {
        super(kode, nama, harga, stok);
        this.jenis = jenis;
    }

    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }

    // method tambahan
    public void deskripsi() {
        System.out.println("======== Deskripsi Produk Pupuk ========");
        System.out.println("Kode: " + getKode());
        System.out.println("Nama: " + getNama());
        System.out.println("Harga: Rp" + getHarga());
        System.out.println("Stok: " + getStok() + " unit");
        System.out.println("Jenis: " + jenis);
        System.out.println("========================================");
        System.out.println();
        
    }
}
```

### AlatPertanian.java
```
package com.upb.agripos.model;

public class AlatPertanian extends Produk {
    private String material; // atribut khusus Alat Pertanian

    public AlatPertanian(String kode, String nama, double harga, int stok, String material) {
        super(kode, nama, harga, stok);
        this.material = material;
    }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

        public void deskripsi() {
        System.out.println("=== Deskripsi Produk Alat Pertanian ===");
        System.out.println("Kode: " + getKode());
        System.out.println("Nama: " + getNama());
        System.out.println("Harga: Rp" + getHarga());
        System.out.println("Stok: " + getStok() + " unit");
        System.out.println("Material: " + material);
        System.out.println("========================================");
        System.out.println();
    }

}
```

### MainInheritance.java

```
package com.upb.agripos;

import com.upb.agripos.model.*;
import com.upb.agripos.util.CreditBy;

public class MainInheritance {
    public static void main(String[] args) {
        Benih b = new Benih("BNH-001", "Benih Padi IR64", 25000, 100, "IR64");
        Pupuk p = new Pupuk("PPK-101", "Pupuk Urea", 350000, 40, "Urea");
        AlatPertanian a = new AlatPertanian("ALT-501", "Cangkul Baja", 90000, 15, "Baja");
        
        System.out.println();
        System.out.println("_________DATA PRODUK_________");
        System.out.println();
        b.deskripsi();
        p.deskripsi();
        a.deskripsi();

        CreditBy.print("<240202834>", "<Fauzatul Farhanah>");
    }
}

```
---

## Hasil Eksekusi
![Inheritance](https://github.com/user-attachments/assets/7698dac8-e412-4ef3-b987-8f5b41106771)

---

## Analisis
- Jelaskan bagaimana kode berjalan.
  Program diawali dengan class ``Produk`` sebagai superclass yang menyimpan atribut umum seperti ``nama``, ``harga``, dan ``stok``. Kemudian dibuat beberapa subclass seperti Benih, Pupuk, dan AlatPertanian yang masing-masing mewarisi atribut dan method dari Produk, serta menambahkan atribut dan method khusus. Pada main class, setiap subclass diinstansiasi menjadi objek, lalu method dari ``superclass`` dan ``subclass`` dipanggil untuk menampilkan data produk. 
- Apa perbedaan pendekatan minggu ini dibanding minggu sebelumnya.
  Pada praktikum minggu sebelumnya (Object dan Class), setiap objek dibuat dari satu class tanpa adanya hubungan antar kelas. Sementara pada minggu ini menggunakan konsep inheritance, di mana subclass dapat mewarisi atribut dan method dari superclass. Hal ini membuat kode lebih ringkas dan tidak berulang. 
- Kendala yang dihadapi dan cara mengatasinya.
  kendala saya yaitu di package nya solusinya dengan menyesuaikan antara file explorer dengan kode packagenya
 
---

## Kesimpulan
- Inheritance membuat class bisa mewarisi atribut dan method dari class lain.
- Program jadi lebih efisien, rapi, dan mudah dikembangkan.
- Keyword super digunakan untuk memanggil konstruktor atau method dari superclass.
- Dibanding minggu sebelumnya, konsep inheritance membantu menghindari penulisan kode berulang.

---

## Quiz
1. Apa keuntungan menggunakan inheritance dibanding membuat class terpisah tanpa hubungan?  
   **Jawaban:** Penggunaan inheritance lebih menguntungkan dibanding membuat class terpisah tanpa hubungan karena kode menjadi lebih hemat dan terstruktur. Atribut dan method yang sama cukup ditulis sekali di superclass lalu bisa digunakan oleh subclass. Dengan cara ini, program lebih mudah dikembangkan, dirawat, dan tidak perlu menulis ulang kode yang sama berulang kali. 

2. Bagaimana cara subclass memanggil konstruktor superclass?  
   **Jawaban:** Penggunaan inheritance lebih menguntungkan dibanding membuat class terpisah tanpa hubungan karena kode menjadi lebih hemat dan terstruktur. Atribut dan method yang sama cukup ditulis sekali di superclass lalu bisa digunakan oleh subclass. Dengan cara ini, program lebih mudah dikembangkan, dirawat, dan tidak perlu menulis ulang kode yang sama berulang kali.

3. Berikan contoh kasus di POS pertanian selain Benih, Pupuk, dan Alat Pertanian yang bisa dijadikan subclass.  
   **Jawaban:**
a. PakanIkan => Atribut tambahan: jenis ikan, berat kemasan, dan kandungan protein.
b. BibitTernak => Atribut tambahan: jenis hewan, usia, dan berat rata-rata.
c. MediaTanam => Atribut tambahan: jenis media (tanah, sekam, cocopeat), volume, dan kelembapan ideal.
d. ProdukOlahan => Atribut tambahan: bahan dasar, tanggal produksi, dan tanggal kedaluwarsa.

---

## Referensi
- Liang, Y. D. *Introduction to Java Programming* (Bab 11).  
- Horstmann, C. S. *Core Java Volume I – Fundamentals*.  
- Oracle Docs: [Inheritance](https://docs.oracle.com/javase/tutorial/java/IandI/subclasses.html).  

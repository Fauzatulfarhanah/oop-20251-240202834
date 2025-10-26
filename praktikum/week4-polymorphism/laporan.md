# Laporan Praktikum Minggu 4
Topik: Polymorphism (Info Produk)

## Identitas
- Nama  : [Fauzatul Farhanah]
- NIM   : [240202834]
- Kelas : [3IKRA]

---

## Tujuan Pembelajaran
- Mahasiswa mampu menjelaskan konsep polymorphism dalam OOP.  
- Mahasiswa mampu membedakan method overloading dan overriding.  
- Mahasiswa mampu mengimplementasikan polymorphism (overriding, overloading, dynamic binding) dalam program.  
- Mahasiswa mampu menganalisis contoh kasus polymorphism pada sistem nyata (Agri-POS).
---

## Dasar Teori
(Tuliskan ringkasan teori singkat (3–5 poin) yang mendasari praktikum.  
Contoh:  
1. Polymorphism adalah konsep dalam OOP (Object-Oriented Programming) yang memungkinkan satu interface digunakan untuk berbagai bentuk objek yang berbeda. 
2. Overloading yaitu mendefinisikan method dengan nama sama tetapi parameter berbeda.
3. Overriding yaitu subclass mengganti implementasi method dari superclass.
4. Dynamic Binding yaitu pemanggilan method ditentukan saat runtime, bukan compile time. 

Dalam konteks Agri-POS, misalnya:  
- Method `getInfo()` pada `Produk` dioverride oleh `Benih`, `Pupuk`, `AlatPertanian` untuk menampilkan detail spesifik.  
- Method `tambahStok()` bisa dibuat overload dengan parameter berbeda (int, double).  


---

## Langkah Praktikum
1. **Overloading**  
   - Tambahkan method `tambahStok(int jumlah)` dan `tambahStok(double jumlah)` pada class `Produk`.  

2. **Overriding**  
   - Tambahkan method `getInfo()` pada superclass `Produk`.  
   - Override method `getInfo()` pada subclass `Benih`, `Pupuk`,`AlatPertanian`, `VitaminTanaman`, `ObatHama`, dan `MediaTanam`.

3. **Dynamic Binding**  
   - Buat array `Produk[] daftarProduk` yang berisi objek `Benih`, `Pupuk`, `AlatPertanian`, `VitaminTanaman`, `ObatHama`, dan `MediaTanam`.
   - Loop array tersebut dan panggil `getInfo()`. Perhatikan bagaimana Java memanggil method sesuai jenis objek aktual.  

4. **Main Class**  
   - Buat `MainPolymorphism.java` untuk mendemonstrasikan overloading, overriding, dan dynamic binding.  

5. **CreditBy**  
   - Tetap panggil `CreditBy.print("<NIM>", "<Nama>")`.  

6. **Commit dan Push**  
   - Commit dengan pesan: `week4-polymorphism`. 
---

## Kode Program  

### Produk.java
```java
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
```

### Benih.java
```java
package main.java.com.upb.agripos.model;

public class Benih extends Produk {
    private String varietas;

    public Benih(String kode, String nama, double harga, int stok, String varietas) {
        super(kode, nama, harga, stok);
        this.varietas = varietas;
    }

    @Override
    public String getInfo() {
        return "[Benih]\n" + super.getInfo() + "\n"+ "Varietas: " + varietas +"\n";
    }
}
```

### Pupuk.java
```java
package main.java.com.upb.agripos.model;

public class Pupuk extends Produk {
    private String jenis; // atribut khusus Pupuk

    public Pupuk(String kode, String nama, double harga, int stok, String jenis) {
        super(kode, nama, harga, stok);
        this.jenis = jenis;
    }

    // Getter & Setter
    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }

    // === Overriding untuk polymorphism ===
    @Override
    public String getInfo() {
        return "[Pupuk]\n" + super.getInfo() + "\n" + "Jenis: " + jenis + "\n";
    }

}
```

### AlatPertanian.java
```java
package main.java.com.upb.agripos.model;

public class AlatPertanian extends Produk {
    private String kategori;

    public AlatPertanian(String kode, String nama, double harga, int stok, String kategori) {
        super(kode, nama, harga, stok);
        this.kategori = kategori;
    }

    @Override
    public String getInfo() {
        return "[Alat Pertanian]\n" + super.getInfo() + "\n" + "Kategori: " + kategori + "\n";
    }
}
```
### VitaminTanaman.java
```java
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
```

### ObatHama.java
```java
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
```

### MediaTanam.java
```java
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

```
---

## Hasil Eksekusi
---
<img width="972" height="861" alt="Screenshot 2025-10-26 064956" src="https://github.com/user-attachments/assets/8627a853-33d2-4dd6-8360-85098198a55a" />
<img width="870" height="812" alt="Screenshot 2025-10-26 065108" src="https://github.com/user-attachments/assets/a8fd33fa-2cb6-47d4-9fcb-aeceb70e6d6f" />

---


## Analisis
- Jelaskan bagaimana kode berjalan. 
**Jawaban:** Program ini menggunakan konsep polymorphism di Java, di mana objek-objek dari subclass (Benih, Pupuk,AlatPertanian, VitaminTanaman, ObatHama,dan MediaTanam) diperlakukan sebagai objek dari superclass `Produk`.
Di dalam `MainPolymorphism.java`, daftar produk disimpan dalam array `Produk[]`, lalu setiap objek dipanggil dengan method `tampilkanInfo()`.
Java akan otomatis memanggil versi method `tampilkanInfo()` yang sesuai dengan jenis objeknya. 

- Apa perbedaan pendekatan minggu ini dibanding minggu sebelumnya.  
**Jawaban:** Pada praktikum sebelumnya, konsep inheritance digunakan untuk mewarisi atribut dan method dari kelas induk. Sedangkan pada minggu ini, konsep polymorphism digunakan agar satu referensi superclass dapat memanggil method dengan hasil berbeda sesuai kelas objeknya.

- Kendala yang dihadapi dan cara mengatasinya.  
**Jawaban:** Awalnya terjadi error pada pemanggilan constructor karena argumen tidak sesuai antara superclass dan subclass.  
Solusi: Menyesuaikan urutan dan tipe data parameter di setiap constructor subclass agar sesuai dengan `Produk`

---

## Kesimpulan
Polymorphism membuat program bisa menggunakan satu metode yang sama untuk berbagai jenis objek dari subclass yang berbeda. Dengan cara ini, pengelolaan data produk seperti benih, pupuk, alat pertanian, vitamin tanaman, obat hama, dan media tanam menjadi lebih mudah dan terstruktur dalam satu program.

---

## Quiz
1. Apa perbedaan overloading dan overriding?  
   **Jawaban:** Overloading terjadi dalam satu class yang sama, ketika ada beberapa method dengan nama yang sama tapi parameternya berbeda (bisa jumlah atau tipe datanya). → nama method sama, beda parameter (terjadi di satu class).

   sedangkan Overriding terjadi di antara class induk dan class anak (subclass), ketika method di subclass menulis ulang method dari superclass supaya punya perilaku yang berbeda. → nama dan parameter sama, tapi isi (perilaku) berbeda (terjadi antar class).

2. Bagaimana Java menentukan method mana yang dipanggil dalam dynamic binding?  
   **Jawaban:** Dalam dynamic binding, Java menentukan method yang dipanggil berdasarkan tipe objek sebenarnya, bukan tipe referensinya, dan penentuannya terjadi saat program dijalankan.

3. Berikan contoh kasus polymorphism dalam sistem POS selain produk pertanian.  
   **Jawaban:** Contoh kasus polymorphism dalam sistem POS adalah pada toko elektronik yang menjual berbagai produk seperti laptop, smartphone, dan televisi.Semua produk itu punya data dasar yang sama misalnya kode, nama, dan harga, jadi bisa dibuat class induk `Produk`. Tapi tiap jenis produk punya ciri khas sendiri, jadi dibuat subclass seperti Laptop, Smartphone, dan Televisi.Dengan polymorphism, program bisa memperlakukan semua produk itu sebagai Produk, tapi tetap memanggil metode sesuai tipe aslinya.

---

## Referensi
- Liang, Y. D. *Introduction to Java Programming* (Bab 11).  
- Schildt, H. *Java: The Complete Reference* (Bab 8).  
- Oracle Docs: [Polymorphism](https://docs.oracle.com/javase/tutorial/java/IandI/polymorphism.html).  

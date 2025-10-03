# Laporan Praktikum Minggu 1 
Topik: Bab 1 – Pengenalan Paradigma dan Setup Proyek

---

## Identitas
- Nama  : Fauzatul Farhanah
- NIM   : 240202834
- Kelas : 3IKRA

---

## Tujuan
- Mahasiswa mampu mendefinisikan paradigma prosedural, OOP, dan fungsional.
- Mahasiswa mampu membandingkan kelebihan dan keterbatasan tiap paradigma.
- Mahasiswa mampu memberikan contoh program sederhana untuk masing-masing paradigma.
- Mahasiswa aktif dalam diskusi kelas (bertanya, menjawab, memberi opini).
  
---

## Dasar Teori
Paradigma pemrograman adalah cara pandang dalam menyusun program:  
- **Prosedural**: program dibangun sebagai rangkaian perintah (fungsi/prosedur).  
- **OOP (Object-Oriented Programming)**: program dibangun dari objek yang memiliki data (atribut) dan perilaku (method).  
- **Fungsional**: program dipandang sebagai pemetaan fungsi matematika, lebih menekankan ekspresi dan transformasi data.  

Dalam konteks Agri-POS, OOP membantu memodelkan entitas nyata seperti Produk, Transaksi, dan Pembayaran sebagai objek. Dengan demikian, sistem lebih mudah dikembangkan dan dipelihara.  

---

## Langkah Praktikum
1. Melakukan setup project Java di folder `praktikum/week1-setup-hello-pos/`.
2. Membuat 3 file program Java:
   - `HelloProcedural.java`
   - `HelloOOP.java`
   - `HelloFunctional.java`
3. Menjalankan program satu per satu untuk memastikan hasil output sesuai yang diharapkan.
4. Melakukan commit dan push ke GitHub dengan pesan: `git commit -m "week1-setup-hello-pos: add HelloProcedural, HelloOOP, HelloFunctional"`


---

## Kode Program  
1. Prosedural
```
// HelloProcedural.java
public class HelloProcedural {
    public static void main(String[] args) {
        // Paradigma prosedural: langsung instruksi urut
        String nama = "Fauzatul Farhanah";
        String nim = "240202834";
        System.out.println("Hello World, I am " + nama + "-" + nim);
    }
}

```
2. OOP
```
// HelloOOP.java
class Person {
    private String name;
    private String nim;

    public Person(String name, String nim) {
        this.name = name;
        this.nim = nim;
    }

    public void sayHello() {
        System.out.println("Hello World, I am " + name + "-" + nim);
    }
}

public class HelloOOP {
    public static void main(String[] args) {
        Person me = new Person("Fauzatul Farhanah", "240202834");
        me.sayHello();
    }
}
```
3. Fungsional
```
// HelloFunctional.java
import java.util.function.BiConsumer;

public class HelloFunctional {
    public static void main(String[] args) {
        // Paradigma fungsional: pakai fungsi murni (lambda)
        BiConsumer<String, String> sayHello = (name, nim) -> 
            System.out.println("Hello World, I am " + name + "-" + nim);

        sayHello.accept("Fauzatul Farhanah", "240202834");
    }
}
```

## Hasil Eksekusi

1. Paradigma Procedural
   ---
   <img width="956" height="799" alt="Screenshot (731)" src="https://github.com/user-attachments/assets/ce3122ed-3fa4-40c6-acc4-56f74b34a8b3" />
   ---
3. Paradigma OOP
   ---
   <img width="1194" height="998" alt="Screenshot (733)" src="https://github.com/user-attachments/assets/28899983-fecb-40bc-a259-09d7dfd4c548" />
   ---
4. Paradigma Functional
   ---
   <img width="1203" height="973" alt="Screenshot (734)" src="https://github.com/user-attachments/assets/d63f936c-463e-47ed-83ad-4ab6a3a2d205" />
   ---

   
)
---

## Analisis
(
- Jelaskan bagaimana kode berjalan.
  1. Procedural
     - Program dimulai dari `main(String[] args)` (titik masuk utama di Java).
     - Variabel `nama` dan `nim` disiapkan untuk menyimpan data mahasiswa.
     - `System.out.println()` langsung mencetak kalimat gabungan string dan variabel.
     - Semua instruksi dieksekusi berurutan tanpa membuat class atau method tambahan.
       
  2. OOP
     - Program dimulai dari method `main()` di class `HelloOOP`.
     - Di dalam main, dibuat objek baru dari class `Person` dengan perintah `Person me = new Person("Fauzatul Farhanah", "240202834");`, yang memanggil constructor `Person(String name, String nim)` untuk mengisi data `name` dan `nim`.
     - Ketika objek `me` sudah ada, program melanjutkan dengan menjalankan `me.sayHello();` ketika method `sayHello()` dipanggil, lalu program mencetak output ke layar.
       
  3. Functional
     - Program dimulai dari method `main()`.
     - Di dalamnya dibuat fungsi lambda bernama `sayHello` dengan tipe `BiConsumer<String, String>` yang berarti fungsi ini menerima dua inputan (`string name` dan `string nim`) kemudian mencetak teks.
     - Setelah fungsi tersebut dibuat, program memanggilnya dengan perintah `sayHello.accept("Fauzatul Farhanah", "240202834");`.
     - Ketika dipanggil, fungsi lambda langsung dijalankan dan menampilkan output.

- Apa perbedaan pendekatan minggu ini dibanding minggu sebelumnya.
  pada minggu ini, kita belajar membandingkan tiga paradigma pemrograman yang berbeda yaitu :
  1. Procedural → kode berjalan urut, sederhana, langsung ditulis di `main()`.
  2. OOP (Object-Oriented Programming) → program dibuat lebih terstruktur dengan memanfaatkan class dan object.
  3. Functional → program dibuat dengan memanfaatkan fungsi murni (lambda), lebih ringkas dan deklaratif.
  
  jadi, pada minggu ini tidak hanya sekadar membuat program, tetapi juga memahami paradigma dalam menulis program, sehingga kita dapat menentukan metode yang tepat sesuai kebutuhan.
  
- Kendala yang dihadapi dan cara mengatasinya
  Sempat error karena struktur package src/main/java/com/upb/agripos.
  Solusi: memastikan package sesuai dengan folder, jadi program bisa dikompilasi tanpa error.
)
---

## Kesimpulan
- Paradigma procedural menekankan instruksi berurutan, sederhana, dan mudah dipahami, tetapi kurang terstruktur untuk program besar.
- Paradigma OOP membuat program lebih terorganisir melalui penggunaan class dan object, sehingga lebih mudah dikembangkan dan dioptimalkan.
- Paradigma functional menggunakan fungsi murni(lambda) dan ekspresi ringkas, cocok untuk pemrosesan data, tapi butuh pemahaman lebih dalam.
- Masing-masing paradigma memiliki kelebihan dan keterbatasan yang dapat dipilih sesuai kebutuhan.
  
---

## Quiz
1. Apakah OOP selalu lebih baik dari prosedural?
   **Jawaban:** Tidak, karena OOP lebih cocok untuk program besar dan kompleks, sedangkan prosedural lebih sesuai untuk program kecil dan sederhana.

2. Kapan functional programming lebih cocok digunakan dibanding OOP atau prosedural?
   **Jawaban:** Functional programming lebih cocok digunakan ketika program membutuhkan pemrosesan data yang kompleks, paralel, atau transformasi data yang ringkas dan deklaratif dibanding OOP atau prosedural.

3. Bagaimana paradigma (prosedural, OOP, fungsional) memengaruhi maintainability dan scalability aplikasi?
   **Jawaban:** Paradigma prosedural cenderung kurang mendukung maintainability dan scalability, OOP lebih memudahkan keduanya melalui struktur class dan object, sedangkan fungsional mendukung maintainability dan scalability lewat kode yang ringkas, modular, dan mudah diparalelkan

4. Mengapa OOP lebih cocok untuk mengembangkan aplikasi POS dibanding prosedural?
   **Jawaban:** OOP lebih cocok untuk mengembangkan aplikasi POS karena dapat merepresentasikan entitas nyata seperti produk, transaksi, dan pembayaran dalam bentuk class dan object, sehingga sistem lebih terstruktur, mudah dikembangkan, dan lebih efisien dalam management sistem dibandingkan paradigma prosedural.

5. Bagaimana paradigma fungsional dapat membantu mengurangi kode berulang (*boilerplate code*)?
   **Jawaban:** Dengan menggunakan fungsi murni dan ekspresi lambda, sehingga logika yang sama cukup ditulis sekali saja lalu dapat dipanggil kembali tanpa harus menyalin kode berulang - ulang.

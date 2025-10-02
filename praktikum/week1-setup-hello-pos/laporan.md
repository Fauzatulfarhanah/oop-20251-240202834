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
- 
---

## Dasar Teori
(Tuliskan ringkasan teori singkat (3–5 poin) yang mendasari praktikum.  
Contoh:  
1. Class adalah blueprint dari objek.  
2. Object adalah instansiasi dari class.  
3. Enkapsulasi digunakan untuk menyembunyikan data.)

---

## Langkah Praktikum
(Tuliskan Langkah-langkah dalam prakrikum, contoh:
1. Langkah-langkah yang dilakukan (setup, coding, run).  
2. File/kode yang dibuat.  
3. Commit message yang digunakan.)

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

1. Paradigma Prosedural
   ---
   <img width="956" height="799" alt="Screenshot (731)" src="https://github.com/user-attachments/assets/ce3122ed-3fa4-40c6-acc4-56f74b34a8b3" />
   ---
3. Paradigma OOP
   ---
   <img width="1194" height="998" alt="Screenshot (733)" src="https://github.com/user-attachments/assets/28899983-fecb-40bc-a259-09d7dfd4c548" />
   ---
4. Paradigma Fungsional
   ---
   <img width="1203" height="973" alt="Screenshot (734)" src="https://github.com/user-attachments/assets/d63f936c-463e-47ed-83ad-4ab6a3a2d205" />
   ---

   
)
---

## Analisis
(
- Jelaskan bagaimana kode berjalan.  
- Apa perbedaan pendekatan minggu ini dibanding minggu sebelumnya.  
- Kendala yang dihadapi dan cara mengatasinya.  
)
---

## Kesimpulan
(Tuliskan kesimpulan dari praktikum minggu ini.  
Contoh: *Dengan menggunakan class dan object, program menjadi lebih terstruktur dan mudah dikembangkan.*)

---

## Quiz
1. Apakah OOP selalu lebih baik dari prosedural?
   **Jawaban:** 

2. Kapan functional programming lebih cocok digunakan dibanding OOP atau prosedural?
   **Jawaban:** …

3. Bagaimana paradigma (prosedural, OOP, fungsional) memengaruhi maintainability dan scalability aplikasi?
   **Jawaban:** …

4. Mengapa OOP lebih cocok untuk mengembangkan aplikasi POS dibanding prosedural?
   **Jawaban:** …

5. Bagaimana paradigma fungsional dapat membantu mengurangi kode berulang (*boilerplate code*)?
   **Jawaban:** …

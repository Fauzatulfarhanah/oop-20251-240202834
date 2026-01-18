# Laporan Praktikum Minggu 9
Topik: [Exception Handling, Custom Exception, dan Penerapan Design Pattern]

## Identitas
- Nama  : [Fauzatul Farhanah]
- NIM   : [240202834]
- Kelas : [3IKRA]

---

## Tujuan
Mahasiswa mampu:
1. Menjelaskan perbedaan antara error dan exception.
2. Mengimplementasikan try–catch–finally dengan tepat.
3. Membuat custom exception sesuai kebutuhan program.
4. Mengintegrasikan exception handling ke dalam aplikasi sederhana (kasus keranjang belanja).
5. (Opsional) Menerapkan design pattern sederhana (Singleton/MVC) dan unit testing dasar.

---

## Dasar Teori
1. Error dan Exception adalah kondisi kesalahan dalam program, namun exception masih dapat ditangani oleh program menggunakan mekanisme tertentu.
2. Exception Handling dilakukan menggunakan struktur try, catch, dan finally untuk mencegah program berhenti secara tiba-tiba.
3. Custom Exception adalah exception yang dibuat sendiri oleh programmer sesuai dengan kebutuhan logika program.
4. Throw dan Throws digunakan untuk melempar dan mendeklarasikan exception dari suatu metode.
5. Penerapan exception membantu program menjadi lebih aman, terkontrol, dan mudah dipahami.

---

## Langkah Praktikum
Langkah-langkah yang dilakukan pada praktikum ini adalah sebagai berikut:
1. Membuat project Java dengan package com.upb.agripos.
2. Membuat beberapa custom exception, yaitu InvalidQuantityException, ProductNotFoundException, InsufficientStockException, dan OutOfStockException.
3. Membuat class Product sebagai model data produk yang memiliki atribut kode, nama, harga, dan stok.
4. Membuat class ShoppingCart untuk mengelola keranjang belanja menggunakan Map serta menerapkan validasi menggunakan exception.
5. Mengimplementasikan try-catch pada class MainExceptionDemo untuk menangani kesalahan yang terjadi.
6. Menjalankan program untuk melihat hasil eksekusi dan pesan kesalahan.
7. Melakukan commit dan push ke repository GitHub.
---

## Kode Program
(Tuliskan kode utama yang dibuat,
1. Kode Utama
```java
package main.java.com.upb.agripos;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {

    private final Map<Product, Integer> items = new HashMap<>();

    // tambah produk
    public void addProduct(Product product, int qty) 
        throws InvalidQuantityException, OutOfStockException {

        if (qty <= 0) {
            throw new InvalidQuantityException(
                "Quantity harus lebih dari 0."
            );
        }

        // menambahkan pengecekan supaya OutOfStockException bisa dilempar
    if (product.getStock() == 0) {
        throw new OutOfStockException("Produk " + product.getName() + " sedang habis.");
    }
    

        items.put(product, items.getOrDefault(product, 0) + qty);
    }

    // hapus produk
    public void removeProduct(Product product)
            throws ProductNotFoundException {

        if (!items.containsKey(product)) {
            throw new ProductNotFoundException(
                "Produk tidak ada dalam keranjang."
            );
        }

        items.remove(product);
    }

    // checkout
    public void checkout()
            throws InsufficientStockException {

        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int qty = entry.getValue();

            if (product.getStock() < qty) {
                throw new InsufficientStockException(
                    "Stok tidak cukup untuk produk: " + product.getName()
                );
            }
        }

        // jika semua stok cukup
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            entry.getKey().reduceStock(entry.getValue());
        }

        items.clear();
    }
  

}

```
---
## Hasil Eksekusi
( 
![Screenshot hasil](![alt text](image-1.png)))
)
---

## Analisis
(
- Jelaskan bagaimana kode berjalan. 
jawab : Program berjalan dengan memeriksa setiap kondisi yang berpotensi menimbulkan kesalahan sebelum proses dilanjutkan. Apabila terjadi kesalahan, program akan melempar custom exception yang kemudian ditangkap pada class ''MainExceptionDemo''. 
- Apa perbedaan pendekatan minggu ini dibanding minggu sebelumnya.
jawab : Perbedaan pendekatan pada praktikum minggu ini dengan Week 7 yaitu :
a. Week 7 berfokus pada penggunaan Collection (ArrayList/Map) untuk menyimpan dan mengelola data.
b. Week 9 berfokus pada validasi dan penanganan kesalahan menggunakan exception.
- Kendala yang dihadapi dan cara mengatasinya.  
jawab : Kendala yang dihadapi adalah memahami kapan exception harus dilempar dan ditangani. Kendala tersebut diatasi dengan mempelajari kembali alur logika program dan menyesuaikan jenis exception dengan kesalahan yang terjadi.
)

---

## Kesimpulan
Berdasarkan praktikum Week 9 yang telah dilakukan, dapat disimpulkan bahwa penerapan exception handling dan custom exception sangat membantu dalam menangani kesalahan secara terstruktur. Dengan adanya exception, program Agri-POS menjadi lebih aman, tidak mudah mengalami crash, dan memberikan pesan kesalahan yang jelas kepada pengguna

---

## Quiz
1. Jelaskan perbedaan error dan exception.
**Jawab** : Error adalah kesalahan serius yang terjadi pada sistem dan umumnya tidak dapat ditangani oleh program, contohnya 'OutOfMemoryError'.
Exception adalah kesalahan yang terjadi saat program berjalan akibat kondisi tertentu dan masih dapat ditangani oleh program menggunakan mekanisme 'try–catch'.

2. Apa fungsi 'finally' dalam blok 'try–catch–finally'?
**Jawab** : Blok 'finally' berfungsi untuk menjalankan kode yang selalu dieksekusi, baik terjadi exception maupun tidak.
Biasanya digunakan untuk proses pembersihan sumber daya, seperti menutup file atau koneksi.

3. Mengapa custom exception diperlukan?
**Jawab** : Custom exception diperlukan supaya program dapat menangani kesalahan sesuai dengan kebutuhan bisnis, memberikan pesan kesalahan yang lebih jelas, serta membuat kode lebih terstruktur dan mudah dipahami dibandingkan menggunakan exception bawaan secara umum.

4. Berikan contoh kasus bisnis dalam POS yang membutuhkan custom exception.
**Jawab** : Contoh kasus bisnis dalam sistem POS adalah penambahan produk dengan jumlah pembelian tidak valid (≤ 0) atau penambahan produk yang stoknya habis.
Kondisi tersebut dapat ditangani menggunakan custom exception seperti 'InvalidQuantityException' atau 'OutOfStockException' agar sistem dapat menampilkan pesan kesalahan yang sesuai.



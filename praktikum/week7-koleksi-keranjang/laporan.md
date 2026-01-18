# Laporan Praktikum Minggu 7 
Topik: [Collections dan Implementasi Keranjang Belanja]

## Identitas
- Nama  : [Fauzatul Farhanah]
- NIM   : [240202834]
- Kelas : [3IKRA]

---

## Tujuan
1. Menjelaskan konsep collection dalam Java (List, Map, Set).
2. Menggunakan ArrayList untuk menyimpan dan mengelola objek.
3. Mengimplementasikan Map atau Set sesuai kebutuhan pengelolaan data.
4. Melakukan operasi dasar pada collection: tambah, hapus, dan hitung total.
5. Menganalisis efisiensi penggunaan collection dalam konteks sistem Agri-POS.
---

## Dasar Teori
### 1. Collections Framework

Java Collections Framework menyediakan struktur data untuk mengelola objek secara dinamis dan efisien.

Struktur utama:

- List (implementasi: ArrayList) — Terurut, dapat menyimpan elemen duplikat.
- Map (implementasi: HashMap) — Menyimpan pasangan key–value, akses cepat berdasarkan key.
- Set (implementasi: HashSet) — Tidak menerima duplikat dan tidak mempertahankan urutan.

---

### 2. Studi Kasus: Keranjang Belanja Agri-POS

Keranjang belanja harus dapat:

- Menambahkan produk
- Menghapus produk
- Menampilkan isi keranjang
- Menghitung total nilai transaksi
- Menangani jumlah (quantity) menggunakan Map

Kasus ini mencerminkan penggunaan struktur data dalam aplikasi nyata seperti POS.

---

## Langkah Praktikum
(Tuliskan Langkah-langkah dalam prakrikum, contoh:
1. Langkah-langkah yang dilakukan (setup, coding, run).  
2. File/kode yang dibuat.  
3. Commit message yang digunakan.)

---

## Kode Program
### 1. Membuat Class Product

```java
package com.upb.agripos;

public class Product {
    private final String code;
    private final String name;
    private final double price;

    public Product(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public double getPrice() { return price; }
}
```

## 2. Implementasi Keranjang dengan ArrayList

```java
package com.upb.agripos;

import java.util.ArrayList;

public class ShoppingCart {
    private final ArrayList<Product> items = new ArrayList<>();

    public void addProduct(Product p) { items.add(p); }
    public void removeProduct(Product p) { items.remove(p); }

    public double getTotal() {
        double sum = 0;
        for (Product p : items) {
            sum += p.getPrice();
        }
        return sum;
    }

    public void printCart() {
        System.out.println("Isi Keranjang:");
        for (Product p : items) {
            System.out.println("- " + p.getCode() + " " + p.getName() + " = " + p.getPrice());
        }
        System.out.println("Total: " + getTotal());
    }
}
```

## 3. Main Program

```java
package com.upb.agripos;

public class MainCart {
    public static void main(String[] args) {
        System.out.println("Hello, I am [Nama]-[NIM] (Week7)");

        Product p1 = new Product("P01", "Beras", 50000);
        Product p2 = new Product("P02", "Pupuk", 30000);

        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(p1);
        cart.addProduct(p2);
        cart.printCart();

        cart.removeProduct(p1);
        cart.printCart();
    }
}
```

## 4. Implementasi Alternatif Menggunakan Map (Dengan Quantity)

```java
package com.upb.agripos;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCartMap {
    private final Map<Product, Integer> items = new HashMap<>();

    public void addProduct(Product p) { items.put(p, items.getOrDefault(p, 0) + 1); }

    public void removeProduct(Product p) {
        if (!items.containsKey(p)) return;
        int qty = items.get(p);
        if (qty > 1) items.put(p, qty - 1);
        else items.remove(p);
    }

    public double getTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public void printCart() {
        System.out.println("Isi Keranjang (Map):");
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            System.out.println("- " + e.getKey().getCode() + " " + e.getKey().getName() + " x" + e.getValue());
        }
        System.out.println("Total: " + getTotal());
    }
}
```
---

## Hasil Eksekusi
![alt text](<Screenshot 2026-01-18 095759.png>)
---

## Analisis
1. Bagaimana kode berjalan
**jawab** : Program dijalankan melalui class MainCart yang memiliki 'method main()' sebagai titik awal eksekusi program.
- Program menampilkan identitas mahasiswa sebagai penanda praktikum Week 7.
- Object Product dibuat untuk merepresentasikan data produk yang terdiri dari kode, nama, dan harga.
- Object 'ShoppingCart' dibuat untuk menyimpan produk menggunakan struktur data 'ArrayList'.
- Produk ditambahkan ke dalam keranjang menggunakan method 'addProduct()'.
- Isi keranjang ditampilkan ke layar menggunakan method 'printCart()', termasuk total harga yang dihitung melalui method 'getTotal()'.
- Salah satu produk dihapus dari keranjang menggunakan method 'removeProduct()', kemudian isi keranjang ditampilkan kembali.

Selain itu, ada implementasi alternatif 'ShoppingCartMap' yang menggunakan Map untuk menyimpan produk beserta jumlah (quantity), sehingga memungkinkan pengelolaan produk yang sama dengan jumlah lebih dari satu.

2. Perbedaan pendekatan minggu ini dibanding minggu sebelumnya
Pada praktikum minggu sebelumnya, fokus utama adalah perancangan sistem menggunakan UML (Use Case Diagram, Class Diagram), Prinsip SOLID untuk memastikan desain sistem bersifat modular, fleksibel, dan mudah dikembangkan.
Pendekatan tersebut masih berada pada tahap desain konseptual, yaitu bagaimana sistem dirancang sebelum diimplementasikan dalam bentuk kode.
Sedangkan pada praktikum minggu ini, ada di tahap implementasi, yaitu: 
- Menerjemahkan desain UML ke dalam class Java
- Mengimplementasikan prinsip Single Responsibility Principle (SRP) dengan memisahkan class Product, ShoppingCart, dan MainCart
- Menggunakan Java Collections (List dan Map) untuk mengelola data secara dinamis

3. Kendala yang dihadapi dan cara mengatasinya
**jawab**
Alhamdulillah tidak ada
---

## Kesimpulan
Berdasarkan praktikum yang telah dilakukan pada Bab 7 mengenai Collections dan Implementasi Keranjang Belanja, dapat disimpulkan bahwa Java Collections Framework sangat membantu dalam pengelolaan data secara dinamis dan efisien. Struktur data seperti List, Map, dan Set memiliki karakteristik dan kegunaan yang berbeda sesuai dengan kebutuhan sistem.
Penggunaan ArrayList pada implementasi keranjang belanja Agri-POS terbukti efektif untuk menyimpan dan mengelola daftar produk secara sederhana, termasuk operasi menambah produk, menghapus produk, dan menghitung total transaksi. Sementara itu, penggunaan Map (HashMap) memberikan solusi yang lebih optimal ketika sistem membutuhkan pengelolaan jumlah (quantity) produk, karena dapat menghindari duplikasi data dan meningkatkan efisiensi.

---

## Quiz
1. Perbedaan mendasar antara List, Map, dan Set
- List adalah struktur data yang menyimpan elemen secara terurut dan memungkinkan data duplikat. Contohnya ArrayList.
- Map adalah struktur data yang menyimpan pasangan key–value, di mana setiap key bersifat unik dan digunakan untuk mengakses value. Contohnya 'HashMap'.
- Set adalah struktur data yang menyimpan elemen unik dan tidak mengizinkan duplikasi data. Contohnya 'HashSet'.

2. Mengapa ArrayList cocok digunakan untuk keranjang belanja sederhana?
ArrayList cocok digunakan untuk keranjang belanja sederhana karena mudah diimplementasikan, mendukung penyimpanan data secara terurut, serta memperbolehkan data duplikat. Hal ini sesuai untuk keranjang belanja yang hanya membutuhkan daftar produk tanpa pengelolaan jumlah (quantity) yang kompleks.

3. Bagaimana struktur Set mencegah duplikasi data?
Struktur Set mencegah duplikasi data dengan melakukan pengecekan kesamaan objek menggunakan metode 'equals()' dan 'hashCode()'. Jika data yang sama sudah ada di dalam Set, maka data tersebut tidak akan ditambahkan kembali.

4. Kapan sebaiknya menggunakan Map dibandingkan List? Jelaskan dengan contoh.
Map sebaiknya digunakan ketika data membutuhkan pasangan key–value, misalnya produk sebagai key dan jumlah produk sebagai value. Contohnya pada keranjang belanja yang menyimpan jumlah produk, HashMap<Product, Integer> lebih efisien dibandingkan List karena dapat langsung mengelola quantity tanpa menyimpan data produk yang sama secara berulang.

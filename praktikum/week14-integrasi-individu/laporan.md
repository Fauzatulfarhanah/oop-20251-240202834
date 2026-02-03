# Laporan Praktikum Minggu 14 — Integrasi Individu
Topik : Integrasi Individu (OOP + Database + GUI)

## Identitas
**Nama**    : Fauzatul Farhanah 
**NIM**     : 240202834 
**Kelas**   : 3IKRA
---

## Tujuan
1. Mengintegrasikan konsep OOP (Bab 1–5) ke dalam satu aplikasi yang utuh.
2. Mengimplementasikan rancangan UML + SOLID (Bab 6) menjadi kode nyata.
3. Mengintegrasikan Collections + Keranjang (Bab 7) ke alur aplikasi.
4. Menerapkan exception handling (Bab 9) untuk validasi dan error flow.
5. Menerapkan pattern + unit testing (Bab 10) pada bagian yang relevan.
6. Menghubungkan aplikasi dengan database via DAO + JDBC (Bab 11).
7. Menyajikan aplikasi berbasis JavaFX (Bab 12–13) yang terhubung ke backend.
---

## Dasar Teori

### 1. MVC + DAO Pattern
Model-View-Controller memisahkan tanggung jawab antara data (`Model`), antarmuka pengguna (`View`), dan logika kontrol (`Controller`). DAO menambahkan lapisan khusus untuk akses database sehingga logika bisnis tidak tercampur dengan query SQL.

### 2. Collections Framework
Java menyediakan kerangka kerja koleksi yang mencakup interface seperti `List` dan implementasinya seperti `ArrayList`. Pada praktikum ini, `List<CartItem>` digunakan untuk menyimpan item keranjang belanja secara dinamis.

### 3. Unit Testing — JUnit 5
JUnit 5 memungkinkan verifikasi otomatis bahwa setiap metode bekerja sesuai ekspektasi tanpa menjalankan seluruh aplikasi. Anotasi `@Test` menandai metode pengujian, `@BeforeEach` menjalankan persiapan sebelum setiap test, dan metode `assert` memvalidasi hasil.

### 4. Exception Handling
Penanganan pengecualian menggunakan `try-catch` dan `throw` memungkinkan program menangani kesalahan secara elegan. `IllegalArgumentException` digunakan untuk memvalidasi input di layer Service.

### 5. JavaFX TabPane
`TabPane` memungkinkan tampilan berbagai halaman dalam satu window menggunakan tab. Digunakan dua tab: *Kelola Produk* untuk CRUD dan *Kasir* untuk operasi keranjang.

### 6. JDBC — PreparedStatement
JDBC adalah API standar Java untuk terhubung dengan database relasional. `PreparedStatement` mengeksekusi query SQL dengan parameter yang aman dari SQL injection.

### 7. SOLID — DIP
View tidak langsung mengakses DAO. Alur yang benar: `View → Controller → Service → DAO → Database`. Service bergantung pada **interface** `ProductDAO`, bukan implementasi konkret.

---

## Struktur Proyek

```
week14-integrasi-individu/
│
├── src/
│   ├── main/java/com/upb/agripos/
│   │   ├── model/
│   │   │   ├── Product.java          ← Entity produk
│   │   │   ├── CartItem.java         ← Item dalam keranjang
│   │   │   └── Cart.java             ← Koleksi keranjang (List<CartItem>)
│   │   ├── dao/
│   │   │   ├── ProductDAO.java       ← Interface kontrak DAO
│   │   │   └── JdbcProductDAO.java   ← Implementasi JDBC
│   │   ├── service/
│   │   │   ├── ProductService.java   ← Validasi + logika produk
│   │   │   └── CartService.java      ← Validasi + logika keranjang
│   │   ├── controller/
│   │   │   └── PosController.java    ← Orchestrator View ↔ Service
│   │   ├── view/
│   │   │   └── PosView.java          ← GUI TabPane (2 tab)
│   │   └── AppJavaFX.java            ← Entry point & wiring
│   │
│   └── test/java/com/upb/agripos/
│       └── CartServiceTest.java      ← 6 unit test JUnit 5
│
├── database/
│   └── setup_database.sql            ← DDL + 10 sample produk
├── screenshots/                      ← Screenshot hasil eksekusi
├── pom.xml                           ← Maven config
└── laporan.md                       
```

---

## Langkah Praktikum

### Langkah 1 — Setup Database PostgreSQL

```bash
psql -U postgres -f database/setup_database.sql
```

Script membuat tabel `products` dengan kolom `code`, `name`, `price`, `stock` dan menyisipkan 10 produk pertanian sebagai data awal.

### Langkah 2 — Konfigurasi Proyek Maven

`pom.xml` dikonfigurasi dengan dependensi:

| Dependensi | Versi | Fungsi |
|:---|:---|:---|
| `javafx-controls` | 17.0.2 | GUI |
| `postgresql` | 42.5.1 | Koneksi database |
| `junit-jupiter-api` | 5.9.2 | Unit testing |
| `junit-jupiter-engine` | 5.9.2 | Test runner |

Plugin: `javafx-maven-plugin` untuk run aplikasi, `maven-surefire-plugin` untuk run test.

### Langkah 3 — Implementasi Layer Model

Tiga class model dibuat:

| Class | Peran |
|:---|:---|
| `Product.java` | Entity produk: `code`, `name`, `price`, `stock` + getter/setter + `toString()` |
| `CartItem.java` | Wraps `Product` + `quantity`, memiliki `getSubtotal()` |
| `Cart.java` | Mengelola `List<CartItem>`: tambah, hapus, hitung total, clear |

### Langkah 4 — Implementasi Layer DAO

| Class | Peran |
|:---|:---|
| `ProductDAO.java` | Interface — kontrak: `insert`, `findByCode`, `findAll`, `update`, `delete` |
| `JdbcProductDAO.java` | Implementasi — eksekusi SQL via `PreparedStatement` |

### Langkah 5 — Implementasi Layer Service

| Class | Peran |
|:---|:---|
| `ProductService.java` | Validasi: kode/nama tidak kosong, harga/stok tidak negatif, produk ada saat hapus |
| `CartService.java` | Validasi: produk tidak null, quantity > 0, quantity ≤ stok |

### Langkah 6 — Implementasi Controller

`PosController.java` mengorchestrasi dua domain sekaligus — **produk** dan **keranjang** — sambil menampilkan `Alert` dialog sebagai feedback ke pengguna.

### Langkah 7 — Implementasi View (TabPane)

`PosView.java` membangun GUI dengan dua tab:

| Tab | Komponen |
|:---|:---|
| **Kelola Produk** | 4 `TextField` (form), 3 `Button` (Tambah/Hapus/Refresh), `TableView` 4 kolom |
| **Kasir** | `ComboBox` produk, `Spinner` quantity, `ListView` keranjang, `Label` total |

### Langkah 8 — Implementasi Main Application

`AppJavaFX.java` sebagai entry point: inisialisasi koneksi JDBC → buat DAO → Service → Controller → View secara berurutan sesuai dependency chain.

### Langkah 9 — Menulis Unit Test

`CartServiceTest.java` berisi **6 test case**:

| # | Test | Skenario |
|:-:|:---|:---|
| 1 | `testAddToCart_Success` | Tambah 1 produk, validasi jumlah dan total |
| 2 | `testAddToCart_MultipleProducts` | Tambah 2 produk berbeda |
| 3 | `testAddToCart_InvalidQuantity` | Quantity = 0 → exception |
| 4 | `testAddToCart_ExceedStock` | Quantity > stok → exception |
| 5 | `testAddToCart_NullProduct` | Produk null → exception |
| 6 | `testClearCart` | Clear keranjang, validasi kosong |

### Langkah 10 — Commit

```bash
git add .
git commit -m "week14-integrasi-individu"
```

---

## Kode Program

### `Cart.java` — Collections & Logika Keranjang

```java
package com.upb.agripos.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity harus lebih dari 0");
        }

        // Jika produk sudah ada di keranjang → tambah quantity-nya
        for (CartItem item : items) {
            if (item.getProduct().getCode().equals(product.getCode())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }

        items.add(new CartItem(product, quantity));
    }

    public void removeItem(String productCode) {
        // Lambda + removeIf dari Collections
        items.removeIf(item -> item.getProduct().getCode().equals(productCode));
    }

    public double getTotal() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public List<CartItem> getItems() { return new ArrayList<>(items); }
    public int getTotalItems()       { return items.size(); }
    public void clear()              { items.clear(); }
    public boolean isEmpty()         { return items.isEmpty(); }
}
```

### `CartService.java` — Business Logic & Validasi

```java
package com.upb.agripos.service;

import com.upb.agripos.model.Cart;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;
import java.util.List;

public class CartService {
    private final Cart cart;

    public CartService() {
        this.cart = new Cart();
    }

    public void addToCart(Product product, int quantity) {
        if (product == null) {
            throw new IllegalArgumentException("Produk tidak boleh null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity harus lebih dari 0");
        }
        if (quantity > product.getStock()) {
            throw new IllegalArgumentException("Stok tidak mencukupi");
        }

        cart.addItem(product, quantity);
    }

    public void removeFromCart(String productCode) { cart.removeItem(productCode); }
    public List<CartItem> getCartItems()            { return cart.getItems(); }
    public double calculateTotal()                  { return cart.getTotal(); }
    public int getTotalItems()                      { return cart.getTotalItems(); }
    public void clearCart()                         { cart.clear(); }
    public boolean isCartEmpty()                    { return cart.isEmpty(); }
}
```

### `JdbcProductDAO.java` — Akses Database

```java
package com.upb.agripos.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.upb.agripos.model.Product;

public class JdbcProductDAO implements ProductDAO {
    private final Connection connection;

    public JdbcProductDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Product p) throws Exception {
        String sql = "INSERT INTO products(code, name, price, stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getCode());
            ps.setString(2, p.getName());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getStock());
            ps.executeUpdate();
        }
    }

    @Override
    public List<Product> findAll() throws Exception {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY code";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Product(
                    rs.getString("code"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                ));
            }
        }
        return list;
    }

    @Override
    public void delete(String code) throws Exception {
        String sql = "DELETE FROM products WHERE code=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            ps.executeUpdate();
        }
    }

    // findByCode() dan update() juga tersedia
}
```

### `CartServiceTest.java` — Unit Test JUnit 5

```java
package com.upb.agripos;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTest {
    private CartService cartService;
    private Product product1, product2;

    @BeforeEach
    public void setUp() {
        cartService = new CartService();
        product1 = new Product("P001", "Benih Padi", 25000, 100);
        product2 = new Product("P002", "Pupuk Urea", 35000, 50);
    }

    @Test
    public void testAddToCart_Success() {
        cartService.addToCart(product1, 5);
        assertEquals(1, cartService.getTotalItems());
        assertEquals(125000, cartService.calculateTotal(), 0.01);
    }

    @Test
    public void testAddToCart_MultipleProducts() {
        cartService.addToCart(product1, 3);
        cartService.addToCart(product2, 2);
        assertEquals(2, cartService.getTotalItems());
        assertEquals(145000, cartService.calculateTotal(), 0.01);
    }

    @Test
    public void testAddToCart_InvalidQuantity() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            cartService.addToCart(product1, 0);
        });
        assertEquals("Quantity harus lebih dari 0", ex.getMessage());
    }

    @Test
    public void testAddToCart_ExceedStock() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            cartService.addToCart(product1, 150);
        });
        assertEquals("Stok tidak mencukupi", ex.getMessage());
    }

    @Test
    public void testAddToCart_NullProduct() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            cartService.addToCart(null, 5);
        });
        assertEquals("Produk tidak boleh null", ex.getMessage());
    }

    @Test
    public void testClearCart() {
        cartService.addToCart(product1, 5);
        cartService.clearCart();
        assertEquals(0, cartService.getTotalItems());
        assertTrue(cartService.isCartEmpty());
    }
}
```

### `AppJavaFX.java` — Entry Point & Wiring

```java
package com.upb.agripos;

import com.upb.agripos.controller.PosController;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.JdbcProductDAO;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.service.CartService;
import com.upb.agripos.view.PosView;
import javafx.application.Application;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DriverManager;

public class AppJavaFX extends Application {
    private Connection connection;

    @Override
    public void start(Stage primaryStage) {
        System.out.println("=======================================");
        System.out.println("Hello World, I am Fauzatul Farhanah-240202834");
        System.out.println("Week 14 - Integrasi Individu");
        System.out.println("=======================================\n");

        try {
            connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/agripos", "postgres", "farhanah");

            ProductDAO     productDAO     = new JdbcProductDAO(connection);
            ProductService productService = new ProductService(productDAO);
            CartService    cartService    = new CartService();
            PosController  posController  = new PosController(productService, cartService);

            PosView posView = new PosView(posController);
            posView.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            if (connection != null && !connection.isClosed()) connection.close();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void main(String[] args) { launch(args); }
}
```

---

## Hasil Eksekusi

### Tampilan Aplikasi Utama
![alt text](<Screenshot (1346).png>)
![alt text](<Screenshot 2026-02-03 181116.png>)

Aplikasi menampilkan dua tab. Tab **Kelola Produk** berisi form input (Kode, Nama, Harga, Stok), tiga tombol berwarna, dan `TableView` berisi daftar produk dari database. Tab **Kasir** berisi `ComboBox` produk, `Spinner` quantity, `ListView` keranjang, dan `Label` total harga.

---

### Hasil Unit Test
![alt text](<Screenshot (1347).png>)

| Status | Test Case | Deskripsi |
|:-:|:---|:---|
| ✅ | `testAddToCart_Success` | Tambah produk berhasil, total benar |
| ✅ | `testAddToCart_MultipleProducts` | Dua produk, total gabungan benar |
| ✅ | `testAddToCart_InvalidQuantity` | Quantity 0 → exception terlempar |
| ✅ | `testAddToCart_ExceedStock` | Quantity > stok → exception terlempar |
| ✅ | `testAddToCart_NullProduct` | Null product → exception terlempar |
| ✅ | `testClearCart` | Keranjang berhasil dikosongkan |

Console menampilkan `%TESTC 6 v2` — 6 test dijalankan, setiap test menunjukkan pasangan `%TESTS` (start) dan `%TESTE` (end) tanpa error.

---

## Analisis

### Cara Kerja Aplikasi — Alur Data

```
[TAB 1: KELOLA PRODUK]

 User isi form & klik Tambah
         │
         ▼
 PosView (lambda event handler)
         │
         ▼
 PosController.addProduct()       ← parsing String → double/int
         │
         ▼
 ProductService.insert()          ← validasi input
         │
         ▼
 JdbcProductDAO.insert()          ← SQL INSERT via PreparedStatement
         │
         ▼
 Database PostgreSQL
         │
         ▼
 loadProductData()                ← refresh ObservableList
         │
         ▼
 TableView auto-refresh ✓


[TAB 2: KASIR]

 User pilih produk & quantity → klik Tambah ke Keranjang
         │
         ▼
 PosController.addToCart()
         │
         ▼
 CartService.addToCart()          ← validasi: null? qty>0? qty≤stok?
         │
         ▼
 Cart.addItem()                   ← tambah ke List<CartItem>
         │
         ▼
 updateCartDisplay()              ← refresh ListView + Label total ✓
```

### Perbedaan Minggu 13 vs Minggu 14

| Aspek | Minggu 13 | Minggu 14 |
|:---|:---|:---|
| **Halaman GUI** | 1 halaman (Kelola Produk) | 2 tab via `TabPane` |
| **Domain** | Produk saja | Produk **+** Keranjang |
| **Collections** | Belum eksplisit | `List<CartItem>` di `Cart` |
| **Unit Testing** | Belum ada | 6 test case JUnit 5 |
| **Controller** | 1 domain | Orchestrator 2 domain |
| **Folder `src/test`** | Belum ada | Terpisah dari `src/main` |

### Penerapan SOLID

**SRP** — setiap class memiliki satu tanggung jawab:
- `Product` / `Cart` → data
- `JdbcProductDAO` → query SQL
- `ProductService` / `CartService` → validasi & logika bisnis
- `PosController` → koordinasi
- `PosView` → tampilan

**DIP** — `ProductService` menerima `ProductDAO` (interface) melalui constructor, bukan `JdbcProductDAO` langsung. Implementasi DAO bisa diganti (misal ke Hibernate) tanpa mengubah Service.

### Kendala & Solusi

| # | Kendala | Solusi |
|:-:|:---|:---|
| 1 | Nama property di `PropertyValueFactory` tidak cocok dengan getter → kolom kosong | Cek manual: `"code"` → `getCode()`, `"name"` → `getName()` |
| 2 | Pesan exception di Service tidak match dengan yang diharapkan test → `assertEquals` gagal | Tulis test dulu, lalu sesuaikan pesan di Service (atau sebaliknya) |

### Traceability — Konsep dari Minggu ke Minggu

| Konsep | Pertama di Minggu | Implementasi di Week 14 |
|:---|:-:|:---|
| Class & Object | 2 | `Product`, `CartItem`, `Cart` |
| Inheritance & Polymorphism | 3–4 | Konsep OOP dipakai di semua class |
| Interface | 4 | `ProductDAO` sebagai kontrak DAO |
| Collections (`List`) | 7 | `Cart` menggunakan `List<CartItem>` |
| Exception Handling | 9 | `IllegalArgumentException` di Service |
| GUI JavaFX | 11–12 | `PosView` — `TabPane`, `TableView`, `ComboBox` |
| TableView + Lambda | 13 | Tab "Kelola Produk" |
| Unit Testing (baru) | **14** | `CartServiceTest` — 6 test case |

---

## Kesimpulan

1. Praktikum minggu 14 berhasil mengintegrasikan seluruh konsep OOP dari minggu 2–13 menjadi satu aplikasi **Agri-POS** yang utuh dan fungsional.

2. **Collections Framework** (`List<CartItem>`) membuktikan keranjang belanja dapat dikelola secara dinamis — tambah, hapus, dan iterasi berjalan efisien.

3. Pola **DAO** dengan interface `ProductDAO` + implementasi `JdbcProductDAO` memisahkan akses database dari logika bisnis secara bersih.

4. **Unit testing JUnit 5** memvalidasi logika `CartService` secara otomatis — 6 test case mencakup skenario sukses dan skenario error, semuanya **lulus**.

5. Arsitektur **View → Controller → Service → DAO** yang mengikuti DIP membuat setiap lapisan dapat dikembangkan secara independen.

6. **TabPane dua tab** memberikan UX yang lebih baik karena fitur "Kelola Produk" dan "Kasir" memiliki tanggung jawab berbeda dan diakses terpisah.

7. Validasi konsisten di layer Service memastikan data yang masuk ke database dan keranjang selalu valid.

8. Praktikum ini membuktikan konsep-konsep fundamental — enkapsulasi, interface, Collections, exception handling, dan design pattern — dapat dipadukan dalam satu sistem yang koheren dan profesional.

---

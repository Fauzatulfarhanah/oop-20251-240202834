# Laporan Week 12 - GUI Dasar JavaFX (Event-Driven Programming)

## Identitas
- **Nama**: Fauzatul Farhanah
- **NIM**: 240202834
- **Kelas**: 3IKRA
---

## 1. Tujuan Praktikum

Praktikum Week 12 bertujuan untuk:
1. Menjelaskan konsep event-driven programming pada JavaFX
2. Membangun antarmuka grafis sederhana untuk form input produk
3. Mengintegrasikan GUI dengan backend (DAO & Service) yang telah dibuat sebelumnya
4. Menerapkan arsitektur MVC pada aplikasi JavaFX
5. Merealisasikan artefak desain dari Bab 6 (UML + SOLID) ke dalam implementasi GUI

---

## 2. Deskripsi Tugas

Tugas ini adalah membuat aplikasi JavaFX untuk **Manajemen Produk Agri-POS** yang memiliki fitur:
- Form input dengan field: Kode Produk, Nama Produk, Harga, dan Stok
- Tombol "Tambah Produk" untuk menyimpan data ke database
- Tombol "Refresh List" untuk memperbarui daftar produk
- ListView untuk menampilkan daftar produk yang telah tersimpan
---

## 3. Arsitektur Aplikasi

Aplikasi menggunakan **arsitektur MVC (Model-View-Controller)** dengan tambahan layer **Service** dan **DAO**:

```
┌─────────────────────────────────────────────────┐
│                    View Layer                    │
│          (ProductFormView.java)                  │
│  - TextField (code, name, price, stock)          │
│  - Button (Tambah, Refresh)                      │
│  - ListView (daftar produk)                      │
└────────────────┬────────────────────────────────┘
                 │ Event Handler
                 ↓
┌─────────────────────────────────────────────────┐
│                Controller Layer                  │
│           (ProductController.java)               │
│  - add() : handle tombol Tambah                  │
│  - getAllProducts() : ambil semua data           │
└────────────────┬────────────────────────────────┘
                 │ Business Logic
                 ↓
┌─────────────────────────────────────────────────┐
│                 Service Layer                    │
│            (ProductService.java)                 │
│  - insert() : validasi + simpan                  │
│  - findAll() : ambil semua produk                │
└────────────────┬────────────────────────────────┘
                 │ Data Access
                 ↓
┌─────────────────────────────────────────────────┐
│                   DAO Layer                      │
│     (ProductDAO + ProductDAOImpl.java)           │
│  - insert() : SQL INSERT                         │
│  - findAll() : SQL SELECT                        │
└────────────────┬────────────────────────────────┘
                 │ JDBC
                 ↓
┌─────────────────────────────────────────────────┐
│              PostgreSQL Database                 │
│                 (agripos.products)               │
└─────────────────────────────────────────────────┘
```

---

## 4. Struktur Direktori

```
week12-gui-dasar/
 ├─ src/main/java/com/upb/agripos/
 │   ├─ model/
 │   │   └─ Product.java
 │   ├─ dao/
 │   │   ├─ ProductDAO.java
 │   │   └─ ProductDAOImpl.java
 │   ├─ service/
 │   │   └─ ProductService.java
 │   ├─ controller/
 │   │   └─ ProductController.java
 │   ├─ view/
 │   │   └─ ProductFormView.java
 │   └─ AppJavaFX.java
 ├─ screenshots/
 │   └─ gui_form_produk.png
 └─ laporan_week12.md
```

---

## 5. Tabel Traceability: Bab 6 → GUI Week 12

| Artefak Bab 6 | Referensi | Handler GUI | Controller/Service | DAO | Dampak UI/DB |
|---|---|---|---|---|---|
| **Use Case** | UC-01 Tambah Produk | Tombol "Tambah Produk" (`btnAdd.setOnAction`) | `ProductController.add()` → `ProductService.insert()` | `ProductDAOImpl.insert()` | UI: ListView bertambah <br> DB: INSERT ke tabel products |
| **Activity Diagram** | AD-01 Tambah Produk | Tombol "Tambah Produk" | 1. Validasi input di Controller <br> 2. ProductService.insert() <br> 3. Refresh ListView | `ProductDAOImpl.insert()` | Flow: Input → Validasi → Simpan → Tampil |
| **Sequence Diagram** | SD-01 Tambah Produk | `btnAdd` event | View → Controller → Service → DAO | DAO → PostgreSQL DB | Urutan: <br> 1. View.btnAdd <br> 2. Controller.add() <br> 3. Service.insert() <br> 4. DAO.insert() <br> 5. DB INSERT <br> 6. Refresh UI |
| **Class Diagram** | Entity: Product | - | Model: `Product.java` (code, name, price, stock) | - | Struktur data sesuai desain |
| **Class Diagram** | Controller | - | `ProductController.java` | - | Orchestrator antara View-Service |
| **Class Diagram** | DAO Pattern | - | `ProductService.java` | `ProductDAO` interface + `ProductDAOImpl` | Pemisahan logika akses data |
| **SOLID: SRP** | Single Responsibility | - | View: UI only <br> Controller: Logic <br> Service: Business <br> DAO: Data Access | Setiap class 1 tanggung jawab | Mudah maintenance |
| **SOLID: DIP** | Dependency Inversion | - | View TIDAK panggil DAO langsung, harus via Service | ProductDAO = interface (abstraksi) | View bergantung pada abstraksi |

---

## 6. Penerapan Event-Driven Programming

Aplikasi ini menggunakan **event-driven programming** pada JavaFX dengan event handler untuk tombol:

### Event Handler: Tombol Tambah Produk
```java
btnAdd.setOnAction(event -> {
    // 1. Ambil input dari TextField
    String code = txtCode.getText();
    String name = txtName.getText();
    String price = txtPrice.getText();
    String stock = txtStock.getText();

    // 2. Panggil controller (sesuai Sequence Diagram)
    controller.add(code, name, price, stock);

    // 3. Clear form
    clearForm();

    // 4. Refresh tampilan
    refreshProductList();
});
```

### Event Handler: Tombol Refresh
```java
btnRefresh.setOnAction(event -> {
    refreshProductList();
});
```

---

## 7. Alur Kerja Aplikasi (Flow)

### Flow: Tambah Produk (Mengikuti Activity Diagram)

1. **User Input**: User mengisi form (Kode, Nama, Harga, Stok)
2. **Event Trigger**: User klik tombol "Tambah Produk"
3. **Event Handler**: `btnAdd.setOnAction` terpanggil
4. **Controller Called**: `ProductController.add()` dipanggil
5. **Validasi Input**: 
   - Cek field kosong
   - Cek format angka (harga & stok)
   - Validasi di Service (harga/stok tidak negatif)
6. **Service Layer**: `ProductService.insert(product)` dipanggil
7. **DAO Layer**: `ProductDAOImpl.insert(product)` eksekusi SQL INSERT
8. **Database**: Data tersimpan di PostgreSQL
9. **Update UI**: 
   - Form di-clear
   - TableView di-refresh dengan data terbaru
10. **Feedback**: Alert dialog menampilkan status sukses/error

---

## 8. Kepatuhan terhadap SOLID Principles

### 1. **SRP (Single Responsibility Principle)**
- `ProductFormView`: Hanya mengurus tampilan UI
- `ProductController`: Hanya orchestrate logic
- `ProductService`: Hanya business logic & validasi
- `ProductDAO`: Hanya akses database

### 2. **OCP (Open/Closed Principle)**
- Interface `ProductDAO` memungkinkan penambahan implementasi baru tanpa ubah code

### 3. **LSP (Liskov Substitution Principle)**
- `ProductDAOImpl` dapat diganti dengan implementasi lain (misal: `ProductDAOFileImpl`)

### 4. **ISP (Interface Segregation Principle)**
- Interface `ProductDAO` hanya berisi method yang diperlukan

### 5. **DIP (Dependency Inversion Principle)** 
- `ProductFormView` TIDAK langsung panggil `ProductDAO`
- View → Controller → Service → DAO (melalui abstraksi)
- `ProductService` bergantung pada interface `ProductDAO`, bukan implementasi konkret

---

## 9. Fitur yang Diimplementasikan

✅ Form input dengan 4 TextField (Code, Name, Price, Stock)
✅ Tombol "Tambah Produk" dengan event handler
✅ Tombol "Refresh List" untuk update tampilan
✅ ListView untuk menampilkan daftar produk
✅ Validasi input (field kosong, format angka)
✅ Integrasi dengan ProductService & ProductDAO
✅ Alert dialog untuk feedback user
✅ Auto-clear form setelah tambah
✅ Auto-refresh list setelah tambah
✅ Koneksi ke PostgreSQL Database

---

## 10. Screenshot 

![alt text](image.png)

---

## 11. Cara Menjalankan Aplikasi

### Prasyarat:
1. **Java Development Kit (JDK)** 11 atau lebih baru dengan JavaFX
2. **PostgreSQL** sudah terinstall dan running
3. **Database** `agripos` sudah dibuat
4. **Tabel** `products` sudah ada dengan struktur:
   ```sql
   CREATE TABLE products (
       code VARCHAR(20) PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       price DOUBLE PRECISION NOT NULL,
       stock INTEGER NOT NULL
   );
   ```

### Langkah-langkah:

1. **Clone/Download** project ini
2. **Konfigurasi Database** di `AppJavaFX.java`:
   ```java
   private static final String DB_URL = "jdbc:postgresql://localhost:5432/agripos";
   private static final String DB_USER = "postgres";
   private static final String DB_PASS = "farhanah"; 
   ```
3. **Compile & Run**:
   - Menggunakan IDE (IntelliJ IDEA / Eclipse / VS Code):
     - Import project sebagai Java project
     - Tambahkan library JavaFX dan PostgreSQL JDBC
     - Run `AppJavaFX.java`
   
   - Menggunakan Command Line:
     ```bash
     # Compile
     javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls \
           -cp postgresql-driver.jar src/main/java/com/upb/agripos/*.java
     
     # Run
     java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls \
          -cp .:postgresql-driver.jar com.upb.agripos.AppJavaFX
     ```

---

## 12. Testing yang Dilakukan

### Test Case 1: Tambah Produk Normal
- **Input**: Code=P001, Name=Pupuk Organik, Price=50000, Stock=100
- **Expected**: Data tersimpan, muncul di TableView, alert sukses
- **Result**: ✅ PASS

### Test Case 2: Field Kosong
- **Input**: Salah satu field dikosongkan
- **Expected**: Alert error "Semua field harus diisi!"
- **Result**: ✅ PASS

### Test Case 3: Format Angka Salah
- **Input**: Price="abc", Stock="xyz"
- **Expected**: Alert error "Harga dan Stok harus berupa angka!"
- **Result**: ✅ PASS

### Test Case 4: Harga Negatif
- **Input**: Price=-1000
- **Expected**: Alert error dari validasi Service
- **Result**: ✅ PASS

### Test Case 5: Refresh List
- **Action**: Klik tombol "Refresh List"
- **Expected**: ListView update dengan data terbaru dari database
- **Result**: ✅ PASS

---

## 13. Kendala dan Solusi

### Kendala 1: JavaFX Module Path
**Problem**: Error "JavaFX runtime components are missing"
**Solusi**: Tambahkan `--module-path` dan `--add-modules` saat compile & run tapi tetep tidak bisa berhasil.
terus di sini kan ga bisa dan bener bener ngestuck berhari hari ga bisa di run ya karna javafx nya. sudah dicoba semua cara sesuai tutor you tube dan sebagainya tetep ga bisa. akhirnya coba ditambah file 'launcher' nah terus ga sengaja ke run sebenernya, terus malah bisa kalo di run di 'launcher'nya dan keluar GUI nya. 

### Kendala 2: Database Connection Error
**Problem**: Tidak bisa koneksi ke PostgreSQL
**Solusi**: Pastikan PostgreSQL running, database & tabel sudah dibuat, username/password benar

### Kendala 3: ListView Tidak Update
**Problem**: Setelah tambah produk, ListView tidak update otomatis
**Solusi**: Panggil `refreshProductList()` setelah operasi insert

---

## 14. Kesimpulan

1. **Event-Driven Programming** sangat cocok untuk aplikasi GUI yang interaktif
2. **Arsitektur MVC** memudahkan pemisahan concerns (UI, Logic, Data)
3. **SOLID Principles** (khususnya DIP) membuat code lebih maintainable
4. **Integrasi GUI-Backend** berhasil dilakukan tanpa membuat CRUD ulang
5. **JavaFX** menyediakan komponen yang powerful untuk membangun GUI modern

---

## 15. Referensi

1. Modul Praktikum PBO Week 12 - GUI Dasar JavaFX
2. JavaFX Documentation - https://openjfx.io/
3. SOLID Principles - Robert C. Martin
4. PostgreSQL JDBC Driver - https://jdbc.postgresql.org/

---
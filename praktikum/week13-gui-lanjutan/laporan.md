# Laporan Praktikum Minggu 13  
**Topik:** GUI Lanjutan (TableView dan Lambda Expression)

---

## Identitas
- **Nama**  : Fauzatul Farhanah  
- **NIM**   : 240202834  
- **Kelas** : 3IKRA

---

## Tujuan
1. Menampilkan data menggunakan TableView JavaFX  
2. Mengintegrasikan koleksi objek dengan GUI menggunakan ObservableList  
3. Menggunakan lambda expression untuk event handling  
4. Menghubungkan GUI dengan DAO secara penuh (CRUD: Create, Read, Delete)  
5. Membangun antarmuka GUI Agri-POS yang lebih interaktif dengan fitur hapus produk  

---

## Dasar Teori

### 1. TableView JavaFX
TableView adalah komponen GUI JavaFX untuk menampilkan data dalam bentuk tabel dengan baris dan kolom.  
Setiap kolom dapat dikonfigurasi menggunakan `PropertyValueFactory`.

### 2. ObservableList
ObservableList adalah list khusus JavaFX yang secara otomatis memperbarui UI ketika data berubah.

### 3. Lambda Expression
Lambda expression memungkinkan penulisan fungsi anonim secara ringkas.  
Contoh:  
```java
(e) -> { /* aksi */ }
```

### 4. PropertyValueFactory
Digunakan untuk menghubungkan kolom TableView dengan getter method object.

### 5. Event-Driven Programming
Aplikasi bereaksi terhadap event seperti klik tombol atau pemilihan data.

### 6. MVC + DAO Pattern
- **Model**: Product.java  
- **View**: ProductTableView.java  
- **Controller**: ProductController.java  
- **Service**: ProductService.java  
- **DAO**: ProductDAOImpl.java  

### 7. SOLID Principles (DIP)
View tidak langsung mengakses database.  
Alur: **View → Controller → Service → DAO → Database**

---

## Langkah Praktikum

### Langkah 1: Setup Database
```bash
psql -U postgres -f database/setup_database.sql
psql -U postgres -d agripos -c "SELECT * FROM products;"
```

### Langkah 2: Konfigurasi Database
```java
private static final String DB_PASS = "farhanah";
```

### Langkah 3: Model Layer
File: `Product.java`  
Atribut: `code`, `name`, `price`, `stock`

### Langkah 4: DAO Layer
File:
- `ProductDAO.java`
- `ProductDAOImpl.java`

CRUD:
- insert
- findAll
- findByCode
- update
- delete

### Langkah 5: Service Layer
```java
public void delete(String code) throws Exception {
    if (code == null || code.trim().isEmpty()) {
        throw new IllegalArgumentException("Kode tidak boleh kosong");
    }

    Product existing = productDAO.findByCode(code);
    if (existing == null) {
        throw new IllegalArgumentException("Produk tidak ditemukan");
    }

    productDAO.delete(code);
}
```

### Langkah 6: Controller Layer
Method:
- `load()`
- `delete(String code)`

### Langkah 7: View Layer (TableView)
```java
TableView<Product> table = new TableView<>();
table.setItems(productList);

TableColumn<Product, String> colCode = new TableColumn<>("Kode");
colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
```

### Langkah 8: Event Handler (Lambda)
```java
btnDelete.setOnAction(e -> {
    Product selected = tableView.getSelectionModel().getSelectedItem();
    if (selected != null) {
        controller.delete(selected.getCode());
        loadData();
    }
});
```

### Langkah 9: ObservableList Auto Refresh
```java
private void loadData() {
    productList.clear();
    productList.addAll(controller.load());
}
```

### Langkah 11: Testing
- Tambah Produk
- Hapus Produk
- Refresh Data
- Sort Kolom

### Commit Message
```bash
git commit -m "week13-gui-lanjutan"
```

---

## Kode Program

### ProductTableView.java
```java
TableView<Product> table = new TableView<>();
table.setItems(productList);
```

### Lambda Expression
```java
btnAdd.setOnAction(e -> controller.add(code, name, price, stock));
```

---

## Hasil Eksekusi
![alt text](image.png)

form input produk diubah menjadi kelola produk(manajemen produk)
---

## Analisis

## Cara Kerja Kode

### Flow Tambah Produk
```text
User input data
   ↓
Klik tombol Tambah
   ↓
btnAdd.setOnAction(lambda)
   ↓
controller.add(code, name, price, stock)
   ↓
service.insert()  // validasi input
   ↓
dao.insert()      // SQL INSERT
   ↓
loadData()
   ↓
ObservableList.clear() + addAll()
   ↓
TableView auto-refresh
```

### Flow Hapus Produk
```text
User pilih row di TableView
   ↓
Klik tombol Hapus
   ↓
btnDelete.setOnAction(lambda)
   ↓
tableView.getSelectionModel().getSelectedItem()
   ↓
Tampilkan dialog konfirmasi
   ↓
User klik OK
   ↓
controller.delete(code)
   ↓
service.delete()   // validasi data
   ↓
dao.delete()       // SQL DELETE
   ↓
loadData()
   ↓
TableView auto-refresh
```

---

## PropertyValueFactory

```java
new PropertyValueFactory<>("code"); // memanggil getCode()
new PropertyValueFactory<>("name"); // memanggil getName()
```

**Catatan penting:**
- Nama property **harus lowercase**
- Harus **match dengan getter method**
- Contoh: `"code"` → `getCode()`

---

## ObservableList

```java
ObservableList<Product> productList = FXCollections.observableArrayList();
tableView.setItems(productList);
```

**Karakteristik:**
- Setiap perubahan pada `productList` otomatis terdeteksi oleh TableView
- Tidak perlu `tableView.refresh()`
- UI menjadi **reaktif dan responsif**

---

## Perbedaan Week 12 vs Week 13

| Aspek | Week 12 | Week 13 |
|------|--------|--------|
| Tampilan Data | ListView (teks satu baris) | TableView (4 kolom terstruktur) |
| Struktur Data | String manual | Object `Product` |
| Data Binding | Manual refresh | ObservableList (auto-refresh) |
| Fitur | Tambah + Refresh | Tambah + Hapus + Refresh |
| Event Handler | Lambda sederhana | Lambda + Dialog konfirmasi |
| Controller Method | add(), getAllProducts() | add(), load(), delete() |
| Service Method | insert(), findAll() | insert(), findAll(), delete() |
| User Experience | Basic | Profesional dengan konfirmasi |
| Sortable | Tidak | Ya (klik header kolom) |
| Selectable | Tidak | Ya (pilih baris) |

---

## Keunggulan TableView

- **Tampilan Terstruktur**  
  Data ditampilkan dalam kolom-kolom sehingga mudah dibaca.

- **Sortable**  
  Klik header kolom untuk sortir ascending/descending.

- **Selectable**  
  Bisa memilih baris untuk operasi edit atau hapus.

- **Professional**  
  Tampilan menyerupai aplikasi enterprise modern.

- **Extensible**  
  Mudah menambah kolom atau custom cell renderer.

---

## Penerapan SOLID Principles

### Single Responsibility Principle (SRP)
- `ProductTableView` → UI saja  
- `ProductController` → Orkestrasi logika  
- `ProductService` → Business logic & validasi  
- `ProductDAO` → Akses database saja  

### Dependency Inversion Principle (DIP)
```text
View → Controller → Service → DAO → Database
```

- View tidak langsung mengakses DAO
- Service bergantung pada interface `ProductDAO`
- Kode lebih modular dan mudah di-maintain

---

## Kendala dan Solusi

### Kendala 1: TableView kosong setelah tambah
- **Penyebab:** Lupa memanggil `loadData()`
- **Solusi:** Tambahkan `loadData()` di event handler `btnAdd`

### Kendala 2: Error `cannot find symbol: PropertyValueFactory`
- **Penyebab:** Import tidak lengkap
- **Solusi:**
```java
import javafx.scene.control.cell.PropertyValueFactory;
```

### Kendala 3: Kolom TableView tidak muncul
- **Penyebab:** Nama property tidak sesuai getter
- **Solusi:**  
  `"code"` → `getCode()`  
  `"name"` → `getName()`

### Kendala 4: Banyak project bentrok di VS Code
- **Penyebab:** Membuka banyak folder week sekaligus
- **Solusi:** Buka hanya folder Week 13

### Kendala 5: Masih menjalankan Week 12
- **Penyebab:** Folder `target/` berisi compiled code lama
- **Solusi:**
```bash
mvn clean
mvn compile
```

---

## Kesimpulan
- TableView lebih profesional dan terstruktur  
- Lambda expression membuat kode lebih ringkas  
- ObservableList membuat UI reaktif  
- MVC + DAO menjaga arsitektur tetap clean  

---
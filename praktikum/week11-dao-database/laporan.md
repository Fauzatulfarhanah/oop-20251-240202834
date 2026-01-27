# Laporan Praktikum Minggu 11 
Topik: Data Access Object (DAO) dan CRUD Database dengan JDBC

## Identitas
- Nama  : Fauzatul Farhanah
- NIM   : 240202834
- Kelas : 3IKRA

---

## Tujuan Pembelajaran

Setelah mengikuti praktikum ini, mahasiswa mampu:

1. Menjelaskan konsep Data Access Object (DAO) dalam pengembangan aplikasi OOP.
2. Menghubungkan aplikasi Java dengan basis data menggunakan JDBC.
3. Mengimplementasikan operasi CRUD (Create, Read, Update, Delete) secara lengkap.
4. Mengintegrasikan DAO dengan class aplikasi OOP sesuai prinsip desain yang baik.

---

## Dasar Teori
### 1. Konsep Data Access Object (DAO)

DAO adalah pola desain yang memisahkan logika akses data dari logika bisnis aplikasi. Dengan DAO, perubahan teknologi basis data tidak memengaruhi logika utama aplikasi.

Manfaat DAO:
- Kode lebih terstruktur dan mudah dipelihara
- Mengurangi tight coupling antara aplikasi dan database
- Mendukung pengujian dan pengembangan lanjutan

---

### 2. JDBC dan Koneksi Database

JDBC (Java Database Connectivity) digunakan untuk menghubungkan aplikasi Java dengan basis data relasional, dalam praktikum ini menggunakan PostgreSQL.

Komponen utama JDBC:
- DriverManager
- Connection
- PreparedStatement
- ResultSet

---

## Langkah Praktikum
1. Setup Lingkungan
- Menginstall dan menjalankan PostgreSQL.
- Membuat database bernama agripos.
- Menambahkan library PostgreSQL JDBC (postgresql-42.x.x.jar) ke project Java.
- Menghubungkan project Java di Visual Studio Code dengan database PostgreSQL.

2. Pembuatan Database
- Membuat tabel products melalui file products.sql.
- Menjalankan file SQL tersebut menggunakan pgAdmin (Query Tool).

3. Coding
- Membuat class Product sebagai representasi data.
- Membuat ProductDAO (interface).
- Membuat ProductDAOImpl untuk implementasi CRUD (Create, Read, Update, Delete).
- Membuat MainDAOTest untuk menguji seluruh fungsi CRUD.

4. Run Program
- Menjalankan MainDAOTest.
- Memastikan koneksi database berhasil dan seluruh operasi CRUD berjalan.
5. Commit Git

---

## Kode Program
kode utama pada 'MainDAOTest.java'
```java
public class MainDAOTest {
    public static void main(String[] args) {
        ProductDAO dao = new ProductDAOImpl();

        System.out.println("=== 1. INSERT DATA ===");
        Product p = new Product("P01", "Beras Premium 5kg", 100, 50000);
        dao.insert(p);

        System.out.println("\n=== 2. UPDATE DATA ===");
        p.setStok(120);
        dao.update(p);

        System.out.println("\n=== 3. FIND BY CODE (P01) ===");
        Product found = dao.findByCode("P01");

        System.out.println("\n=== 4. READ ALL DATA ===");
        dao.findAll();

        System.out.println("\n=== 5. DELETE DATA (P02) ===");
        dao.delete("P02");
    }
}

---

## Hasil Eksekusi
![alt text](image.png)
---

## Analisis
1. Program berjalan dengan cara:
- MainDAOTest memanggil method CRUD dari ProductDAOImpl.
- ProductDAOImpl menggunakan JDBC untuk berkomunikasi dengan database PostgreSQL.
- Setiap operasi SQL (INSERT, SELECT, UPDATE, DELETE) dijalankan melalui PreparedStatement.

2. Perbedaan pendekatan minggu ini dibanding minggu sebelumnya:
- Minggu sebelumnya data masih disimpan secara statis atau berbasis object saja.
- Minggu ini data disimpan secara persisten di database PostgreSQL.
- Digunakan DAO Pattern untuk memisahkan logika akses data dan logika program utama.
3. Kendala yang dihadapi:
- 'Error No suitable driver found' karena JDBC belum ditambahkan.
- 'Error ClassNotFoundException' karena file .jar belum terhubung ke project.
- Duplicate data pada database karena proses insert dijalankan lebih dari satu kali.
- aplikasi Java belum dapat terhubung ke database PostgreSQL karena driver JDBC belum dikenali oleh sistem. 

Solusi:
- Menambahkan library PostgreSQL JDBC ke project.
- Mengatur ulang koneksi database.
- Mengecek dan mengelola data di database menggunakan pgAdmin.
- menambahkan dependency PostgreSQL JDBC menggunakan Maven melalui file 'pom.xml', sehingga proses koneksi database dapat berjalan dengan baik dan aplikasi dapat dijalankan dengan normal.
---

## Kesimpulan
Dengan menggunakan PostgreSQL dan DAO Pattern, program menjadi lebih terstruktur, mudah dikembangkan, dan mampu menyimpan data secara permanen. Implementasi CRUD menggunakan JDBC memungkinkan aplikasi Java berinteraksi langsung dengan database secara efisien dan profesional.

---

## Penilaian (Mengacu RPS)

| Aspek                         | Bobot |
|-------------------------------|------:|
| Implementasi DAO sesuai OOP   | 30%   |
| CRUD berjalan lengkap         | 30%   |
| Integrasi DAO dengan aplikasi | 20%   |
| Laporan & dokumentasi         | 20%   |
| Total                         | 100%  |

---

## Checklist Keberhasilan

- [ ] Interface DAO dibuat
- [ ] Implementasi DAO menggunakan JDBC
- [ ] CRUD berjalan lengkap
- [ ] Tidak ada SQL langsung di `main()`
- [ ] Screenshot hasil CRUD tersedia (`screenshots/crud_result.png`)
- [ ] Laporan lengkap (`laporan_week11.md`)
# Laporan Praktikum Minggu 6 
Topik: [Desain Arsitektur Sistem dengan UML dan Prinsip SOLID]

## Identitas
- Nama  : [Fauzatul Farhanah]
- NIM   : [240202834]
- Kelas : [3IKRA]

---

## Tujuan
1. Mahasiswa mampu mengidentifikasi kebutuhan sistem ke dalam diagram UML.
2. Mahasiswa mampu menggambar UML Class Diagram dengan relasi antar class yang tepat.
3. Mahasiswa mampu menjelaskan prinsip desain OOP (SOLID).
4. Mahasiswa mampu menerapkan minimal dua prinsip SOLID dalam kode program.

---

## Deksripsi Singkat Sistem
Agri-POS adalah sistem yang dirancang khusus untuk manajemen transaksi penjualan produk pertanian. Sistem ini memfasilitasi Kasir dalam melakukan proses checkout belanjaan pelanggan secara efisien. Fitur utama mencakup manajemen stok produk, penghitungan total transaksi secara otomatis, serta dukungan berbagai metode pembayaran (Tunai dan E-Wallet) yang terintegrasi dengan Payment Gateway.

---

## Penjelasan Diagram
1. Use Case Diagram (Kebutuhan Sistem)  
Diagram ini memetakan hubungan antara pengguna (aktor) dan fungsi sistem.
- Kasir memiliki peran utama dalam operasional harian seperti membuat transaksi, menambah produk ke keranjang, dan melakukan checkout.  
- Fungsi Checkout memiliki relasi <<include>> ke Pilih Metode Pembayaran, yang artinya kasir wajib memilih cara bayar setiap kali transaksi dilakukan.  
- Admin bertugas mengelola data barang (stok) dan memantau laporan, sementara Payment Gateway bertindak sebagai pendukung sistem untuk memverifikasi pembayaran digital.

2.  Activity Diagram  
Diagram ini menjelaskan langkah-langkah kerja sistem dari sisi pengguna.  
- Terdapat logika Decision (Percabangan) pada tahap pengecekan stok: jika stok cukup, barang masuk ke keranjang; jika tidak, sistem akan menampilkan pesan stok habis.  
- Alur pembayaran juga digambarkan bercabang (Tunai atau E-Wallet). Jika memilih E-Wallet, sistem akan berinteraksi dengan Payment Gateway untuk validasi saldo sebelum transaksi dinyatakan selesai.

3. Sequence Diagram 
 Ini adalah bagian paling detail yang menunjukkan urutan pesan antar objek dalam waktu nyata (runtime).  
 - Logika Alt (Alternative) Pertama: Sistem menangani dua skenario pembayaran. Pada pembayaran tunai, sistem langsung mengonfirmasi sukses. Pada E-Wallet, sistem memanggil permintaanPembayaran().  
 - Logika Alt (Alternative) Kedua (Saldo): Di dalam blok E-Wallet, terdapat pengecekan saldo. Jika [Saldo Tidak Cukup], sistem menjalankan fungsi pembayaranGagal(). Jika [Saldo Cukup], sistem menjalankan pembayaranSukses() dan melanjutkan ke tahap penyimpanan transaksi  
 - Setelah pembayaran diverifikasi sukses, sistem menjalankan fungsi internal simpanTransaksi() dan tampilkandanCetakStruk().

4. Class Diagram
 Diagram ini adalah cetak biru teknis yang mendukung semua alur di atas.
- Inheritance (Pewarisan): Kelas Kasir mewarisi sifat dari kelas abstrak User, menunjukkan hubungan spesialisasi.
- Composition (Komposisi): Hubungan antara Transaksi dan CartItem menggunakan komposisi. Artinya, item belanja tidak bisa ada tanpa adanya transaksi. Jika objek transaksi dihapus, maka item di dalamnya otomatis terhapus.
- SOLID & Interface: Penggunaan interface Metode Pembayaran mendukung prinsip Dependency Inversion, di mana Layanan Pembayaran tidak kaku dan bisa beradaptasi dengan berbagai jenis pembayaran baru di masa depan.

Keterkaitan Antar Diagram:
Use Case memberikan alasan kenapa fungsi itu ada, Activity menunjukkan urutan langkahnya, Sequence memperlihatkan bagaimana objek-objek teknis saling "mengobrol" lewat logika Alt, dan Class Diagram menyediakan wadah (atribut dan method) agar semua perintah tersebut bisa dijalankan secara kode.

---

## Penjelasan penerapan prinsip SOLID
1. S - Single Responsibility Principle (SRP)  
Setiap kelas hanya punya satu tanggung jawab yang spesifik. Kelas Transaksi hanya fokus mengelola data transaksi dan total harga, sementara kelas Layanan Pembayaran bertanggung jawab penuh atas alur proses pembayaran. Dengan ini, jika ada perubahan di alur pembayaran, kita tidak perlu mengutak-atik kelas transaksi.

2. O - Open/Closed Principle (OCP)  
Sistem ini terbuka untuk penambahan fitur tapi tertutup untuk modifikasi kode inti. Hal ini terlihat pada penggunaan interface Metode Pembayaran. Jika ke depannya kita ingin menambah metode pembayaran baru (misalnya QRIS), kita cukup membuat kelas baru tanpa perlu mengubah logika yang sudah ada di Layanan Pembayaran.

3. L - Liskov Substitution Principle (LSP)  
Semua kelas turunan atau implementasi harus bisa menggantikan kelas induknya tanpa merusak sistem. Dalam diagram kita, PembayaranTunai dan PembayaranEWallet adalah bentuk nyata dari Metode Pembayaran. Sistem bisa menggunakan salah satu dari keduanya secara bergantian tanpa peduli jenis spesifiknya, dan proses pembayaran akan tetap berjalan normal.

4. I - Interface Segregation Principle (ISP)  
Prinsip ini menjaga agar sebuah interface memiliki fungsi yang tidak relevan. interface Metode Pembayaran kita buat sangat spesifik hanya untuk menangani aksi pembayaran(). Jadi, kelas yang mengimplementasikannya tidak terbebani oleh fungsi-fungsi lain yang tidak dibutuhkan.

5. D - Dependency Inversion Principle (DIP)  
Prinsip ini mengajarkan agar kelas tingkat tinggi tidak bergantung pada kelas tingkat rendah, melainkan pada abstraksi. Hal ini terlihat di Class Diagram, di mana Layanan Pembayaran bergantung pada interface Metode Pembayaran, bukan langsung pada kelas PembayaranTunai atau PembayaranEWallet. Hal ini yang membuat alur di Sequence Diagram bisa fleksibel memilih antara tunai atau e-wallet melalui satu pintu yang sama.

## Tabel Traceability

| ID FR | Kebutuhan Fungsional | Use Case (Diagram) | Activity / Sequence | Kelas/Interface Realisasi |
| :--- | :--- | :--- | :--- | :--- |
| **FR-01** | Menambah Produk ke Keranjang | Tambah Produk ke Keranjang | Input/Scan Produk | `Product`, `CartItem` |
| **FR-02** | Proses Checkout | Checkout | `checkout()` | `Transaksi`, `Kasir` |
| **FR-03** | Memilih Metode Pembayaran | Pilih Metode Pembayaran | Pilih Metode Pembayaran | `MetodePembayaran` |
| **FR-04** | Validasi Pembayaran E-Wallet | Pembayaran E-Wallet | `permintaanPembayaran()` | `PaymentGateway`, `PembayaranEWallet` |
| **FR-05** | Finalisasi & Simpan Data | Cetak Struk | `simpanTransaksi()` | `Transaksi`, `LayananPembayaran` |
| **FR-06** | Manajemen Stok & Barang | Kelola Produk | (Admin Role) | `Product` |
---

## Kesimpulan dan refleksi singkat 
Secara keseluruhan, perancangan sistem Agri-POS ini sudah terstruktur. Keunggulan utama dari desain ini adalah kemampuannya dalam menangani skenario dunia nyata, yang terlihat dari penggunaan logika Alt (Alternative) pada Sequence Diagram untuk membedakan alur pembayaran tunai dan digital. Selain itu, sistem ini menjamin keamanan data melalui relasi Composition, di mana setiap item belanja terikat dengan transaksinya. jika transaksi dibatalkan, tidak akan ada data sampah yang tertinggal. Penggunaan interface juga membuktikan bahwa sistem ini dirancang dengan standar industri (SOLID) yang membuatnya sangat fleksibel untuk dikembangkan lebih jauh tanpa harus merombak kode dari nol.
Refleksi
Proses perancangan ini menyadarkan bahwa sebuah sistem yang baik harus bisa menangani kondisi gagal (seperti saldo tidak cukup atau stok habis), bukan hanya alur suksesnya saja. Meskipun saat ini fitur intinya sudah berjalan dengan baik, sistem Agri-POS masih memiliki potensi pengembangan yang besar. Ke depannya, sistem ini bisa ditingkatkan dengan fitur notifikasi stok kritis otomatis untuk Admin agar stok produk pertanian selalu terjaga. Selain itu, integrasi dengan database berbasis cloud akan menjadi langkah strategis agar data penjualan dari berbagai cabang bisa terpantau secara real-time dalam satu dasbor yang terpusat.
---

## Quiz
1. Jelaskan perbedaan aggregation dan composition serta berikan contoh penerapannya pada desain Anda.
   **Jawaban:** Aggregation adalah bentuk hubungan yang lebih lemah (weak relationship). Meskipun objek induk "memiliki" objek bagian, objek bagian tersebut tetap memiliki masa hidup yang mandiri. Artinya, jika objek induk dihapus, objek bagian tidak akan ikut terhapus.Penerapan pada Desain: Relasi ini diterapkan pada hubungan antara kelas CartItem dengan Product. CartItem mereferensikan informasi dari kelas Product (seperti nama dan harga). Namun, Product adalah data master yang berdiri sendiri di dalam katalog barang. Pada desain ini, jika sebuah transaksi atau isi keranjang belanja dihapus, objek Product tetap akan tersimpan di dalam sistem untuk digunakan pada transaksi lainnya. Ini menunjukkan bahwa Product memiliki eksistensi yang tidak terikat pada satu transaksi tertentu.

   sedangkan Composition adalah bentuk hubungan yang kuat (strong relationship) di mana objek bagian tidak dapat berdiri sendiri tanpa objek induknya (whole). Jika objek induk dimusnahkan, maka secara otomatis objek bagiannya akan ikut terhapus dari memori sistem.Penerapan pada Desain: Relasi ini diterapkan pada hubungan antara kelas Transaksi dengan CartItem. Dalam sistem Agri-POS, sebuah item belanja (CartItem) secara logika hanya ada karena adanya sebuah transaksi (Transaction). Jika objek Transaksi dihapus, maka seluruh rincian barang di dalam CartItem akan ikut terhapus demi menjaga integritas data transaksi.

2. Bagaimana prinsip Open/Closed dapat memastikan sistem mudah dikembangkan? 
   **Jawaban:** Prinsip Open/Closed memastikan sistem Agri-POS mudah dikembangkan karena kita memisahkan logika utama dengan detail implementasi. Melalui penggunaan interface pada metode pembayaran, kita dapat memperluas fungsionalitas sistem (seperti menambah jenis E-Wallet baru) cukup dengan menambah kelas baru tanpa harus memodifikasi kode inti yang sudah stabil. Hal ini menjamin keberlanjutan sistem dan meminimalisir risiko munculnya bug saat pengembangan fitur baru dilakukan  

3. Mengapa Dependency Inversion Principle (DIP) meningkatkan testability? Berikan contoh     penerapannya.
   **Jawaban:** DIP meningkatkan testability karena prinsip ini menghilangkan ketergantungan keras terhadap modul tingkat rendah atau layanan eksternal. Dengan bergantung pada abstraksi (interface), kita dapat dengan mudah menyuntikkan objek tiruan (Mock/Stub) selama proses pengujian. Hal ini memastikan pengujian dapat dilakukan secara terisolasi, lebih cepat, dan tidak merusak data pada lingkungan produksi.  
   Penerapan DIP pada desain saya terletak pada interface MetodePembayaran. Dengan cara ini, LayananPembayaran tidak perlu tahu detail teknis bagaimana E-Wallet bekerja. Hal ini meningkatkan testability karena saya bisa mengganti objek E-Wallet asli dengan objek simulasi saat pengujian, sehingga proses verifikasi logika sistem menjadi lebih mandiri, aman, dan tidak bergantung pada pihak ketiga.  

## Checklist Pemeriksaan Mandiri

- [ ] Semua FR tercover di Use Case Diagram
- [ ] Activity & Sequence memuat alur sukses dan gagal (alt/opt)
- [ ] Class Diagram memiliki visibility, tipe, multiplicity, dan paket
- [ ] Mapping SOLID (min 3) ditunjukkan di desain dan dijelaskan di laporan
- [ ] Konsistensi penamaan antar diagram terjaga
- [ ] File sumber diagram disertakan (`src/uml/`) + gambar `docs/`
- [ ] Commit mengikuti format `week6-uml-solid: iterasi-N <deskripsi>`

## Referensi

* Freeman, E. (2020). *Head First Design Patterns* (Bab 1–3).
* Gamma, E. et al. (2018). *Design Patterns: Elements of Reusable Object-Oriented Software*.
* Robert C. Martin. *SOLID Principles*.
* Object Management Group (OMG). *UML Specification*.

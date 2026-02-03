-- Week 14: Integrasi Individu
-- Database Setup Script
-- Mahasiswa: Fauzatul Farhanah (240202834)

CREATE DATABASE agripos;

\c agripos

DROP TABLE IF EXISTS products;

CREATE TABLE products (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DOUBLE PRECISION NOT NULL CHECK (price >= 0),
    stock INTEGER NOT NULL CHECK (stock >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO products (code, name, price, stock) VALUES
('P001', 'Pupuk Organik', 50000, 100),
('P002', 'Benih Padi IR64', 75000, 50),
('P003', 'Pestisida Nabati', 35000, 80),
('P004', 'Pupuk Urea', 45000, 120),
('P005', 'Benih Jagung Hibrida', 90000, 40),
('P006', 'Pupuk NPK', 55000, 90),
('P007', 'Herbisida Organik', 42000, 60),
('P008', 'Benih Kedelai', 65000, 70),
('P009', 'Pupuk Kompos', 30000, 150),
('P010', 'Pestisida Premium', 48000, 55);

SELECT * FROM products ORDER BY code;

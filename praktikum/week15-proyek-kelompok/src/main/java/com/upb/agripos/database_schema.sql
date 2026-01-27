-- Database Schema for AgriPOS
-- DROP TABLE IF EXISTS statements
DROP TABLE IF EXISTS transaction_items;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;

-- CREATE TABLES
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN', 'KASIR')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    stock INTEGER NOT NULL CHECK (stock >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    transaction_code VARCHAR(20) UNIQUE NOT NULL,
    user_id INTEGER NOT NULL REFERENCES users(id),
    total_amount DECIMAL(10,2) NOT NULL CHECK (total_amount >= 0),
    payment_method VARCHAR(20) NOT NULL CHECK (payment_method IN ('CASH', 'E_WALLET')),
    payment_status VARCHAR(20) NOT NULL CHECK (payment_status IN ('PENDING', 'SUCCESS', 'FAILED')),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transaction_items (
    id SERIAL PRIMARY KEY,
    transaction_id INTEGER NOT NULL REFERENCES transactions(id) ON DELETE CASCADE,
    product_code VARCHAR(10) NOT NULL REFERENCES products(code),
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    subtotal DECIMAL(10,2) NOT NULL CHECK (subtotal >= 0)
);

-- INSERT DEFAULT DATA
INSERT INTO users (username, password, full_name, role) VALUES
('admin', 'admin123', 'Administrator System', 'ADMIN'),
('kasir1', 'kasir123', 'Kasir Pertama', 'KASIR'),
('kasir2', 'kasir456', 'Kasir Kedua', 'KASIR');

INSERT INTO products (code, name, category, price, stock) VALUES
('P001', 'Beras Premium 5kg', 'Sembako', 75000.00, 50),
('P002', 'Minyak Goreng 2L', 'Sembako', 35000.00, 100),
('P003', 'Gula Pasir 1kg', 'Sembako', 15000.00, 75),
('P004', 'Telur Ayam 1kg', 'Sembako', 28000.00, 60),
('P005', 'Sabun Mandi 90g', 'Kebutuhan Mandi', 5000.00, 200),
('P006', 'Shampoo 180ml', 'Kebutuhan Mandi', 25000.00, 80),
('P007', 'Pasta Gigi 100g', 'Kebutuhan Mandi', 12000.00, 120),
('P008', 'Aqua Galon 19L', 'Minuman', 20000.00, 40),
('P009', 'Susu UHT 1L', 'Minuman', 22000.00, 65),
('P010', 'Kopi Sachet 10x', 'Minuman', 15000.00, 90);

-- CREATE INDEXES FOR PERFORMANCE
CREATE INDEX idx_transactions_user_id ON transactions(user_id);
CREATE INDEX idx_transactions_date ON transactions(transaction_date);
CREATE INDEX idx_transaction_items_transaction_id ON transaction_items(transaction_id);
CREATE INDEX idx_products_category ON products(category);
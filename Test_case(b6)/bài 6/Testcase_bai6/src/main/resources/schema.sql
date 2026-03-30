-- ============================================================
--  Database Setup Script for NghienPhim User Management
-- ============================================================

-- Tạo database (nếu chưa có)
CREATE DATABASE IF NOT EXISTS testdb
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE testdb;

-- Xoá bảng cũ (nếu có) để tạo lại sạch
DROP TABLE IF EXISTS users;

-- Tạo bảng users
CREATE TABLE users (
    username VARCHAR(50)  NOT NULL PRIMARY KEY,
    fullname VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email    VARCHAR(100) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ── Seed data mẫu ──────────────────────────────────────────
INSERT INTO users (username, fullname, password, email) VALUES
    ('admin',  'Văn Tèo',   '123',      'admin@gmail.com'),
    ('user01', 'Nguyễn An', 'pass123',  'an@gmail.com'),
    ('user02', 'Trần Bình', 'pass456',  'binh@gmail.com');

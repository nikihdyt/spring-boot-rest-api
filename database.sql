CREATE DATABASE db_xtramile_apl_test;

USE db_xtramile_apl_test;

CREATE TABLE students (
    id VARCHAR(25) NOT NULL,
    nama_depan VARCHAR(50) NOT NULL,
    nama_belakang VARCHAR(100),
    tanggal_lahir DATE NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

Drop table students;
DESC students;
SELECT * FROM students;
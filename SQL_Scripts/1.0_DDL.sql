DROP DATABASE IF EXISTS comandaxpress;
CREATE DATABASE comandaxpress;
USE comandaxpress;

-- Creación de la tabla Usuarios
CREATE TABLE Usuarios (
    usuario_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    usuario VARCHAR(255) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL,
    rol ENUM('cliente', 'admin') NOT NULL
);

-- Creación de la tabla Categorías de Productos
CREATE TABLE Categorias (
    categoria_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT
);

-- Creación de la tabla Productos
CREATE TABLE Productos (
    producto_id INT AUTO_INCREMENT PRIMARY KEY,
    categoria_id INT,
    nombre VARCHAR(255) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    descripcion TEXT,
    imagen_url VARCHAR(255),
    activo BOOLEAN,
    FOREIGN KEY (categoria_id) REFERENCES Categorias(categoria_id) ON DELETE SET NULL
);

-- Creación de la tabla Mesas
CREATE TABLE mesa(
    mesa_id INT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL UNIQUE,
    capacidad INT NOT NULL,
    activa BOOLEAN NOT NULL DEFAULT TRUE
);

-- Creación de la tabla Tickets
CREATE TABLE Tickets (
    ticket_id INT AUTO_INCREMENT PRIMARY KEY,
    mesa_id INT,
    fecha_hora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (mesa_id) REFERENCES Mesa(mesa_id)
);

-- Creación de la tabla Detalle de Tickets
CREATE TABLE Ticket_Detalle (
    ticket_id INT,
    producto_id INT,
    cantidad INT NOT NULL,
    FOREIGN KEY (ticket_id) REFERENCES Tickets(ticket_id),
    FOREIGN KEY (producto_id) REFERENCES Productos(producto_id),
    PRIMARY KEY (ticket_id, producto_id)
);

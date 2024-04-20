DROP DATABASE IF EXISTS comandaxpress;
CREATE DATABASE comandaxpress;
USE comandaxpress;

-- Creación de la tabla Usuarios
CREATE TABLE Usuarios (
    usuario_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    usuario VARCHAR(255) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL, -- Se recomienda guardar un hash de la contraseña
    rol ENUM('cliente', 'admin') NOT NULL -- O los roles que requieras
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
CREATE TABLE Mesas (
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
    FOREIGN KEY (mesa_id) REFERENCES Mesas(mesa_id)
);

-- Creación de la tabla Detalle de Tickets
CREATE TABLE TicketDetalle (
    ticket_id INT,
    producto_id INT,
    cantidad INT NOT NULL,
    FOREIGN KEY (ticket_id) REFERENCES Tickets(ticket_id),
    FOREIGN KEY (producto_id) REFERENCES Productos(producto_id),
    PRIMARY KEY (ticket_id, producto_id)
);

-- Inserts para Usuarios
INSERT INTO Usuarios (nombre, apellido, email, usuario, contraseña, rol) VALUES 
('Juan', 'Pérez', 'juan.perez@example.com', 'juanp', 'hashed_password', 'cliente'),
('Maria', 'Gomez', 'maria.gomez@example.com', 'mariag', 'hashed_password', 'admin');

-- Inserts para Categorías de Productos
INSERT INTO Categorias (nombre, descripcion) VALUES 
('Entrantes', 'Platos para comenzar tu comida'),
('Platos Principales', 'El corazón de la comida'),
('Postres', 'El dulce final de la comida');

-- Inserts para Productos
INSERT INTO Productos (categoria_id, nombre, precio, descripcion, activo) VALUES 
(1, 'Patatas Bravas', 5.00, 'Patatas con salsa brava', TRUE),
(2, 'Paella', 15.00, 'Paella Valenciana', TRUE),
(3, 'Flan', 4.00, 'Flan casero con caramelo', TRUE);

-- Inserts para Mesas
INSERT INTO Mesa (numero, capacidad, activa) VALUES 
(1, 4, TRUE),
(2, 2, FALSE),
(3, 6, TRUE);

-- Inserts para Tickets
-- Asumiendo que las mesas 1 y 3 están activas y han generado tickets.
INSERT INTO Tickets (mesa_id) VALUES 
(1),
(3);

-- Inserts para Detalle de Tickets
-- Asumiendo que los tickets 1 y 2 contienen diferentes productos.
INSERT INTO TicketDetalle (ticket_id, producto_id, cantidad) VALUES 
(1, 1, 2), -- Ticket 1 contiene 2 Patatas Bravas
(1, 2, 1), -- Ticket 1 contiene 1 Paella
(2, 3, 3); -- Ticket 2 contiene 3 Flan

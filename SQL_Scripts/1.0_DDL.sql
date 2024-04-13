drop database if exists comandaxpress;
create database comandaxpress;
use comandaxpress;

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
CREATE TABLE Categorias(
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
    FOREIGN KEY (categoria_id) REFERENCES Categorias(categoria_id)
);

-- Creación de la tabla Pedidos
CREATE TABLE Pedidos (
    pedido_id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('pendiente', 'completado', 'cancelado') NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES Usuarios(usuario_id)
);

-- Creación de la tabla Pedido_Producto para asociar pedidos con productos (muchos a muchos)
CREATE TABLE Pedido_Producto (
    pedido_id INT,
    producto_id INT,
    cantidad INT NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES Pedidos(pedido_id),
    FOREIGN KEY (producto_id) REFERENCES Productos(producto_id),
    PRIMARY KEY (pedido_id, producto_id)
);



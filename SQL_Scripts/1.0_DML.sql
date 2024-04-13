-- Insertar datos en la tabla Usuarios
INSERT INTO Usuarios (nombre, apellido, email, usuario, contraseña, rol) VALUES 
('Juan', 'Pérez', 'juan.perez@example.com', 'juanperez', 'pass123', 'cliente'),
('María', 'López', 'maria.lopez@example.com', 'marialopez', 'pass456', 'admin');

-- Insertar datos en la tabla Categorias_Productos
INSERT INTO Categorias (nombre, descripcion) VALUES 
('Bebidas', 'Bebidas frías y calientes'),
('Postres', 'Dulces, pasteles y más');

-- Insertar datos en la tabla Productos
INSERT INTO Productos (categoria_id, nombre, precio, descripcion, imagen_url) VALUES 
(1, 'Café Americano', 2.50, 'Café negro intenso', 'url_a_imagen_del_cafe'),
(2, 'Cheesecake', 4.00, 'Tarta de queso clásica', 'url_a_imagen_del_cheesecake');

-- Insertar datos en la tabla Pedidos
INSERT INTO Pedidos (usuario_id, estado, total) VALUES 
(1, 'pendiente', 6.50),
(2, 'completado', 8.00);

-- Insertar datos en la tabla Pedido_Producto
INSERT INTO Pedido_Producto (pedido_id, producto_id, cantidad) VALUES 
(1, 1, 2),
(1, 2, 1),
(2, 2, 2);


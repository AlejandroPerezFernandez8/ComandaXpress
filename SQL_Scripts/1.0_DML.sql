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
INSERT INTO mesa (numero, capacidad, activa) VALUES 
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
INSERT INTO Ticket_Detalle (ticket_id, producto_id, cantidad) VALUES 
(1, 1, 2), -- Ticket 1 contiene 2 Patatas Bravas
(1, 2, 1), -- Ticket 1 contiene 1 Paella
(2, 3, 3); -- Ticket 2 contiene 3 Flan

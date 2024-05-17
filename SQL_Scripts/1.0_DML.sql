-- Inserts para Usuarios
INSERT INTO Usuarios (nombre, apellido, email, usuario, contraseña, foto) VALUES 
('Juan', 'Pérez', 'juan.perez@example.com', 'juanp', 'hashed_password', 'sdjfnlsjdnfkjsdnflknw9uehlijsndfkbsduf'),
('Maria', 'Gomez', 'maria.gomez@example.com', 'mariag', 'hashed_password', 'asjdasjkndasndjasndknaksdnkasndkjas'),
('Carlos', 'Fernandez', 'carlos.fernandez@example.com', 'carlosf', 'hashed_password', 'asdadasdqwewqeqwe1231asdasdasd'),
('Ana', 'Ruiz', 'ana.ruiz@example.com', 'anar', 'hashed_password', '123qwewqeasdzxcsadqweasdzxc'),
('Pedro', 'Alvarez', 'pedro.alvarez@example.com', 'pedroa', 'hashed_password', 'zxvcxvxcvdsfsdfsdfsdf'),
('Luisa', 'Martinez', 'luisa.martinez@example.com', 'luisam', 'hashed_password', 'cvbcvbsdfgsdfgsdfgwer'),
('Jose', 'Lopez', 'jose.lopez@example.com', 'josel', 'hashed_password', 'werwersdfsdfwerwersdf'),
('Lucia', 'Molina', 'lucia.molina@example.com', 'luciam', 'hashed_password', 'sdfwerwer123123ewrwer'),
('Miguel', 'Sanchez', 'miguel.sanchez@example.com', 'miguels', 'hashed_password', 'wer123123werwefsdfsdf'),
('Sofia', 'Prado', 'sofia.prado@example.com', 'sofiap', 'hashed_password', 'sdfsdf123123erwerwe'),
('Admin', 'Admin', 'admin@example.com', 'admin', 'admin', 'adminphoto');

-- Inserts para Categorías de Productos
INSERT INTO Categorias (nombre, descripcion) VALUES 
('Entrantes', 'Platos para comenzar tu comida'),
('Platos Principales', 'El corazón de la comida'),
('Postres', 'El dulce final de la comida'),
('Bebidas', 'Refrescante y variado'),
('Aperitivos', 'Pequeños bocados antes de la comida'),
('Sopas', 'Calientes y reconfortantes'),
('Ensaladas', 'Ligeras y saludables'),
('Carnes', 'Los mejores cortes seleccionados'),
('Pescados', 'Del mar a la mesa'),
('Pizzas', 'Hechas en horno de leña');

-- Inserts para Productos
INSERT INTO Productos (categoria_id, nombre, precio) VALUES 
(1, 'Patatas Bravas', 5.00),
(1, 'Croquetas', 6.00),
(2, 'Paella', 15.00),
(2, 'Bistec', 18.00),
(3, 'Flan', 4.00),
(3, 'Tiramisú', 5.50),
(4, 'Agua', 1.50),
(4, 'Cerveza', 3.00),
(5, 'Aceitunas', 3.00),
(5, 'Pan con Tomate', 4.50);

-- Inserts para Mesas
INSERT INTO mesa (numero, capacidad, activa) VALUES 
(1, 4, TRUE),
(2, 2, FALSE),
(3, 6, TRUE),
(4, 4, TRUE),
(5, 2, TRUE),
(6, 8, FALSE),
(7, 4, TRUE),
(8, 2, TRUE),
(9, 6, FALSE),
(10, 10, TRUE);


-- Inserts para Tickets
-- Asumiendo que las mesas 1 y 3 están activas y han generado tickets.
-- Assuming the active tables 1, 3, 4, 5, 7, 8, and 10 have generated tickets.
INSERT INTO Tickets (mesa_id) VALUES 
(1),
(3),
(4),
(5),
(7),
(8),
(10);


-- Inserts para Detalle de Tickets
-- Assuming tickets have different products in various quantities.
INSERT INTO Ticket_Detalle (ticket_id, producto_id, cantidad) VALUES 
(1, 1, 2), 
(1, 2, 1),
(2, 3, 3), 
(3, 4, 1), 
(3, 5, 2), 
(4, 6, 2), 
(4, 7, 3),
(5, 8, 1), 
(5, 9, 2), 
(6, 1, 3); 


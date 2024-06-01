use comandaxpress;
INSERT INTO Usuarios (nombre, apellido, email, usuario, contraseña, foto)
VALUES ('Admin', 'User', 'admin@example.com', 'admin', 'admin', NULL);

INSERT INTO mesa (numero, capacidad) VALUES
(1, 4), (2, 4), (3, 4), (4, 4), (5, 4), (6, 4), (7, 4), (8, 4), (9, 4), (10, 4),
(11, 4), (12, 4), (13, 4), (14, 4);

INSERT INTO Categorias (nombre, descripcion) VALUES
('Entrantes', 'Platos ligeros para comenzar la comida'),
('Refrescos', 'Bebidas sin alcohol para acompañar la comida'),
('Vinos', 'Selección de vinos tintos, blancos y rosados'),
('Cervezas', 'Variedad de cervezas nacionales e internacionales'),
('Postres', 'Deliciosos postres para finalizar la comida'),
('Carnes', 'Selección de carnes a la parrilla y guisadas'),
('Pescados', 'Pescados frescos preparados de diversas maneras'),
('Ensaladas', 'Ensaladas frescas y saludables'),
('Sopas', 'Sopas calientes y frías para todos los gustos'),
('Pasta', 'Platos de pasta con diferentes salsas');

INSERT INTO Productos (categoria_id, nombre, precio) VALUES
-- Entrantes
(1, 'Bruschetta', 5.00), (1, 'Calamares a la romana', 7.50), (1, 'Patatas bravas', 4.50), (1, 'Alitas de pollo', 6.00),
(1, 'Nachos con queso', 5.50), (1, 'Croquetas de jamón', 6.00), (1, 'Empanadillas', 5.00), (1, 'Tortilla de patatas', 4.00),
(1, 'Gambas al ajillo', 8.00), (1, 'Jamón ibérico', 12.00),
-- Refrescos
(2, 'Coca Cola', 2.00), (2, 'Fanta Naranja', 2.00), (2, 'Sprite', 2.00), (2, 'Agua Mineral', 1.50),
(2, 'Tónica', 2.00), (2, 'Nestea', 2.50), (2, 'Zumo de Naranja', 2.50), (2, 'Zumo de Piña', 2.50),
(2, 'Aquarius', 2.00), (2, 'Red Bull', 3.00),
-- Vinos
(3, 'Vino Tinto Rioja', 15.00), (3, 'Vino Blanco Albariño', 12.00), (3, 'Vino Rosado Navarra', 10.00), (3, 'Vino Tinto Ribera', 18.00),
(3, 'Vino Blanco Verdejo', 14.00), (3, 'Vino Rosado Penedés', 11.00), (3, 'Vino Tinto Priorat', 20.00), (3, 'Vino Blanco Rueda', 13.00),
(3, 'Cava Brut', 16.00), (3, 'Champagne', 25.00),
-- Cervezas
(4, 'Cerveza Heineken', 3.00), (4, 'Cerveza Corona', 3.50), (4, 'Cerveza Mahou', 2.50), (4, 'Cerveza Estrella Galicia', 2.50),
(4, 'Cerveza San Miguel', 2.50), (4, 'Cerveza Amstel', 2.50), (4, 'Cerveza Cruzcampo', 2.50), (4, 'Cerveza Budweiser', 3.00),
(4, 'Cerveza Guinness', 3.50), (4, 'Cerveza Stella Artois', 3.00),
-- Postres
(5, 'Tarta de queso', 4.00), (5, 'Flan de huevo', 3.50), (5, 'Helado de vainilla', 3.00), (5, 'Brownie con helado', 4.50),
(5, 'Tiramisú', 4.00), (5, 'Crema catalana', 3.50), (5, 'Mousse de chocolate', 4.00), (5, 'Fruta de temporada', 3.00),
(5, 'Tarta de chocolate', 4.50), (5, 'Helado de fresa', 3.00),
-- Carnes
(6, 'Entrecot de ternera', 15.00), (6, 'Solomillo de cerdo', 12.00), (6, 'Pollo asado', 10.00), (6, 'Cordero a la parrilla', 18.00),
(6, 'Chuleta de cerdo', 11.00), (6, 'Costillas de ternera', 16.00), (6, 'Filete de ternera', 14.00), (6, 'Hamburguesa gourmet', 12.00),
(6, 'Chuletón de buey', 20.00), (6, 'Pechuga de pollo a la parrilla', 11.00),
-- Pescados
(7, 'Salmón a la plancha', 13.00), (7, 'Merluza en salsa verde', 12.00), (7, 'Atún a la parrilla', 15.00), (7, 'Dorada al horno', 14.00),
(7, 'Lubina a la sal', 16.00), (7, 'Bacalao al pil pil', 15.00), (7, 'Gambas al ajillo', 12.00), (7, 'Mejillones a la marinera', 10.00),
(7, 'Pulpo a la gallega', 18.00), (7, 'Caldereta de marisco', 20.00),
-- Ensaladas
(8, 'Ensalada César', 7.00), (8, 'Ensalada mixta', 6.00), (8, 'Ensalada de pasta', 7.00), (8, 'Ensalada de pollo', 8.00),
(8, 'Ensalada de atún', 7.00), (8, 'Ensalada griega', 7.50), (8, 'Ensalada de quinoa', 8.00), (8, 'Ensalada caprese', 7.50),
(8, 'Ensalada de espinacas', 7.00), (8, 'Ensalada de frutas', 6.50),
-- Sopas
(9, 'Sopa de tomate', 4.00), (9, 'Sopa de pollo', 4.50), (9, 'Sopa de marisco', 5.00), (9, 'Crema de calabaza', 4.00),
(9, 'Sopa de verduras', 4.00), (9, 'Gazpacho', 3.50), (9, 'Sopa de cebolla', 4.00), (9, 'Caldo gallego', 4.50),
(9, 'Sopa de miso', 3.50), (9, 'Caldo de pollo', 4.00),
-- Pasta
(10, 'Espaguetis a la boloñesa', 8.00), (10, 'Macarrones con queso', 7.00), (10, 'Lasaña de carne', 9.00), (10, 'Raviolis de espinacas', 8.50),
(10, 'Tallarines al pesto', 8.00), (10, 'Penne all arrabbiata', 7.50), (10, 'Fettuccine Alfredo', 8.50), (10, 'Canelones de carne', 9.00),
(10, 'Gnocchi a la romana', 7.50), (10, 'Tagliatelle con trufa', 10.00);

INSERT INTO Tickets (mesa_id, fecha_hora) VALUES
(1, '2024-05-01 12:30:00'), (1, '2024-05-02 13:45:00'), (1, '2024-05-03 14:15:00'), (2, '2024-05-01 11:50:00'), (2, '2024-05-02 13:20:00'), 
(3, '2024-05-03 14:05:00'), (3, '2024-05-04 12:10:00'), (4, '2024-05-05 13:25:00'), (4, '2024-05-06 12:35:00'), (5, '2024-05-07 14:50:00'), 
(5, '2024-05-08 11:45:00'), (6, '2024-05-09 13:40:00'), (6, '2024-05-10 12:25:00'), (7, '2024-05-11 14:30:00'), (7, '2024-05-12 11:55:00'), 
(8, '2024-05-13 13:15:00'), (8, '2024-05-14 12:50:00'), (9, '2024-05-15 14:20:00'), (9, '2024-05-16 13:05:00'), (10, '2024-05-17 12:40:00'), 
(10, '2024-05-18 13:30:00'), (11, '2024-05-19 14:10:00'), (11, '2024-05-20 11:35:00'), (12, '2024-05-21 13:50:00'), (12, '2024-05-22 12:15:00'), 
(13, '2024-05-23 14:00:00'), (13, '2024-05-24 11:20:00'), (14, '2024-05-25 13:40:00'), (14, '2024-05-26 12:05:00');


INSERT INTO Ticket_Detalle (ticket_id, producto_id, cantidad) VALUES
(1, 1, 2), (1, 11, 1), (1, 21, 2), (1, 31, 1), (1, 41, 3), (1, 51, 2),
(2, 2, 1), (2, 12, 2), (2, 22, 3), (2, 32, 2), (2, 42, 1), (2, 52, 2),
(3, 3, 2), (3, 13, 1), (3, 23, 2), (3, 33, 3), (3, 43, 2), (3, 53, 1),
(4, 4, 3), (4, 14, 2), (4, 24, 1), (4, 34, 2), (4, 44, 3), (4, 54, 2),
(5, 5, 2), (5, 15, 1), (5, 25, 2), (5, 35, 1), (5, 45, 3), (5, 55, 2),
(6, 6, 1), (6, 16, 2), (6, 26, 3), (6, 36, 2), (6, 46, 1), (6, 56, 2),
(7, 7, 2), (7, 17, 1), (7, 27, 2), (7, 37, 3), (7, 47, 2), (7, 57, 1),
(8, 8, 3), (8, 18, 2), (8, 28, 1), (8, 38, 2), (8, 48, 3), (8, 58, 2),
(9, 9, 2), (9, 19, 1), (9, 29, 2), (9, 39, 1), (9, 49, 3), (9, 59, 2),
(10, 10, 1), (10, 20, 2), (10, 30, 3), (10, 40, 2), (10, 50, 1), (10, 60, 2),
(11, 1, 2), (11, 11, 1), (11, 21, 2), (11, 31, 3), (11, 41, 2), (11, 51, 1),
(12, 2, 1), (12, 12, 2), (12, 22, 3), (12, 32, 2), (12, 42, 1), (12, 52, 2),
(13, 3, 2), (13, 13, 1), (13, 23, 2), (13, 33, 3), (13, 43, 2), (13, 53, 1),
(14, 4, 3), (14, 14, 2), (14, 24, 1), (14, 34, 2), (14, 44, 3), (14, 54, 2),
(15, 5, 2), (15, 15, 1), (15, 25, 2), (15, 35, 1), (15, 45, 3), (15, 55, 2),
(16, 6, 1), (16, 16, 2), (16, 26, 3), (16, 36, 2), (16, 46, 1), (16, 56, 2),
(17, 7, 2), (17, 17, 1), (17, 27, 2), (17, 37, 3), (17, 47, 2), (17, 57, 1),
(18, 8, 3), (18, 18, 2), (18, 28, 1), (18, 38, 2), (18, 48, 3), (18, 58, 2),
(19, 9, 2), (19, 19, 1), (19, 29, 2), (19, 39, 1), (19, 49, 3), (19, 59, 2),
(20, 10, 1), (20, 20, 2), (20, 30, 3), (20, 40, 2), (20, 50, 1), (20, 60, 2),
(21, 1, 2), (21, 11, 1), (21, 21, 2), (21, 31, 3), (21, 41, 2), (21, 51, 1),
(22, 2, 1), (22, 12, 2), (22, 22, 3), (22, 32, 2), (22, 42, 1), (22, 52, 2),
(23, 3, 2), (23, 13, 1), (23, 23, 2), (23, 33, 3), (23, 43, 2), (23, 53, 1),
(24, 4, 3), (24, 14, 2), (24, 24, 1), (24, 34, 2), (24, 44, 3), (24, 54, 2),
(25, 5, 2), (25, 15, 1), (25, 25, 2), (25, 35, 1), (25, 45, 3), (25, 55, 2),
(26, 6, 1), (26, 16, 2), (26, 26, 3), (26, 36, 2), (26, 46, 1), (26, 56, 2),
(27, 7, 2), (27, 17, 1), (27, 27, 2), (27, 37, 3), (27, 47, 2), (27, 57, 1),
(28, 8, 3), (28, 18, 2), (28, 28, 1), (28, 38, 2), (28, 48, 3), (28, 58, 2);

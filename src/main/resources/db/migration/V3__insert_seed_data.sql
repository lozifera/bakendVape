-- Categorías
INSERT INTO categoria (nombre, created_at) VALUES
('Vapes Desechables', CURRENT_TIMESTAMP),
('Pods y Kits de Inicio', CURRENT_TIMESTAMP),
('Mods y Vaporizadores Avanzados', CURRENT_TIMESTAMP),
('Líquidos (E-Liquids)', CURRENT_TIMESTAMP),
('Sales de Nicotina', CURRENT_TIMESTAMP),
('Resistencias (Coils)', CURRENT_TIMESTAMP),
('Cartuchos y Pods de Repuesto', CURRENT_TIMESTAMP),
('Tanques y Atomizadores', CURRENT_TIMESTAMP),
('Accesorios', CURRENT_TIMESTAMP),
('Promociones y Ofertas', CURRENT_TIMESTAMP);

-- Marcas
INSERT INTO marcas (nombre, created_at) VALUES
('SMOK', CURRENT_TIMESTAMP),
('Voopoo', CURRENT_TIMESTAMP),
('GeekVape', CURRENT_TIMESTAMP),
('Vaporesso', CURRENT_TIMESTAMP),
('Uwell', CURRENT_TIMESTAMP),
('Oxva', CURRENT_TIMESTAMP),
('Lost Vape', CURRENT_TIMESTAMP),
('Elf Bar', CURRENT_TIMESTAMP),
('Geek Bar', CURRENT_TIMESTAMP),
('HQD', CURRENT_TIMESTAMP);

-- Atributos
INSERT INTO atributo (nombre, unidad, created_at) VALUES
('Sabor', NULL, CURRENT_TIMESTAMP),
('Puffs', 'unidades', CURRENT_TIMESTAMP),
('Nicotina', '%', CURRENT_TIMESTAMP),
('Batería', 'mAh', CURRENT_TIMESTAMP),
('Capacidad de Líquido', 'ml', CURRENT_TIMESTAMP),
('Puerto de Carga', NULL, CURRENT_TIMESTAMP),
('Potencia', 'W', CURRENT_TIMESTAMP),
('Resistencia', 'Ω', CURRENT_TIMESTAMP),
('Tipo de Resistencia', NULL, CURRENT_TIMESTAMP),
('Material de Resistencia', NULL, CURRENT_TIMESTAMP),
('Compatibilidad', NULL, CURRENT_TIMESTAMP),
('Voltaje', 'V', CURRENT_TIMESTAMP),
('Capacidad de Batería', 'mAh', CURRENT_TIMESTAMP),
('Tipo de Dispositivo', NULL, CURRENT_TIMESTAMP),
('Tipo de Nicotina', NULL, CURRENT_TIMESTAMP),
('Proporción VG', '%', CURRENT_TIMESTAMP),
('Proporción PG', '%', CURRENT_TIMESTAMP),
('Cantidad por Caja', 'unidades', CURRENT_TIMESTAMP),
('Color', NULL, CURRENT_TIMESTAMP),
('Dimensiones', 'cm', CURRENT_TIMESTAMP),
('Peso', 'g', CURRENT_TIMESTAMP),
('Sistema de Activación', NULL, CURRENT_TIMESTAMP),
('Pantalla', NULL, CURRENT_TIMESTAMP),
('Flujo de Aire Ajustable', NULL, CURRENT_TIMESTAMP),
('Recargable', NULL, CURRENT_TIMESTAMP),
('Reutilizable', NULL, CURRENT_TIMESTAMP),
('País de Origen', NULL, CURRENT_TIMESTAMP),
('Material', NULL, CURRENT_TIMESTAMP),
('Modelo Compatible', NULL, CURRENT_TIMESTAMP),
('Duración Estimada', 'días', CURRENT_TIMESTAMP);

-- V2__insert_roles.sql
-- Insertar roles iniciales del sistema con nombres profesionales

INSERT INTO rol (id_rol, nombre, created_at, updated_at)
VALUES
    (1, 'ROLE_ADMINISTRATOR', NOW(), NOW()),
    (2, 'ROLE_VENDOR', NOW(), NOW()),
    (3, 'ROLE_CLIENTE', NOW(), NOW()),
    (4, 'ROLE_VIP_CLIENTE', NOW(), NOW());

-- Comentarios:
-- ROLE_ADMINISTRATOR: acceso total, gestión de usuarios, reportes, auditoría.
-- ROLE_VENDOR: puede crear productos, gestionar inventario, ver pedidos asignados.
-- ROLE_CUSTOMER: usuario estándar, compra, carrito, historial de pedidos.
-- ROLE_PREMIUM_CUSTOMER: cliente VIP con descuentos, subastas exclusivas, puntos bonus.

INSERT INTO public.usuario (
    nombre,
    apellido,
    email,
    "password",
    es_vip,
    puntos_actuales,
    id_rol,
    created_at,
    updated_at
)
VALUES (
            'Leonel','Loza','leonel@gmail.com',
            -- Password: loza123456789
            '$2a$10$f35/zCy57zikBwMIxmr6veuriFj9tmsXEfHxnsfsxJFeQOC0eFyma',true,0,
            1,
            NOW(),
            NOW()
        );
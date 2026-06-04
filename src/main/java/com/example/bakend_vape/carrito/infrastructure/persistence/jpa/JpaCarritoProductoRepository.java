package com.example.bakend_vape.carrito.infrastructure.persistence.jpa;

import com.example.bakend_vape.carrito.infrastructure.persistence.entity.CarritoProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCarritoProductoRepository extends JpaRepository<CarritoProductoEntity, Long> {

    List<CarritoProductoEntity> findByCarritoIdCarrito(Long idCarrito);

}

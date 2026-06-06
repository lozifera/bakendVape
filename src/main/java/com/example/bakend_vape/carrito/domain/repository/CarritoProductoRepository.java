package com.example.bakend_vape.carrito.domain.repository;

import com.example.bakend_vape.carrito.domain.model.CarritoProducto;

import java.util.List;
import java.util.Optional;

public interface CarritoProductoRepository {

    CarritoProducto save(CarritoProducto carritoProducto);

    Optional<CarritoProducto> findById(Long idCarritoProducto);

    List<CarritoProducto> findAll();

    List<CarritoProducto> findByCarritoId(Long idCarrito);

    List<CarritoProducto> findByProductoId(Long idProducto);

    void deleteById(Long idCarritoProducto);

    void deleteByCarritoId(Long idCarrito);

    boolean existsByCarritoIdAndProductoId(Long idCarrito, Long idProducto);
}
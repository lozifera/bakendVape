package com.example.bakend_vape.producto.domain.repository;

import com.example.bakend_vape.producto.domain.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository {

    Producto save(Producto producto);

    Optional<Producto> findById(Long id);

    List<Producto> findAll();

    List<Producto> findByCategoriaId(Long categoriaId);

    Optional<Producto> findByNombre(String nombre);

    List<Producto> findByMarcaId(Long marcaId);

    Boolean existsByNombre(String nombre);

    void delete(Long id);

}

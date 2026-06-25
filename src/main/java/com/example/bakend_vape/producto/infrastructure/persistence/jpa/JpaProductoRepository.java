package com.example.bakend_vape.producto.infrastructure.persistence.jpa;

import com.example.bakend_vape.producto.infrastructure.persistence.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaProductoRepository extends JpaRepository<ProductoEntity, Long> {

    Optional<ProductoEntity> findByNombre(String nombre);

    List<ProductoEntity> findByCategoriaIdCategoria(Long categoriaId);

    List<ProductoEntity> findByMarcaIdMarca(Long marcaId);

    List<ProductoEntity> findByNombreContainingIgnoreCase(String nombre);

    boolean existsByNombre(String  nombre);

}

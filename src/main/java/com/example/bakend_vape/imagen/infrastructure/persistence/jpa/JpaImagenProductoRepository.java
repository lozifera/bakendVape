package com.example.bakend_vape.imagen.infrastructure.persistence.jpa;

import com.example.bakend_vape.imagen.infrastructure.persistence.entity.ImagenProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaImagenProductoRepository extends JpaRepository<ImagenProductoEntity, Long> {

    List<ImagenProductoEntity> findByProductoIdProducto(Long idProducto);

}

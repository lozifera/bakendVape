package com.example.bakend_vape.imagen.infrastructure.persistence.jpa;

import com.example.bakend_vape.imagen.infrastructure.persistence.entity.ImagenCategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaImagenCategoriaRepository extends JpaRepository<ImagenCategoriaEntity, Long> {

    List<ImagenCategoriaEntity> findByCategoriaIdCategoria(Long idCategoria);

    List<ImagenCategoriaEntity> findByImagenIdImagen(Long idImagen);

}

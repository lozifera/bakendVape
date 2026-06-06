package com.example.bakend_vape.imagen.domain.repository;

import com.example.bakend_vape.imagen.domain.model.ImagenCategoria;

import java.util.List;
import java.util.Optional;

public interface ImagenCategoriaRepository {

    ImagenCategoria save(ImagenCategoria imagenCategoria);

    Optional<ImagenCategoria> findById(Long idImagenCategoria);

    List<ImagenCategoria> findAll();

    void delete(Long idImagenCategoria);

    List<ImagenCategoria> findByCategoriaId(Long idCategoria);

    List<ImagenCategoria> findByImagenId(Long idImagen);

}

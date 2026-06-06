package com.example.bakend_vape.imagen.domain.repository;

import com.example.bakend_vape.imagen.domain.model.ImagenProducto;

import java.util.List;
import java.util.Optional;

public interface ImagenProductoRepository {

    ImagenProducto save(ImagenProducto imagenProducto);

    Optional<ImagenProducto> findById(Long idImagenProducto);

    List<ImagenProducto> findAll();

    void deleteById(Long idImagenProducto);

    List<ImagenProducto> findByProductoId(Long idProducto);

    List<ImagenProducto> findByImagenId(Long idImagen);

}

package com.example.bakend_vape.imagen.domain.repository;

import com.example.bakend_vape.imagen.domain.model.Imagen;

import java.util.List;
import java.util.Optional;


public interface ImagenRepository {

    Imagen seve(Imagen imagen);

    Optional<Imagen> findById(Long id);

    List<Imagen> findAll();

    void delete(Long id);

}

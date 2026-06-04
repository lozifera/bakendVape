package com.example.bakend_vape.marca.domain.repository;

import com.example.bakend_vape.marca.domain.model.Marca;

import java.util.List;
import java.util.Optional;

public interface MarcaRepository {

    Marca save(Marca marca);

    Optional<Marca> findById(Long id);

    Optional<Marca> findByNombre(String nombre);

    List<Marca> findAll();

    void delete(Long id);

}

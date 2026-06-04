package com.example.bakend_vape.categoria.domain.repository;

import com.example.bakend_vape.categoria.domain.model.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository {

    Categoria save(Categoria categoria);

    Optional<Categoria> findById(Long id);

    Optional<Categoria> findByNombre(String nombre);

    List<Categoria> findAll();

    void delete(Long id);

}

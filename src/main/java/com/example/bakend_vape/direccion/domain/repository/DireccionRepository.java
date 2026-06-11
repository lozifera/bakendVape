package com.example.bakend_vape.direccion.domain.repository;

import com.example.bakend_vape.direccion.domain.model.Direccion;

import java.util.List;
import java.util.Optional;

public interface DireccionRepository {

    Direccion save(Direccion direccion);

    Optional<Direccion> findById(Long id);

    List<Direccion> findByUsuario(Long idUsuario);

    void delete(Long id);

    Long countByUsuario(Long idUsuario);

}

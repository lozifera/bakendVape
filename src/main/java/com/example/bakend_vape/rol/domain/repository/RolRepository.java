package com.example.bakend_vape.rol.domain.repository;

import com.example.bakend_vape.rol.domain.model.Rol;

import java.util.List;
import java.util.Optional;

public interface RolRepository {

    Rol save(Rol rol);

    Optional<Rol> findById(Long id);

    Optional<Rol> findByNombre(String nombre);

    List<Rol> findAll();

}

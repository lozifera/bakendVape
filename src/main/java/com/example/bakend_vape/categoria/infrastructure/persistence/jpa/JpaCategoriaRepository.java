package com.example.bakend_vape.categoria.infrastructure.persistence.jpa;

import com.example.bakend_vape.categoria.infrastructure.persistence.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCategoriaRepository extends JpaRepository<CategoriaEntity, Long> {

    Optional<CategoriaEntity> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

}

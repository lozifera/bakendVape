package com.example.bakend_vape.marca.infrastructure.persistence.jpa;

import com.example.bakend_vape.marca.infrastructure.persistence.entity.MarcaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaMarcaRepository extends JpaRepository<MarcaEntity, Long> {

    Optional<MarcaEntity> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

}

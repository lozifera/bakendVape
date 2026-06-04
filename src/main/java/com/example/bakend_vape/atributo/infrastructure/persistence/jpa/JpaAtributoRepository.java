package com.example.bakend_vape.atributo.infrastructure.persistence.jpa;

import com.example.bakend_vape.atributo.infrastructure.persistence.entity.AtributoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaAtributoRepository extends JpaRepository<AtributoEntity,Long> {

    Optional<AtributoEntity> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

}

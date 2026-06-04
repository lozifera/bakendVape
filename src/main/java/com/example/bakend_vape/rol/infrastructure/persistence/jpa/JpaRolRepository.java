package com.example.bakend_vape.rol.infrastructure.persistence.jpa;

import com.example.bakend_vape.rol.infrastructure.persistence.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaRolRepository extends JpaRepository<RolEntity, Long> {

    Optional<RolEntity> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

}

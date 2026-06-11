package com.example.bakend_vape.imagen.infrastructure.persistence.jpa;

import com.example.bakend_vape.imagen.infrastructure.persistence.entity.ImagenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaImagenRepository extends JpaRepository<ImagenEntity, Long> {

    Optional<ImagenEntity> findByUrl(String url);
}

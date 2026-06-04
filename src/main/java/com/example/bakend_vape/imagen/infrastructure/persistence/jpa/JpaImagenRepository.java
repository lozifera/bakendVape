package com.example.bakend_vape.imagen.infrastructure.persistence.jpa;

import com.example.bakend_vape.imagen.infrastructure.persistence.entity.ImagenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaImagenRepository extends JpaRepository<ImagenEntity, Long> {
}

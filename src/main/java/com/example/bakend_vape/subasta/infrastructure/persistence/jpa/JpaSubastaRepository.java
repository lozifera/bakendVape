package com.example.bakend_vape.subasta.infrastructure.persistence.jpa;

import com.example.bakend_vape.subasta.infrastructure.persistence.entity.SubastaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSubastaRepository extends JpaRepository<SubastaEntity, Long> {
}

package com.example.bakend_vape.subasta.infrastructure.persistence.jpa;

import com.example.bakend_vape.subasta.infrastructure.persistence.entity.OfertaSubastaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaOfertaSubastaRepository extends JpaRepository<OfertaSubastaEntity, Long> {

    List<OfertaSubastaEntity> findBySubastaIdSubasta(Long idSubasta);

}

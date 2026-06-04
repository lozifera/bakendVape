package com.example.bakend_vape.aviso.infrastructure.persistence.jpa;

import com.example.bakend_vape.aviso.infrastructure.persistence.entity.AvisoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaAvisoRepository extends JpaRepository<AvisoEntity, Long> {

    List<AvisoEntity> findBySoloVip(Boolean soloVip);

    List<AvisoEntity> findBySoloVipTrue();

    List<AvisoEntity> findByUsuarioIdUsuario(Long idUsuario);

    Page<AvisoEntity> findAll(Pageable pageable);

}

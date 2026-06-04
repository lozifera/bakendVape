package com.example.bakend_vape.auditoria.infrastructure.persistence.jpa;

import com.example.bakend_vape.auditoria.infrastructure.persistence.entity.AuditoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaAuditoriaRepository extends JpaRepository<AuditoriaEntity, Long> {

    List<AuditoriaEntity> findByUsuarioIdUsuario(Long idUsuario);

}

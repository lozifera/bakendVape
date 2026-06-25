package com.example.bakend_vape.subasta.infrastructure.persistence.jpa;

import com.example.bakend_vape.subasta.infrastructure.persistence.entity.SubastaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaSubastaRepository extends JpaRepository<SubastaEntity, Long> {

    @Query(value = "SELECT * FROM subastas WHERE estado = 'ACTIVA' AND (created_at + (duracion_minutos * INTERVAL '1 minute')) <= NOW()", nativeQuery = true)
    List<SubastaEntity> findExpiredActiveSubastas();
}

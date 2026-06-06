package com.example.bakend_vape.auditoria.domain.repository;

import com.example.bakend_vape.auditoria.domain.model.Auditoria;

import java.util.List;
import java.util.Optional;

public interface AuditoriaRepository {

    Auditoria save(Auditoria auditoria);

    Optional<Auditoria> findById(Long id);

    List<Auditoria> findAll();

}

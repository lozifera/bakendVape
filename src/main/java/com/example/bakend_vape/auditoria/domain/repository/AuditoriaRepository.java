package com.example.bakend_vape.auditoria.domain.repository;

import com.example.bakend_vape.auditoria.domain.model.Audotoria;

import java.util.List;
import java.util.Optional;

public interface AuditoriaRepository {

    Audotoria save(Audotoria auditoria);

    Optional<Audotoria> findById(Long id);

    List<Audotoria> findAll();

}

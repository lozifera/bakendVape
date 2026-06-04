package com.example.bakend_vape.aviso.domain.repository;

import com.example.bakend_vape.aviso.domain.model.Aviso;

import java.util.List;
import java.util.Optional;

public interface AvisoRepository {

    Aviso save(Aviso aviso);

    Optional<Aviso> findById(Long id);

    List<Aviso> findAll();

    void delete(Long id);

    List<Aviso> lists();

    List<Aviso> listActivos();

}

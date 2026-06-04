package com.example.bakend_vape.subasta.domain.repository;

import com.example.bakend_vape.subasta.domain.model.Subasta;

import java.util.List;
import java.util.Optional;

public interface SubastaRepository {

    Subasta save(Subasta subasta);

    Optional<Subasta> findById(Long id);

    List<Subasta> findAll();

}

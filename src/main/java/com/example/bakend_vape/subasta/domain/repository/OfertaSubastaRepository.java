package com.example.bakend_vape.subasta.domain.repository;

import com.example.bakend_vape.subasta.domain.model.OfertaSubasta;

import java.util.List;
import java.util.Optional;

public interface OfertaSubastaRepository {

    OfertaSubasta save(OfertaSubasta ofertaSubasta);

    Optional<OfertaSubasta> findById(Long idOfertaSubasta);

    List<OfertaSubasta> findBySubastaId(Long idSubasta);

    List<OfertaSubasta> findAll();

    void deleteById(Long idOfertaSubasta);


}

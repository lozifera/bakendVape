package com.example.bakend_vape.atributo.domain.repository;

import com.example.bakend_vape.atributo.domain.model.Atributo;

import java.util.List;
import java.util.Optional;

public interface AtributoRepository {

    Atributo save(Atributo atributo);

    Optional<Atributo> findById(Long id);

    List<Atributo> findAll();

    void delete(Long id);



}

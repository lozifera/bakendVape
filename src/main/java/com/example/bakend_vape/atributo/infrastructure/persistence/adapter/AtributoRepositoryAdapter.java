package com.example.bakend_vape.atributo.infrastructure.persistence.adapter;

import com.example.bakend_vape.atributo.domain.model.Atributo;
import com.example.bakend_vape.atributo.domain.repository.AtributoRepository;
import com.example.bakend_vape.atributo.infrastructure.mapper.AtributoMapper;
import com.example.bakend_vape.atributo.infrastructure.persistence.entity.AtributoEntity;
import com.example.bakend_vape.atributo.infrastructure.persistence.jpa.JpaAtributoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AtributoRepositoryAdapter implements AtributoRepository {

    private final JpaAtributoRepository jpa;
    private final AtributoMapper mapper;

    public AtributoRepositoryAdapter(JpaAtributoRepository jpa) {
        this.jpa = jpa;
        this.mapper = new AtributoMapper();
    }

    @Override
    public Atributo save(Atributo atributo) {

        AtributoEntity entity = mapper.toEntity(atributo);
        AtributoEntity saved = jpa.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Atributo> findById(Long id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Atributo> findAll() {
        return jpa.findAll().stream().map(mapper :: toDomain).toList();
    }

    @Override
    public void delete(Long id) {
        jpa.deleteById(id);
    }
}

package com.example.bakend_vape.aviso.infrastructure.persistence.adapter;

import com.example.bakend_vape.aviso.domain.model.Aviso;
import com.example.bakend_vape.aviso.domain.repository.AvisoRepository;
import com.example.bakend_vape.aviso.infrastructure.mapper.AvisoMapper;
import com.example.bakend_vape.aviso.infrastructure.persistence.entity.AvisoEntity;
import com.example.bakend_vape.aviso.infrastructure.persistence.jpa.JpaAvisoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AvisoRepositoryAdapter implements AvisoRepository {

    private final JpaAvisoRepository jpa;
    private final AvisoMapper mapper;

    public AvisoRepositoryAdapter(JpaAvisoRepository jpa) {
        this.jpa = jpa;
        this.mapper = new AvisoMapper();
    }

    @Override
    public Aviso save(Aviso aviso) {

        AvisoEntity entity = mapper.toEntity(aviso);
        AvisoEntity saved = jpa.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Aviso> findById(Long id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Aviso> findAll() {
        return jpa.findAll().stream().map(mapper :: toDomain).toList();
    }

    @Override
    public void delete(Long id) {

        jpa.deleteById(id);

    }

    @Override
    public List<Aviso> lists() {
        return jpa.findAll().stream().map(mapper :: toDomain).toList();
    }

    @Override
    public List<Aviso> listActivos() {
        return jpa.findBySoloVipTrue().stream().map(mapper :: toDomain).toList();
    }
}

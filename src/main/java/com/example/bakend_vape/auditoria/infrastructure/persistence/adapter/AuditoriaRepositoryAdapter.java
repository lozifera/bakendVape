package com.example.bakend_vape.auditoria.infrastructure.persistence.adapter;

import com.example.bakend_vape.auditoria.domain.model.Auditoria;
import com.example.bakend_vape.auditoria.domain.repository.AuditoriaRepository;
import com.example.bakend_vape.auditoria.infrastructure.mapper.AuditoriaMapper;
import com.example.bakend_vape.auditoria.infrastructure.persistence.entity.AuditoriaEntity;
import com.example.bakend_vape.auditoria.infrastructure.persistence.jpa.JpaAuditoriaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuditoriaRepositoryAdapter implements AuditoriaRepository {

    private final JpaAuditoriaRepository jpa;
    private final AuditoriaMapper mapper;

    public AuditoriaRepositoryAdapter(JpaAuditoriaRepository jpa) {
        this.jpa = jpa;
        this.mapper = new AuditoriaMapper();
    }

    @Override
    public Auditoria save(Auditoria auditoria) {

        AuditoriaEntity entity = mapper.toEntity(auditoria);
        AuditoriaEntity saved = jpa.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Auditoria> findById(Long id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Auditoria> findAll() {
        return jpa.findAll().stream().map(mapper :: toDomain).toList();
    }
}

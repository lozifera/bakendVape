package com.example.bakend_vape.subasta.infrastructure.persistence.adapter;

import com.example.bakend_vape.subasta.domain.model.Subasta;
import com.example.bakend_vape.subasta.domain.repository.SubastaRepository;
import com.example.bakend_vape.subasta.infrastructure.mapper.SubastaMapper;
import com.example.bakend_vape.subasta.infrastructure.persistence.entity.SubastaEntity;
import com.example.bakend_vape.subasta.infrastructure.persistence.jpa.JpaSubastaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SubastaRepositoryAdapter implements SubastaRepository {

    private final JpaSubastaRepository jpa;
    private final SubastaMapper mapper;

    public SubastaRepositoryAdapter (JpaSubastaRepository jpa) {
        this.jpa = jpa;
        this.mapper = new SubastaMapper();
    }

    @Override
    public Subasta save(Subasta subasta) {

        SubastaEntity entity = mapper.toEntity(subasta);
        SubastaEntity saved = jpa.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Subasta> findById(Long id) {
        return jpa.findById(id).map(mapper :: toDomain);
    }

    @Override
    public List<Subasta> findAll() {
        return jpa.findAll().stream().map(mapper :: toDomain).toList();
    }

    @Override
    public List<Subasta> findExpiredActive() {
        return jpa.findExpiredActiveSubastas().stream()
                .map(mapper::toDomain)
                .toList();
    }
}

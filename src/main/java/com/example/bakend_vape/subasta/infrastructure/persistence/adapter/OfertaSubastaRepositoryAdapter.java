package com.example.bakend_vape.subasta.infrastructure.persistence.adapter;

import com.example.bakend_vape.subasta.domain.model.OfertaSubasta;
import com.example.bakend_vape.subasta.domain.repository.OfertaSubastaRepository;
import com.example.bakend_vape.subasta.infrastructure.mapper.OfertaSubastaMapper;
import com.example.bakend_vape.subasta.infrastructure.persistence.entity.OfertaSubastaEntity;
import com.example.bakend_vape.subasta.infrastructure.persistence.jpa.JpaOfertaSubastaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OfertaSubastaRepositoryAdapter implements OfertaSubastaRepository {

    private final JpaOfertaSubastaRepository jpa;
    private final OfertaSubastaMapper mapper;

    public OfertaSubastaRepositoryAdapter(JpaOfertaSubastaRepository jpa) {
        this.jpa = jpa;
        this.mapper = new OfertaSubastaMapper();
    }

    @Override
    public OfertaSubasta save(OfertaSubasta ofertaSubasta) {

        OfertaSubastaEntity entity = mapper.toEntity(ofertaSubasta);
        OfertaSubastaEntity saved = jpa.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<OfertaSubasta> findById(Long idOfertaSubasta) {
        return jpa.findById(idOfertaSubasta).map(mapper :: toDomain);
    }

    @Override
    public List<OfertaSubasta> findBySubastaId(Long idSubasta) {
        return jpa.findBySubastaIdSubasta(idSubasta).stream().map(mapper :: toDomain).toList();
    }

    @Override
    public List<OfertaSubasta> findAll() {
        return jpa.findAll().stream().map(mapper :: toDomain).toList();
    }

    @Override
    public void deleteById(Long idOfertaSubasta) {

        jpa.deleteById(idOfertaSubasta);

    }
}

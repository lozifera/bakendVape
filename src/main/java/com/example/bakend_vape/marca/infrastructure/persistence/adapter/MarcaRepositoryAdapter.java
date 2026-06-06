package com.example.bakend_vape.marca.infrastructure.persistence.adapter;

import com.example.bakend_vape.marca.domain.model.Marca;
import com.example.bakend_vape.marca.domain.repository.MarcaRepository;
import com.example.bakend_vape.marca.infrastructure.mapper.MarcaMapper;
import com.example.bakend_vape.marca.infrastructure.persistence.entity.MarcaEntity;
import com.example.bakend_vape.marca.infrastructure.persistence.jpa.JpaMarcaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MarcaRepositoryAdapter implements MarcaRepository {

    private final JpaMarcaRepository jpa;
    private final MarcaMapper mapper;

    public MarcaRepositoryAdapter(JpaMarcaRepository jpa) {
        this.jpa = jpa;
        this.mapper = new MarcaMapper();
    }

    @Override
    public Marca save(Marca marca) {

        MarcaEntity entity = mapper.toEntity(marca);
        MarcaEntity saved = jpa.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Marca> findById(Long id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Marca> findByNombre(String nombre) {
        return jpa.findByNombre(nombre).map(mapper::toDomain);
    }

    @Override
    public List<Marca> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void delete(Long id) {

        jpa.deleteById(id);

    }
}

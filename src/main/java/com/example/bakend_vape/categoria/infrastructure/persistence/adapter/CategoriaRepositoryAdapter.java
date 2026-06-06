package com.example.bakend_vape.categoria.infrastructure.persistence.adapter;

import com.example.bakend_vape.carrito.infrastructure.persistence.jpa.JpaCarritoRepository;
import com.example.bakend_vape.categoria.domain.model.Categoria;
import com.example.bakend_vape.categoria.domain.repository.CategoriaRepository;
import com.example.bakend_vape.categoria.infrastructure.mapper.CategoriaMapper;
import com.example.bakend_vape.categoria.infrastructure.persistence.entity.CategoriaEntity;
import com.example.bakend_vape.categoria.infrastructure.persistence.jpa.JpaCategoriaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoriaRepositoryAdapter implements CategoriaRepository {

    private final JpaCategoriaRepository jpa;
    private final CategoriaMapper mapper;

    public CategoriaRepositoryAdapter (JpaCategoriaRepository jpa) {
        this.jpa = jpa;
        this.mapper = new CategoriaMapper();
    }

    @Override
    public Categoria save(Categoria categoria) {

        CategoriaEntity entity = mapper.toEntity(categoria);
        CategoriaEntity saved = jpa.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Categoria> findById(Long id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Categoria> findByNombre(String nombre) {
        return jpa.findByNombre(nombre).map(mapper::toDomain);
    }

    @Override
    public List<Categoria> findAll() {
        return jpa.findAll().stream().map(mapper :: toDomain).toList();
    }

    @Override
    public void delete(Long id) {

        jpa.deleteById(id);

    }
}

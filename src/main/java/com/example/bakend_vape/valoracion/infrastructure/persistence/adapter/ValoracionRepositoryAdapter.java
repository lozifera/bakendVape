package com.example.bakend_vape.valoracion.infrastructure.persistence.adapter;

import com.example.bakend_vape.valoracion.domain.model.Valoracion;
import com.example.bakend_vape.valoracion.domain.repository.ValoracionRepository;
import com.example.bakend_vape.valoracion.infrastructure.mapper.ValoracionMapper;
import com.example.bakend_vape.valoracion.infrastructure.persistence.entity.ValoracionEntity;
import com.example.bakend_vape.valoracion.infrastructure.persistence.jpa.JpaValoracionRepository;

import java.util.List;
import java.util.Optional;

public class ValoracionRepositoryAdapter implements ValoracionRepository {

    private final JpaValoracionRepository jpa;
    private final ValoracionMapper mapper;

        public ValoracionRepositoryAdapter(JpaValoracionRepository jpa) {
            this.jpa = jpa;
            this.mapper = new ValoracionMapper();
        }

    @Override
    public Valoracion save(Valoracion valoracion) {

        ValoracionEntity entity = mapper.toEntity(valoracion);
        ValoracionEntity saved = jpa.save(entity);
        return mapper.toDomain(saved);

    }

    @Override
    public Optional<Valoracion> findById(Long id) {
        return jpa.findById(id).map(mapper :: toDomain);
    }

    @Override
    public List<Valoracion> findByProductoId(Long productoId) {
        return jpa.findByProductoIdProducto(productoId).stream().map(mapper :: toDomain).toList();
    }

    @Override
    public List<Valoracion> findByUsuarioId(Long usuarioId) {
        return jpa.findByUsuarioIdUsuario(usuarioId).stream().map(mapper :: toDomain).toList();
    }
}

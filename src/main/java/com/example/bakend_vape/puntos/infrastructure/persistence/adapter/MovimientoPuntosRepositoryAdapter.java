package com.example.bakend_vape.puntos.infrastructure.persistence.adapter;

import com.example.bakend_vape.puntos.domain.model.MovimientoPuntos;
import com.example.bakend_vape.puntos.domain.repository.MovimientoPuntosRepository;
import com.example.bakend_vape.puntos.infrastructure.mapper.MovimientoPuntosMapper;
import com.example.bakend_vape.puntos.infrastructure.persistence.entity.MovimientoPuntosEntity;
import com.example.bakend_vape.puntos.infrastructure.persistence.jpa.JpaMovimientoPuntosRepository;

import java.util.List;

public class MovimientoPuntosRepositoryAdapter implements MovimientoPuntosRepository {

    private final JpaMovimientoPuntosRepository jpa;
    private  final MovimientoPuntosMapper mapper;

    public MovimientoPuntosRepositoryAdapter(JpaMovimientoPuntosRepository jpa) {
        this.jpa = jpa;
        this.mapper = new MovimientoPuntosMapper();
    }

    @Override
    public MovimientoPuntos save(MovimientoPuntos movimientoPuntos) {

        MovimientoPuntosEntity entity = mapper.toEntity(movimientoPuntos);
        MovimientoPuntosEntity saved = jpa.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public List<MovimientoPuntos> findByUsuarioId(Long usuarioId) {
        return jpa.findByUsuarioIdUsuario(usuarioId).stream().map(mapper :: toDomain).toList();
    }
}

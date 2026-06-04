package com.example.bakend_vape.puntos.domain.repository;

import com.example.bakend_vape.puntos.domain.model.MovimientoPuntos;

import java.util.List;

public interface MovimientoPuntosRepository {

    MovimientoPuntos save(MovimientoPuntos movimientoPuntos);

    List<MovimientoPuntos> findByUsuarioId(Long usuarioId);

}

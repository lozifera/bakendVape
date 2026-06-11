package com.example.bakend_vape.puntos.application.usecase;

import com.example.bakend_vape.puntos.application.dto.MovimientoPuntosResponse;

import java.util.List;

public interface ObtenerMovimientosPuntosUseCase {
    List<MovimientoPuntosResponse> execute(Long idUsuario);
}

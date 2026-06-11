package com.example.bakend_vape.valoracion.application.usecase;

import com.example.bakend_vape.valoracion.application.dto.ValoracionResponse;

import java.util.List;

public interface ObtenerValoracionesPorProductoUseCase {
    List<ValoracionResponse> execute(Long idProducto);
}

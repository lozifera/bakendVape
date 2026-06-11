package com.example.bakend_vape.valoracion.application.usecase;

import com.example.bakend_vape.valoracion.application.dto.CrearValoracionRequest;
import com.example.bakend_vape.valoracion.application.dto.ValoracionResponse;

public interface CrearValoracionUseCase {
    ValoracionResponse execute(CrearValoracionRequest request);
}

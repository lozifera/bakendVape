package com.example.bakend_vape.marca.application.usecase;

import com.example.bakend_vape.marca.application.dto.MarcaResponse;

public interface ObtenerMarcaPorIdUseCase {
    MarcaResponse execute(Long idMarca);
}


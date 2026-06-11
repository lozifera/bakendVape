package com.example.bakend_vape.marca.application.usecase;

import com.example.bakend_vape.marca.application.dto.ActualizarMarcaRequest;
import com.example.bakend_vape.marca.application.dto.MarcaResponse;

public interface ActualizarMarcaUseCase {
    MarcaResponse execute(Long idMarca, ActualizarMarcaRequest request);
}


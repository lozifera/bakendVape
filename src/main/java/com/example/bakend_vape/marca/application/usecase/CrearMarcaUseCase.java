package com.example.bakend_vape.marca.application.usecase;

import com.example.bakend_vape.marca.application.dto.CrearMarcaRequest;
import com.example.bakend_vape.marca.application.dto.MarcaResponse;

public interface CrearMarcaUseCase {
    MarcaResponse execute(CrearMarcaRequest request);
}


package com.example.bakend_vape.marca.application.usecase;

import com.example.bakend_vape.marca.application.dto.MarcaResponse;

import java.util.List;

public interface ObtenerMarcasUseCase {
    List<MarcaResponse> execute();
}


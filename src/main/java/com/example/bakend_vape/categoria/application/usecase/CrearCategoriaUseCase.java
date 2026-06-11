package com.example.bakend_vape.categoria.application.usecase;

import com.example.bakend_vape.categoria.application.dto.CrearCategoriaRequest;
import com.example.bakend_vape.categoria.application.dto.CategoriaResponse;

public interface CrearCategoriaUseCase {
    CategoriaResponse execute(CrearCategoriaRequest request);
}


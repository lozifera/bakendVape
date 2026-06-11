package com.example.bakend_vape.categoria.application.usecase;

import com.example.bakend_vape.categoria.application.dto.ActualizarCategoriaRequest;
import com.example.bakend_vape.categoria.application.dto.CategoriaResponse;

public interface ActualizarCategoriaUseCase {
    CategoriaResponse execute(Long idCategoria, ActualizarCategoriaRequest request);
}


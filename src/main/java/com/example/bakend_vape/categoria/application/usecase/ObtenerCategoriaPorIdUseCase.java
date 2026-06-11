package com.example.bakend_vape.categoria.application.usecase;

import com.example.bakend_vape.categoria.application.dto.CategoriaResponse;

public interface ObtenerCategoriaPorIdUseCase {
    CategoriaResponse execute(Long idCategoria);
}


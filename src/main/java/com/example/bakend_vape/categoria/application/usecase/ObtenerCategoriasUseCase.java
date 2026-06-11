package com.example.bakend_vape.categoria.application.usecase;

import com.example.bakend_vape.categoria.application.dto.CategoriaResponse;

import java.util.List;

public interface ObtenerCategoriasUseCase {
    List<CategoriaResponse> execute();
}


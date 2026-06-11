package com.example.bakend_vape.producto.application.usecase;

import com.example.bakend_vape.producto.application.dto.ProductoResponse;

public interface ObtenerProductoPorIdUseCase {
    ProductoResponse execute(Long id);
}

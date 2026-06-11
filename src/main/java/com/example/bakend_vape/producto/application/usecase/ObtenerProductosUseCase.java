package com.example.bakend_vape.producto.application.usecase;

import com.example.bakend_vape.producto.application.dto.ProductoResponse;

import java.util.List;

public interface ObtenerProductosUseCase {
    List<ProductoResponse> execute();
}

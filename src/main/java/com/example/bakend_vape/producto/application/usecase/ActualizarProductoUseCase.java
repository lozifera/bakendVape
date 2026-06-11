package com.example.bakend_vape.producto.application.usecase;

import com.example.bakend_vape.producto.application.dto.ActualizarProductoRequest;
import com.example.bakend_vape.producto.application.dto.ProductoResponse;

public interface ActualizarProductoUseCase {
    ProductoResponse execute(Long id, ActualizarProductoRequest request);
}

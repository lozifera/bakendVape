package com.example.bakend_vape.producto.application.usecase;

import com.example.bakend_vape.producto.application.dto.CrearProductoRequest;
import com.example.bakend_vape.producto.application.dto.ProductoResponse;

public interface CrearProductoUseCase {

    ProductoResponse execute(CrearProductoRequest request);

}

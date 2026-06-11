package com.example.bakend_vape.producto.application.usecase;

import com.example.bakend_vape.producto.application.dto.ProductoRankingResponse;

import java.util.List;

public interface ObtenerRankingProductosUseCase {
    /**
     * @param limite cantidad de productos a devolver (por defecto 10)
     */
    List<ProductoRankingResponse> execute(int limite);
}

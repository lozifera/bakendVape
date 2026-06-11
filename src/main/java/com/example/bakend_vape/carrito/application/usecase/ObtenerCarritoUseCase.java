package com.example.bakend_vape.carrito.application.usecase;

import com.example.bakend_vape.carrito.application.dto.CarritoResponse;

public interface ObtenerCarritoUseCase {
    CarritoResponse execute(Long idUsuario);
}

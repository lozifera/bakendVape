package com.example.bakend_vape.carrito.application.usecase;

import com.example.bakend_vape.carrito.application.dto.AgregarProductoCarritoRequest;
import com.example.bakend_vape.carrito.application.dto.CarritoResponse;

public interface AgregarProductoCarritoUseCase {
    CarritoResponse execute(Long idUsuario, AgregarProductoCarritoRequest request);
}

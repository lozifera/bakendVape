package com.example.bakend_vape.carrito.application.usecase;

import com.example.bakend_vape.carrito.application.dto.ActualizarCantidadRequest;
import com.example.bakend_vape.carrito.application.dto.CarritoResponse;

public interface ActualizarCantidadCarritoUseCase {
    /**
     * @param idUsuario  null si el carrito es anónimo
     * @param sessionId  null si el carrito es de un usuario autenticado
     */
    CarritoResponse execute(Long idUsuario, Long idCarritoProducto,
                            ActualizarCantidadRequest request, String sessionId);
}

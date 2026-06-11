package com.example.bakend_vape.carrito.application.usecase;

public interface EliminarProductoCarritoUseCase {
    /**
     * @param idUsuario null si el carrito es anónimo
     * @param sessionId null si el carrito es de un usuario autenticado
     */
    void execute(Long idUsuario, Long idCarritoProducto, String sessionId);
}

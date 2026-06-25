package com.example.bakend_vape.pedido.application.usecase;

import com.example.bakend_vape.pedido.application.dto.ActualizarEstadoPedidoRequest;
import com.example.bakend_vape.pedido.application.dto.PedidoResponse;

public interface ActualizarEstadoPedidoUseCase {
    PedidoResponse execute(Long idPedido, ActualizarEstadoPedidoRequest request);
}

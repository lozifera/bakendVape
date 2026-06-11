package com.example.bakend_vape.pedido.application.usecase;

import com.example.bakend_vape.pedido.application.dto.PedidoResponse;

public interface ObtenerPedidoPorIdUseCase {
    PedidoResponse execute(Long idPedido);
}

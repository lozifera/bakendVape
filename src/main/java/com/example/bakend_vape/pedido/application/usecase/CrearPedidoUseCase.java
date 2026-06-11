package com.example.bakend_vape.pedido.application.usecase;

import com.example.bakend_vape.pedido.application.dto.CrearPedidoRequest;
import com.example.bakend_vape.pedido.application.dto.PedidoResponse;

public interface CrearPedidoUseCase {
    PedidoResponse execute(CrearPedidoRequest request);
}

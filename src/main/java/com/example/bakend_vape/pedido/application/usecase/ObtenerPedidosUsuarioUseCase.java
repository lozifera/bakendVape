package com.example.bakend_vape.pedido.application.usecase;

import com.example.bakend_vape.pedido.application.dto.PedidoResponse;

import java.util.List;

public interface ObtenerPedidosUsuarioUseCase {
    List<PedidoResponse> execute(Long idUsuario);
}

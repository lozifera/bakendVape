package com.example.bakend_vape.pedido.domain.event;

import lombok.Getter;

@Getter
public class PedidoCreadoEvent {

    private final Long usuarioId;
    private final Long pedidoId;

    public PedidoCreadoEvent(Long usuarioId, Long pedidoId) {
        this.usuarioId = usuarioId;
        this.pedidoId = pedidoId;
    }
}

package com.example.bakend_vape.pedido.application.dto;

import com.example.bakend_vape.pedido.domain.model.EstadoPedido;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActualizarEstadoPedidoRequest {
    private EstadoPedido estado;
}

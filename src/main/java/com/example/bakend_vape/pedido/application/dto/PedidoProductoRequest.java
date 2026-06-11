package com.example.bakend_vape.pedido.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoProductoRequest {

    @JsonProperty("id_producto")
    private Long idProducto;
    private Integer cantidad;
}

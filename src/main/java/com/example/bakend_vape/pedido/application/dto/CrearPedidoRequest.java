package com.example.bakend_vape.pedido.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CrearPedidoRequest {

    @JsonProperty("id_usuario")
    private Long idUsuario;

    private List<PedidoProductoRequest> productos;
}

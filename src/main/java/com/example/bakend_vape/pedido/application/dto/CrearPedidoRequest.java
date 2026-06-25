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

    @JsonProperty("id_direccion")
    private Long idDireccion;

    @JsonProperty("direccion_envio")
    private String direccionEnvio;

    @JsonProperty("referencia_envio")
    private String referenciaEnvio;

    @JsonProperty("puntos_usados")
    private Integer puntosUsados;

    private List<PedidoProductoRequest> productos;
}

package com.example.bakend_vape.pedido.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class PedidoResponse {

    @JsonProperty("id_pedido")
    private Long idPedido;

    @JsonProperty("id_usuario")
    private Long idUsuario;

    private BigDecimal total;
    private LocalDateTime fecha;
    private Boolean estado;
    private List<PedidoProductoResponse> productos;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}

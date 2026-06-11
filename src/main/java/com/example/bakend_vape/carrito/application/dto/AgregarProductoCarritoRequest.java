package com.example.bakend_vape.carrito.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgregarProductoCarritoRequest {

    @JsonProperty("id_producto")
    private Long idProducto;

    private Integer cantidad;

    /**
     * Usado solo cuando el visitante aún no está autenticado.
     * El frontend debe generar y persistir este valor en localStorage.
     */
    @JsonProperty("session_id")
    private String sessionId;
}

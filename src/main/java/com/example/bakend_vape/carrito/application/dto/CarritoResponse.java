package com.example.bakend_vape.carrito.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarritoResponse {

    @JsonProperty("id_carrito")
    private Long idCarrito;

    /** Null si el carrito es anónimo */
    @JsonProperty("id_usuario")
    private Long idUsuario;

    /** Solo presente si el carrito es anónimo */
    @JsonProperty("session_id")
    private String sessionId;

    private List<CarritoProductoResponse> productos;
    private BigDecimal total;
}

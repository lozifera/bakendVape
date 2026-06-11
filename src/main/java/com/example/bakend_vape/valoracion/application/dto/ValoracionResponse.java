package com.example.bakend_vape.valoracion.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ValoracionResponse {

    @JsonProperty("id_valoracion")
    private Long idValoracion;

    @JsonProperty("id_usuario")
    private Long idUsuario;

    @JsonProperty("nombre_usuario")
    private String nombreUsuario;

    @JsonProperty("id_producto")
    private Long idProducto;

    private Integer puntuacion;
    private String comentario;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}

package com.example.bakend_vape.valoracion.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearValoracionRequest {

    @JsonProperty("id_usuario")
    private Long idUsuario;

    @JsonProperty("id_producto")
    private Long idProducto;

    private Integer puntuacion;
    private String comentario;
}

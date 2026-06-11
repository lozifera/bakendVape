package com.example.bakend_vape.aviso.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CrearAvisoRequest {

    private String titulo;
    private String descripcion;

    @JsonProperty("solo_vip")
    private Boolean soloVip;

    @JsonProperty("fecha_inicio")
    private LocalDateTime fechaInicio;

    @JsonProperty("fecha_fin")
    private LocalDateTime fechaFin;

    @JsonProperty("id_usuario")
    private Long idUsuario;
}

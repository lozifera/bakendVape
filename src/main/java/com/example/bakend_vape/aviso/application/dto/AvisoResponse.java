package com.example.bakend_vape.aviso.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AvisoResponse {

    @JsonProperty("id_aviso")
    private Long idAviso;

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

    @JsonProperty("nombre_autor")
    private String nombreAutor;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}

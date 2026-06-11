package com.example.bakend_vape.atributo.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AtributoResponse {

    @JsonProperty("id_atributo")
    private Long idAtributo;

    private String nombre;
    private String unidad;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}

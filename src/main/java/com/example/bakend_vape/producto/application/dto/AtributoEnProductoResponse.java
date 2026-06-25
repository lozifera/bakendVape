package com.example.bakend_vape.producto.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AtributoEnProductoResponse {
    @JsonProperty("id_atributo")
    private Long idAtributo;
    private String nombre;
    private String unidad;
    private String valor;
}
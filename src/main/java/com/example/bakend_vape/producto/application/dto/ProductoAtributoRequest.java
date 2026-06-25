package com.example.bakend_vape.producto.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoAtributoRequest {
    @JsonProperty("id_atributo")
    private Long idAtributo;
    private String valor;
}
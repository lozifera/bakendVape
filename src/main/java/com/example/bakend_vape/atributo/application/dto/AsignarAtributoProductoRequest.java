package com.example.bakend_vape.atributo.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AsignarAtributoProductoRequest {

    @JsonProperty("id_producto")
    private Long idProducto;

    @JsonProperty("id_atributo")
    private Long idAtributo;

    private String valor;
}

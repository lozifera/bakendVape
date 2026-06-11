package com.example.bakend_vape.producto.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ActualizarProductoRequest {

    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    @JsonProperty("stock_minimo")
    private Integer stockMinimo;
    private Long idCategoria;
    private Long idMarca;
}

package com.example.bakend_vape.producto.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class ProductoRankingResponse {

    private Integer posicion;

    @JsonProperty("id_producto")
    private Long idProducto;

    private String nombre;
    private BigDecimal precio;

    @JsonProperty("total_vendido")
    private Long totalVendido;

    @JsonProperty("ingresos_totales")
    private BigDecimal ingresosTotales;
}

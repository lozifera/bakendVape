package com.example.bakend_vape.subasta.application.dto;

import com.example.bakend_vape.subasta.domain.model.EstadoSubasta;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CrearSubastaRequest {

    private String titulo;
    private String descripcion;

    @JsonProperty("solo_vip")
    private Boolean soloVip;

    @JsonProperty("precio_inicial")
    private BigDecimal precioInicial;

    @JsonProperty("duracion_minutos")
    private Integer duracionMinutos;
}

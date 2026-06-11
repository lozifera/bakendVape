package com.example.bakend_vape.subasta.application.dto;

import com.example.bakend_vape.subasta.domain.model.EstadoSubasta;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SubastaResponse {

    @JsonProperty("id_subasta")
    private Long idSubasta;

    private String titulo;
    private String descripcion;

    @JsonProperty("solo_vip")
    private Boolean soloVip;

    @JsonProperty("precio_inicial")
    private BigDecimal precioInicial;

    @JsonProperty("oferta_actual")
    private BigDecimal ofertaActual;

    @JsonProperty("duracion_minutos")
    private Integer duracionMinutos;

    private EstadoSubasta estado;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}

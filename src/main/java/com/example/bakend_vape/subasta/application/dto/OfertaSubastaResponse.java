package com.example.bakend_vape.subasta.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OfertaSubastaResponse {

    @JsonProperty("id_oferta")
    private Long idOferta;

    @JsonProperty("id_subasta")
    private Long idSubasta;

    @JsonProperty("id_usuario")
    private Long idUsuario;

    @JsonProperty("nombre_usuario")
    private String nombreUsuario;

    private BigDecimal monto;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}

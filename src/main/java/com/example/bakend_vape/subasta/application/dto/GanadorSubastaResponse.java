package com.example.bakend_vape.subasta.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GanadorSubastaResponse {

    @JsonProperty("id_subasta")
    private Long idSubasta;

    @JsonProperty("id_usuario_ganador")
    private Long idUsuarioGanador;

    @JsonProperty("nombre_ganador")
    private String nombreGanador;

    @JsonProperty("monto_ganador")
    private BigDecimal montoGanador;

    private String mensaje;
}

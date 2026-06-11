package com.example.bakend_vape.puntos.application.dto;

import com.example.bakend_vape.puntos.domain.model.MotivoMovimiento;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MovimientoPuntosResponse {

    @JsonProperty("id_movimiento")
    private Long idMovimiento;

    @JsonProperty("id_usuario")
    private Long idUsuario;

    private Integer puntos;
    private MotivoMovimiento motivo;

    @JsonProperty("referencia_id")
    private Long referenciaId;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}

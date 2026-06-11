package com.example.bakend_vape.subasta.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class HacerOfertaRequest {

    @JsonProperty("id_usuario")
    private Long idUsuario;

    private BigDecimal monto;
}

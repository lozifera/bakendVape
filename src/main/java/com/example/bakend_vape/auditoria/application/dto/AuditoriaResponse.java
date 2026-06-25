package com.example.bakend_vape.auditoria.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AuditoriaResponse {

    private Long idAuditoria;
    private Long idUsuario;
    private String usuario;
    private String accion;
    private String tabla;
    private Long registroId;
    private String valorAnterior;
    private String valorNuevo;
    private LocalDateTime fecha;
}
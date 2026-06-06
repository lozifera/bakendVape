package com.example.bakend_vape.auditoria.domain.model;

import com.example.bakend_vape.usuario.domain.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Auditoria {

    private Long idAuditoria;
    public Usuario usuario;
    private AccionAuditoria accion;
    private String tabla;
    private Long registroId;
    private String valorAnterior;
    private String valorNuevo;
    private LocalDateTime fecha;

}

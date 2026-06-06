package com.example.bakend_vape.puntos.domain.model;

import com.example.bakend_vape.shared.domain.valueObject.Puntos;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class MovimientoPuntos {

    private Long idMovimientoPuntos;
    private Usuario usuario;
    private Puntos puntos;
    private MotivoMovimiento motivo;
    private Long referenciaId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

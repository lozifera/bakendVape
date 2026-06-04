package com.example.bakend_vape.subasta.domain.model;

import com.example.bakend_vape.shared.domain.valueObject.Money;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class OfertaSubasta {

    private Long idOfertaSubasta;
    private Subasta subasta;
    private Usuario usuario;
    private Money monto;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

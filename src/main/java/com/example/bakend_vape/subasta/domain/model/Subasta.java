package com.example.bakend_vape.subasta.domain.model;

import com.example.bakend_vape.shared.domain.valueObject.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subasta {

    private Long idSubasta;
    private String titulo;
    private String descripcion;
    private Boolean soloVip;
    private Money precioInicial;
    private Integer duracionMinutos;
    private EstadoSubasta estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

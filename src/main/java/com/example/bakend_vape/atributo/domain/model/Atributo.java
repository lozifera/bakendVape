package com.example.bakend_vape.atributo.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Atributo {

    private Long idAtributo;
    private String nombre;
    private String unidad;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}

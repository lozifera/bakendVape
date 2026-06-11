package com.example.bakend_vape.categoria.application.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ActualizarCategoriaRequest {
    private String nombre;
    private List<CrearCategoriaImagenRequest> imagenes;
}


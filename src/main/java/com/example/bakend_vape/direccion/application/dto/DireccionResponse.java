package com.example.bakend_vape.direccion.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DireccionResponse {

    private Long idDireccion;
    private String direccion;
    private String refrencia;
    private Boolean principal;
    private Long idUsuario;

}

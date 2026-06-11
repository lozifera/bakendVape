package com.example.bakend_vape.direccion.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActualizarDireccionRequest {

    private String direccion;
    private String refrencia;
    private Boolean principal;

}

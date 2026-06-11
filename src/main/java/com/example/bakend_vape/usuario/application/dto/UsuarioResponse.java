package com.example.bakend_vape.usuario.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioResponse {

    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private  Boolean esVip;
    private Integer puntosActuales;
    private String rol;

}

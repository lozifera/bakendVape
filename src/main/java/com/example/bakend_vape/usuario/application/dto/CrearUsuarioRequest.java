package com.example.bakend_vape.usuario.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CrearUsuarioRequest {

    private String nombre;
    private String apellido;
    private String email;
    private String password;

}

package com.example.bakend_vape.usuario.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponse {

    private String token;

    @JsonProperty("id_usuario")
    private Long idUsuario;

    private String nombre;

    private String apellido;

    private String email;

    @JsonProperty("es_vip")
    private Boolean esVip;

    @JsonProperty("puntos_actuales")
    private Integer puntosActuales;

    private String rol;
}

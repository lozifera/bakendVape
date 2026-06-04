package com.example.bakend_vape.usuario.domain.model;

import com.example.bakend_vape.rol.domain.model.Rol;
import com.example.bakend_vape.usuario.domain.valueObject.Email;
import com.example.bakend_vape.usuario.domain.valueObject.Password;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter

public class Usuario {

    private Long id_usuario;
    private String nombre;
    private String apellido;
    private Email email;
    private Password password;
    private Boolean es_vip;
    private int puntos_actuales;
    private Rol rol;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public Usuario(Long id_usuario, String nombre, String apellido, Email email, Password password, Boolean es_vip, int puntos_actuales, Rol rol, LocalDateTime created_at, LocalDateTime updated_at) {
        this.id_usuario = id_usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.rol = rol;

        this.es_vip = false;
        this.puntos_actuales = 0;
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
    }

    public void convertirVip() {
        if (this.puntos_actuales >= 100) {
            this.es_vip = true;
        }
    }

    private void actualizarFecha(){
        this.updated_at = LocalDateTime.now();
    }

}

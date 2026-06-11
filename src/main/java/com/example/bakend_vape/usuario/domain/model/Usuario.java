package com.example.bakend_vape.usuario.domain.model;

import com.example.bakend_vape.rol.domain.model.Rol;
import com.example.bakend_vape.usuario.domain.valueObject.Email;
import com.example.bakend_vape.usuario.domain.valueObject.Password;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {

    private Long idUsuario;
    private String nombre;
    private String apellido;
    private Email email;
    private Password password;
    private Boolean esVip;
    private int puntos_actuales;
    private Rol rol;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;


    public void convertirVip() {
        this.esVip = true;
        actualizarFecha();
    }

    public void quitarVip() {
        this.esVip = false;
        actualizarFecha();
    }

    private void actualizarFecha(){
        this.updated_at = LocalDateTime.now();
    }

}

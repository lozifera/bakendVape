package com.example.bakend_vape.direccion.domain.model;

import com.example.bakend_vape.usuario.domain.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Direccion {

    private Long idDireccion;
    private String direccion;
    private String referencia;
    private  Boolean principal;
    private Usuario usuario;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}

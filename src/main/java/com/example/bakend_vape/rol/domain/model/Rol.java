package com.example.bakend_vape.rol.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Rol {

    private Long id_rol;
    private String nombre;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}

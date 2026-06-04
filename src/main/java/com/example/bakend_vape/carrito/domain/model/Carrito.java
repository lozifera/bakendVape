package com.example.bakend_vape.carrito.domain.model;

import com.example.bakend_vape.usuario.domain.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Carrito {

    private Long idCarrito;
    private Usuario usuario;
    private Boolean activo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

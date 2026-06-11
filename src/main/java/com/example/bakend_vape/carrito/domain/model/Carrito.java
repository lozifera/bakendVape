package com.example.bakend_vape.carrito.domain.model;

import com.example.bakend_vape.usuario.domain.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Carrito {

    private Long idCarrito;

    /** Null si el carrito es de un visitante anónimo */
    private Usuario usuario;

    /** Identificador de sesión HTTP para carritos anónimos */
    private String sessionId;

    private Boolean activo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

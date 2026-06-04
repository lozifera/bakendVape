package com.example.bakend_vape.valoracion.domain.model;

import com.example.bakend_vape.producto.domain.model.Producto;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Valoracion {

    private Long idValoracion;
    private Usuario usuario;
    private Producto producto;
    private Integer puntuacion;
    private String comentario;
    private LocalDateTime cratedAt;
    private  LocalDateTime updatedAt;

}

package com.example.bakend_vape.aviso.domain.model;

import com.example.bakend_vape.usuario.domain.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Aviso {

    private Long idAviso;
    private String titulo;
    private String descripcion;
    private Boolean soloVip;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Usuario usuario;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

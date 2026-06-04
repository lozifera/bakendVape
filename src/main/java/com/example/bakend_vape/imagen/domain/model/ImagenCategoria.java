package com.example.bakend_vape.imagen.domain.model;

import com.example.bakend_vape.categoria.domain.model.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ImagenCategoria {

    private Long idImagenCategoria;
    private String imagen;
    private Categoria categoria;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

package com.example.bakend_vape.imagen.domain.model;

import com.example.bakend_vape.shared.domain.valueObject.Url;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Imagen {

    private Long idImagen;
    private Url url;
    private String nombre;
    private  Boolean estado;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

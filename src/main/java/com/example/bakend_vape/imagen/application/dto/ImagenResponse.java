package com.example.bakend_vape.imagen.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImagenResponse {

    private Long idImagen;
    private String url;
    private String nombre;
    private Boolean estado;

}

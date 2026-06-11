package com.example.bakend_vape.imagen.domain.model;

import com.example.bakend_vape.producto.domain.model.Producto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImagenProducto {

    private Long idImagenProducto;
    private Imagen imagen;
    private Producto producto;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}

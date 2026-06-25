package com.example.bakend_vape.producto.application.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ActualizarImagenesProductoRequest {
    private List<CrearProductoImagenRequest> imagenes;
}
package com.example.bakend_vape.producto.application.usecase;

import com.example.bakend_vape.imagen.application.dto.ImagenResponse;
import com.example.bakend_vape.producto.application.dto.CrearProductoImagenRequest;

import java.util.List;

public interface ActualizarImagenesProductoUseCase {
    List<ImagenResponse> execute(Long idProducto, List<CrearProductoImagenRequest> imagenes);
}
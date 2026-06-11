package com.example.bakend_vape.direccion.application.usecase;

import com.example.bakend_vape.direccion.application.dto.DireccionResponse;

import java.util.List;

public interface ObtenerDireccionesUsuarioUseCase {

    List<DireccionResponse> execute(Long idUsuario);

}

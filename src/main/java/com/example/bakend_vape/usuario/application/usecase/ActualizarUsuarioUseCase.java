package com.example.bakend_vape.usuario.application.usecase;

import com.example.bakend_vape.usuario.application.dto.ActualizarUsuarioRequest;
import com.example.bakend_vape.usuario.application.dto.UsuarioResponse;

public interface ActualizarUsuarioUseCase {

    UsuarioResponse execute(Long id, ActualizarUsuarioRequest request);

}

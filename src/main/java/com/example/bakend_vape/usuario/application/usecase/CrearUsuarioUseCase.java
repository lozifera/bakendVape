package com.example.bakend_vape.usuario.application.usecase;

import com.example.bakend_vape.usuario.application.dto.CrearUsuarioRequest;
import com.example.bakend_vape.usuario.application.dto.UsuarioResponse;

public interface CrearUsuarioUseCase {

    UsuarioResponse execute(CrearUsuarioRequest request);

}

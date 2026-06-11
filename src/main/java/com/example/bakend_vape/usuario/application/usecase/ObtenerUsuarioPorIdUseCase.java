package com.example.bakend_vape.usuario.application.usecase;

import com.example.bakend_vape.usuario.application.dto.UsuarioResponse;

public interface ObtenerUsuarioPorIdUseCase {

    UsuarioResponse execute(Long id);

}

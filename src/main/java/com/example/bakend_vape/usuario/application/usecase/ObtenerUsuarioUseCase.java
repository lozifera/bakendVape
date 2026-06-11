package com.example.bakend_vape.usuario.application.usecase;

import com.example.bakend_vape.usuario.application.dto.UsuarioResponse;

import java.util.List;

public interface ObtenerUsuarioUseCase {

    List<UsuarioResponse> execute();

}

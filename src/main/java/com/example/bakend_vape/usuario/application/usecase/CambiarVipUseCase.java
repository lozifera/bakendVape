package com.example.bakend_vape.usuario.application.usecase;

import com.example.bakend_vape.usuario.application.dto.CambiarVipRequest;
import com.example.bakend_vape.usuario.application.dto.UsuarioResponse;

public interface CambiarVipUseCase {

    UsuarioResponse execute(Long idUsuario, CambiarVipRequest request);

}

package com.example.bakend_vape.rol.application.usecase;

import com.example.bakend_vape.rol.application.dto.RolResponse;

public interface ObtenerRolUseCase {

    RolResponse execute(Long id);

}

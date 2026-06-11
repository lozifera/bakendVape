package com.example.bakend_vape.rol.application.usecase;

import com.example.bakend_vape.rol.application.dto.CrearRolRequest;
import com.example.bakend_vape.rol.application.dto.RolResponse;

public interface CrearRolUseCase {

    RolResponse execute(CrearRolRequest request);

}

package com.example.bakend_vape.rol.application.usecase;

import com.example.bakend_vape.rol.application.dto.RolResponse;

import java.util.List;

public interface ListarRolesUseCase {

        List<RolResponse> execute();
}

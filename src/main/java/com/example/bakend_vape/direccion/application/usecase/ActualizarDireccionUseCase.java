package com.example.bakend_vape.direccion.application.usecase;

import com.example.bakend_vape.direccion.application.dto.ActualizarDireccionRequest;
import com.example.bakend_vape.direccion.application.dto.DireccionResponse;

public interface ActualizarDireccionUseCase {

    DireccionResponse execute(Long id, ActualizarDireccionRequest request);

}

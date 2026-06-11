package com.example.bakend_vape.direccion.application.usecase;

import com.example.bakend_vape.direccion.application.dto.CrearDireccionRequest;
import com.example.bakend_vape.direccion.application.dto.DireccionResponse;

public interface CrearDireccionUseCase {

    DireccionResponse execute(CrearDireccionRequest request);

}

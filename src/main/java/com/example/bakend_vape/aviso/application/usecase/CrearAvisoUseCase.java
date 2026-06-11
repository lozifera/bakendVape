package com.example.bakend_vape.aviso.application.usecase;

import com.example.bakend_vape.aviso.application.dto.CrearAvisoRequest;
import com.example.bakend_vape.aviso.application.dto.AvisoResponse;

public interface CrearAvisoUseCase {
    AvisoResponse execute(CrearAvisoRequest request);
}

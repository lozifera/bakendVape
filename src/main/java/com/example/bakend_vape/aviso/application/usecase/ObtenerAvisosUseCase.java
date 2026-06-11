package com.example.bakend_vape.aviso.application.usecase;

import com.example.bakend_vape.aviso.application.dto.AvisoResponse;

import java.util.List;

public interface ObtenerAvisosUseCase {
    List<AvisoResponse> execute();
}

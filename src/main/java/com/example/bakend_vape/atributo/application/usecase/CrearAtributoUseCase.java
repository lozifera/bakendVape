package com.example.bakend_vape.atributo.application.usecase;

import com.example.bakend_vape.atributo.application.dto.AtributoResponse;
import com.example.bakend_vape.atributo.application.dto.CrearAtributoRequest;

public interface CrearAtributoUseCase {
    AtributoResponse execute(CrearAtributoRequest request);
}

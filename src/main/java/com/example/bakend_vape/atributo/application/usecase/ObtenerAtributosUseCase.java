package com.example.bakend_vape.atributo.application.usecase;

import com.example.bakend_vape.atributo.application.dto.AtributoResponse;

import java.util.List;

public interface ObtenerAtributosUseCase {
    List<AtributoResponse> execute();
}

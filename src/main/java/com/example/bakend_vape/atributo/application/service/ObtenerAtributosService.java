package com.example.bakend_vape.atributo.application.service;

import com.example.bakend_vape.atributo.application.dto.AtributoResponse;
import com.example.bakend_vape.atributo.application.usecase.ObtenerAtributosUseCase;
import com.example.bakend_vape.atributo.domain.repository.AtributoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerAtributosService implements ObtenerAtributosUseCase {

    private final AtributoRepository atributoRepository;

    public ObtenerAtributosService(AtributoRepository atributoRepository) {
        this.atributoRepository = atributoRepository;
    }

    @Override
    public List<AtributoResponse> execute() {
        return atributoRepository.findAll().stream().map(a ->
                new AtributoResponse(a.getIdAtributo(), a.getNombre(), a.getUnidad(), a.getCreatedAt())
        ).toList();
    }
}

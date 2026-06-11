package com.example.bakend_vape.atributo.application.service;

import com.example.bakend_vape.atributo.application.dto.AtributoResponse;
import com.example.bakend_vape.atributo.application.dto.CrearAtributoRequest;
import com.example.bakend_vape.atributo.application.usecase.CrearAtributoUseCase;
import com.example.bakend_vape.atributo.domain.model.Atributo;
import com.example.bakend_vape.atributo.domain.repository.AtributoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CrearAtributoService implements CrearAtributoUseCase {

    private final AtributoRepository atributoRepository;

    public CrearAtributoService(AtributoRepository atributoRepository) {
        this.atributoRepository = atributoRepository;
    }

    @Override
    public AtributoResponse execute(CrearAtributoRequest request) {
        Atributo atributo = new Atributo(
                null,
                request.getNombre(),
                request.getUnidad(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        Atributo guardado = atributoRepository.save(atributo);
        return new AtributoResponse(guardado.getIdAtributo(), guardado.getNombre(),
                guardado.getUnidad(), guardado.getCreatedAt());
    }
}

package com.example.bakend_vape.aviso.application.service;

import com.example.bakend_vape.aviso.application.dto.AvisoResponse;
import com.example.bakend_vape.aviso.application.usecase.ObtenerAvisosUseCase;
import com.example.bakend_vape.aviso.domain.repository.AvisoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerAvisosService implements ObtenerAvisosUseCase {

    private final AvisoRepository avisoRepository;

    public ObtenerAvisosService(AvisoRepository avisoRepository) {
        this.avisoRepository = avisoRepository;
    }

    @Override
    public List<AvisoResponse> execute() {
        return avisoRepository.listActivos().stream()
                .map(CrearAvisoService::toResponse)
                .toList();
    }
}

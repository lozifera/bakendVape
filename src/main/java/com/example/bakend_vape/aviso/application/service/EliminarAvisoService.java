package com.example.bakend_vape.aviso.application.service;

import com.example.bakend_vape.aviso.application.usecase.EliminarAvisoUseCase;
import com.example.bakend_vape.aviso.domain.repository.AvisoRepository;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EliminarAvisoService implements EliminarAvisoUseCase {

    private final AvisoRepository avisoRepository;

    public EliminarAvisoService(AvisoRepository avisoRepository) {
        this.avisoRepository = avisoRepository;
    }

    @Override
    public void execute(Long id) {
        avisoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aviso no encontrado con id: " + id));
        avisoRepository.delete(id);
    }
}

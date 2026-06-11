package com.example.bakend_vape.direccion.application.service;

import com.example.bakend_vape.direccion.application.usecase.EliminarDireccionUseCase;
import com.example.bakend_vape.direccion.domain.repository.DireccionRepository;
import org.springframework.stereotype.Service;

@Service
public class EliminarDireccionService implements EliminarDireccionUseCase {

    private  final DireccionRepository direccionRepository;

    public EliminarDireccionService(DireccionRepository direccionRepository) {
        this.direccionRepository = direccionRepository;
    }

    @Override
    public void execute(Long idDireccion) {

        direccionRepository.findById(idDireccion).orElseThrow(() -> new RuntimeException("Direccion no encontrada"));


        direccionRepository.delete(idDireccion);

    }
}

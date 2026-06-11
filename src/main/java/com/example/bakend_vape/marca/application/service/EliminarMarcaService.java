package com.example.bakend_vape.marca.application.service;

import com.example.bakend_vape.marca.application.usecase.EliminarMarcaUseCase;
import com.example.bakend_vape.marca.domain.repository.MarcaRepository;
import org.springframework.stereotype.Service;

@Service
public class EliminarMarcaService implements EliminarMarcaUseCase {

    private final MarcaRepository marcaRepository;

    public EliminarMarcaService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    @Override
    public void execute(Long idMarca) {

        // Validar que la marca existe
        marcaRepository.findById(idMarca)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        // Eliminar la marca
        marcaRepository.delete(idMarca);
    }
}


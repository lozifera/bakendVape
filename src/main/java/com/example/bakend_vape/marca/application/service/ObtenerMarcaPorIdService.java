package com.example.bakend_vape.marca.application.service;

import com.example.bakend_vape.marca.application.dto.MarcaResponse;
import com.example.bakend_vape.marca.application.usecase.ObtenerMarcaPorIdUseCase;
import com.example.bakend_vape.marca.domain.model.Marca;
import com.example.bakend_vape.marca.domain.repository.MarcaRepository;
import org.springframework.stereotype.Service;

@Service
public class ObtenerMarcaPorIdService implements ObtenerMarcaPorIdUseCase {

    private final MarcaRepository marcaRepository;

    public ObtenerMarcaPorIdService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    @Override
    public MarcaResponse execute(Long idMarca) {

        Marca marca = marcaRepository.findById(idMarca)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        return new MarcaResponse(
                marca.getId_marca(),
                marca.getNombre(),
                marca.getCreated_at(),
                marca.getUpdated_at()
        );
    }
}


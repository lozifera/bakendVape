package com.example.bakend_vape.marca.application.service;

import com.example.bakend_vape.marca.application.dto.MarcaResponse;
import com.example.bakend_vape.marca.application.usecase.ObtenerMarcasUseCase;
import com.example.bakend_vape.marca.domain.model.Marca;
import com.example.bakend_vape.marca.domain.repository.MarcaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObtenerMarcasService implements ObtenerMarcasUseCase {

    private final MarcaRepository marcaRepository;

    public ObtenerMarcasService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    @Override
    public List<MarcaResponse> execute() {
        return marcaRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    private MarcaResponse convertirAResponse(Marca marca) {
        return new MarcaResponse(
                marca.getId_marca(),
                marca.getNombre(),
                marca.getCreated_at(),
                marca.getUpdated_at()
        );
    }
}


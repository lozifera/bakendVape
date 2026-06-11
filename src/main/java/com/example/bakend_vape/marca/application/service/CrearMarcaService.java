package com.example.bakend_vape.marca.application.service;

import com.example.bakend_vape.marca.application.dto.CrearMarcaRequest;
import com.example.bakend_vape.marca.application.dto.MarcaResponse;
import com.example.bakend_vape.marca.application.usecase.CrearMarcaUseCase;
import com.example.bakend_vape.marca.domain.model.Marca;
import com.example.bakend_vape.marca.domain.repository.MarcaRepository;
import com.example.bakend_vape.marca.infrastructure.mapper.MarcaMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CrearMarcaService implements CrearMarcaUseCase {

    private final MarcaRepository marcaRepository;
    private final MarcaMapper marcaMapper;

    public CrearMarcaService(MarcaRepository marcaRepository, MarcaMapper marcaMapper) {
        this.marcaRepository = marcaRepository;
        this.marcaMapper = marcaMapper;
    }

    @Override
    public MarcaResponse execute(CrearMarcaRequest request) {

        // Validar que el nombre no esté vacío
        if (request.getNombre() == null || request.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre de la marca no puede estar vacío");
        }

        // Validar que la marca no exista ya
        marcaRepository.findByNombre(request.getNombre())
                .ifPresent(m -> {
                    throw new RuntimeException("La marca " + request.getNombre() + " ya existe");
                });

        // Crear nueva marca
        Marca marca = new Marca(
                null,
                request.getNombre(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Marca marcaGuardada = marcaRepository.save(marca);

        return new MarcaResponse(
                marcaGuardada.getId_marca(),
                marcaGuardada.getNombre(),
                marcaGuardada.getCreated_at(),
                marcaGuardada.getUpdated_at()
        );
    }
}


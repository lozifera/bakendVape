package com.example.bakend_vape.direccion.application.service;

import com.example.bakend_vape.direccion.application.dto.DireccionResponse;
import com.example.bakend_vape.direccion.application.usecase.ObtenerDireccionesUsuarioUseCase;
import com.example.bakend_vape.direccion.domain.model.Direccion;
import com.example.bakend_vape.direccion.domain.repository.DireccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObtenerDireccionesUsuarioService implements ObtenerDireccionesUsuarioUseCase {

    private final DireccionRepository direccionRepository;

    public ObtenerDireccionesUsuarioService(DireccionRepository direccionRepository) {
        this.direccionRepository = direccionRepository;
    }

    @Override
    public List<DireccionResponse> execute(Long idUsuario) {
        return direccionRepository.findByUsuario(idUsuario)
                .stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    private DireccionResponse convertirAResponse(Direccion direccion){
        return new DireccionResponse(
                direccion.getIdDireccion(),
                direccion.getDireccion(),
                direccion.getReferencia(),
                direccion.getPrincipal(),
                direccion.getUsuario().getIdUsuario()
        );
    }
}

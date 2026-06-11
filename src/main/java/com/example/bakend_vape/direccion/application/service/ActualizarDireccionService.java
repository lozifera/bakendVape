package com.example.bakend_vape.direccion.application.service;

import com.example.bakend_vape.direccion.application.dto.ActualizarDireccionRequest;
import com.example.bakend_vape.direccion.application.dto.DireccionResponse;
import com.example.bakend_vape.direccion.application.usecase.ActualizarDireccionUseCase;
import com.example.bakend_vape.direccion.domain.model.Direccion;
import com.example.bakend_vape.direccion.domain.repository.DireccionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActualizarDireccionService implements ActualizarDireccionUseCase {

    private final DireccionRepository direccionRepository;

    public ActualizarDireccionService(DireccionRepository direccionRepository) {
        this.direccionRepository = direccionRepository;
    }


    @Override
    public DireccionResponse execute(Long idDireccion, ActualizarDireccionRequest request) {

        Direccion direccion = direccionRepository.findById(idDireccion)
                .orElseThrow(() -> new RuntimeException("Direccion no encontrada"));

        // Si se marca como principal
        if (request.getPrincipal() != null && request.getPrincipal()) {
            List<Direccion> direccionesUsuario = direccionRepository.findByUsuario(direccion.getUsuario().getIdUsuario());

            direccionesUsuario.forEach(d -> {
                if (!d.getIdDireccion().equals(idDireccion)) {
                    d.setPrincipal(false);
                    direccionRepository.save(d);
                }
            });
        }

        if (request.getDireccion() != null){
            direccion.setDireccion(request.getDireccion());
        }

        if (request.getRefrencia() != null){
            direccion.setReferencia(request.getRefrencia());
        }

        if (request.getPrincipal() != null){
            direccion.setPrincipal(request.getPrincipal());
        }

        direccion.setUpdated_at(LocalDateTime.now());

        Direccion update = direccionRepository.save(direccion);

        return new DireccionResponse(
                update.getIdDireccion(),
                update.getDireccion(),
                update.getReferencia(),
                update.getPrincipal(),
                update.getUsuario().getIdUsuario()
        );
    }
}

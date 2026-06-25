package com.example.bakend_vape.auditoria.application.service;

import com.example.bakend_vape.auditoria.application.dto.AuditoriaResponse;
import com.example.bakend_vape.auditoria.application.usecase.ObtenerAuditoriasUseCase;
import com.example.bakend_vape.auditoria.domain.repository.AuditoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ObtenerAuditoriasService
        implements ObtenerAuditoriasUseCase {

    private final AuditoriaRepository auditoriaRepository;

    @Override
    public List<AuditoriaResponse> execute() {

        return auditoriaRepository
                .findAll()
                .stream()
                .map(a -> new AuditoriaResponse(
                        a.getIdAuditoria(),
                        a.getUsuario().getIdUsuario(),
                        a.getUsuario().getNombre(),
                        a.getAccion().name(),
                        a.getTabla(),
                        a.getRegistroId(),
                        a.getValorAnterior(),
                        a.getValorNuevo(),
                        a.getFecha()
                ))
                .toList();
    }
}
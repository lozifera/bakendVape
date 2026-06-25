package com.example.bakend_vape.auditoria.infrastructure.controller;

import com.example.bakend_vape.auditoria.application.dto.AuditoriaResponse;
import com.example.bakend_vape.auditoria.application.usecase.ObtenerAuditoriasUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auditorias")
@RequiredArgsConstructor
public class AuditoriaController {

    private final ObtenerAuditoriasUseCase obtenerAuditoriasUseCase;

    @GetMapping
    public List<AuditoriaResponse> listar() {
        return obtenerAuditoriasUseCase.execute();
    }
}
package com.example.bakend_vape.auditoria.application.usecase;

import com.example.bakend_vape.auditoria.application.dto.AuditoriaResponse;

import java.util.List;

public interface ObtenerAuditoriasUseCase {

    List<AuditoriaResponse> execute();

}
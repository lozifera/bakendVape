package com.example.bakend_vape.auditoria.application.service;

import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.auditoria.domain.model.Auditoria;
import com.example.bakend_vape.auditoria.domain.repository.AuditoriaRepository;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;

    public void registrar(
            Usuario usuario,
            AccionAuditoria accion,
            String tabla,
            Long registroId,
            String valorAnterior,
            String valorNuevo
    ) {

        Auditoria auditoria = new Auditoria(
                null,
                usuario,
                accion,
                tabla,
                registroId,
                valorAnterior,
                valorNuevo,
                LocalDateTime.now()
        );

        auditoriaRepository.save(auditoria);
    }
}
package com.example.bakend_vape.aviso.application.service;

import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.aviso.application.dto.AvisoResponse;
import com.example.bakend_vape.aviso.application.dto.CrearAvisoRequest;
import com.example.bakend_vape.aviso.application.usecase.CrearAvisoUseCase;
import com.example.bakend_vape.aviso.domain.model.Aviso;
import com.example.bakend_vape.aviso.domain.repository.AvisoRepository;
import com.example.bakend_vape.shared.security.UsuarioAutenticadoService;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CrearAvisoService implements CrearAvisoUseCase {

    private final AvisoRepository avisoRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;

    public CrearAvisoService(AvisoRepository avisoRepository,
                             UsuarioAutenticadoService usuarioAutenticadoService,
                             AuditoriaService auditoriaService) {
        this.avisoRepository = avisoRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    @Transactional
    public AvisoResponse execute(CrearAvisoRequest request) {
        Usuario usuarioCreador = usuarioAutenticadoService.obtenerUsuario();

        Aviso aviso = new Aviso(
                null,
                request.getTitulo(),
                request.getDescripcion(),
                request.getSoloVip() != null ? request.getSoloVip() : false,
                request.getFechaInicio(),
                request.getFechaFin(),
                usuarioCreador,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Aviso guardado = avisoRepository.save(aviso);

        try {
            auditoriaService.registrar(
                    usuarioCreador,
                    AccionAuditoria.CREATE,
                    "aviso",
                    guardado.getIdAviso(),
                    null,
                    guardado.getTitulo()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return toResponse(guardado);
    }

    static AvisoResponse toResponse(Aviso a) {
        return new AvisoResponse(
                a.getIdAviso(),
                a.getTitulo(),
                a.getDescripcion(),
                a.getSoloVip(),
                a.getFechaInicio(),
                a.getFechaFin(),
                a.getUsuario().getIdUsuario(),
                a.getUsuario().getNombre() + " " + a.getUsuario().getApellido(),
                a.getCreatedAt()
        );
    }
}

package com.example.bakend_vape.aviso.application.service;

import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.aviso.application.usecase.EliminarAvisoUseCase;
import com.example.bakend_vape.aviso.domain.repository.AvisoRepository;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import com.example.bakend_vape.shared.security.UsuarioAutenticadoService;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class EliminarAvisoService implements EliminarAvisoUseCase {

    private final AvisoRepository avisoRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;

    public EliminarAvisoService(AvisoRepository avisoRepository,
                                UsuarioAutenticadoService usuarioAutenticadoService,
                                AuditoriaService auditoriaService) {
        this.avisoRepository = avisoRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    public void execute(Long id) {
        var aviso = avisoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aviso no encontrado con id: " + id));

        String tituloAnterior = aviso.getTitulo();

        avisoRepository.delete(id);

        Usuario usuario = usuarioAutenticadoService.obtenerUsuario();
        try {
            auditoriaService.registrar(
                    usuario,
                    AccionAuditoria.DELETE,
                    "aviso",
                    id,
                    tituloAnterior,
                    null
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

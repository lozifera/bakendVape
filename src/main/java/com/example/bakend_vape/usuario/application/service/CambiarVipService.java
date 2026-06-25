package com.example.bakend_vape.usuario.application.service;

import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.shared.security.UsuarioAutenticadoService;
import com.example.bakend_vape.usuario.application.dto.CambiarVipRequest;
import com.example.bakend_vape.usuario.application.dto.UsuarioResponse;
import com.example.bakend_vape.usuario.application.usecase.CambiarVipUseCase;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CambiarVipService implements CambiarVipUseCase {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;

    public CambiarVipService(UsuarioRepository usuarioRepository,
                             UsuarioAutenticadoService usuarioAutenticadoService,
                             AuditoriaService auditoriaService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    @Transactional
    public UsuarioResponse execute(Long idUsuario, CambiarVipRequest request) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Boolean estadoAnterior = usuario.getEsVip();

        if (Boolean.TRUE.equals(request.getEsVip())) {
            usuario.convertirVip();
        } else {
            usuario.quitarVip();
        }

        Usuario actualizado = usuarioRepository.save(usuario);

        try {
            Usuario admin = usuarioAutenticadoService.obtenerUsuario();
            auditoriaService.registrar(
                    admin,
                    AccionAuditoria.UPDATE,
                    "usuario_vip",
                    idUsuario,
                    "es_vip=" + estadoAnterior,
                    "es_vip=" + actualizado.getEsVip()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new UsuarioResponse(
                actualizado.getIdUsuario(),
                actualizado.getNombre(),
                actualizado.getApellido(),
                actualizado.getEmail().getValue(),
                actualizado.getEsVip(),
                actualizado.getPuntos_actuales(),
                actualizado.getRol().getNombre()
        );
    }
}

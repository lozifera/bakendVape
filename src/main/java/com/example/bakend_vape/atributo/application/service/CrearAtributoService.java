package com.example.bakend_vape.atributo.application.service;

import com.example.bakend_vape.atributo.application.dto.AtributoResponse;
import com.example.bakend_vape.atributo.application.dto.CrearAtributoRequest;
import com.example.bakend_vape.atributo.application.usecase.CrearAtributoUseCase;
import com.example.bakend_vape.atributo.domain.model.Atributo;
import com.example.bakend_vape.atributo.domain.repository.AtributoRepository;
import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.shared.security.UsuarioAutenticadoService;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CrearAtributoService implements CrearAtributoUseCase {

    private final AtributoRepository atributoRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;

    public CrearAtributoService(AtributoRepository atributoRepository,
                                UsuarioAutenticadoService usuarioAutenticadoService,
                                AuditoriaService auditoriaService) {
        this.atributoRepository = atributoRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    public AtributoResponse execute(CrearAtributoRequest request) {
        Atributo atributo = new Atributo(
                null,
                request.getNombre(),
                request.getUnidad(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        Atributo guardado = atributoRepository.save(atributo);

        Usuario usuario = usuarioAutenticadoService.obtenerUsuario();
        try {
            auditoriaService.registrar(
                    usuario,
                    AccionAuditoria.CREATE,
                    "atributo",
                    guardado.getIdAtributo(),
                    null,
                    guardado.getNombre()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new AtributoResponse(guardado.getIdAtributo(), guardado.getNombre(),
                guardado.getUnidad(), guardado.getCreatedAt());
    }
}

package com.example.bakend_vape.atributo.application.service;

import com.example.bakend_vape.atributo.application.usecase.EliminarAtributoUseCase;
import com.example.bakend_vape.atributo.domain.repository.AtributoRepository;
import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import com.example.bakend_vape.shared.security.UsuarioAutenticadoService;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class EliminarAtributoService implements EliminarAtributoUseCase {

    private final AtributoRepository atributoRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;

    public EliminarAtributoService(AtributoRepository atributoRepository,
                                   UsuarioAutenticadoService usuarioAutenticadoService,
                                   AuditoriaService auditoriaService) {
        this.atributoRepository = atributoRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    public void execute(Long id) {
        var atributo = atributoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Atributo no encontrado con id: " + id));

        String nombreAnterior = atributo.getNombre();

        atributoRepository.delete(id);

        Usuario usuario = usuarioAutenticadoService.obtenerUsuario();
        try {
            auditoriaService.registrar(
                    usuario,
                    AccionAuditoria.DELETE,
                    "atributo",
                    id,
                    nombreAnterior,
                    null
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

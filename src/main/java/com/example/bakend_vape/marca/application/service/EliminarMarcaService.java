package com.example.bakend_vape.marca.application.service;

import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.marca.application.usecase.EliminarMarcaUseCase;
import com.example.bakend_vape.marca.domain.repository.MarcaRepository;
import com.example.bakend_vape.shared.security.UsuarioAutenticadoService;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class EliminarMarcaService implements EliminarMarcaUseCase {

    private final MarcaRepository marcaRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;

    public EliminarMarcaService(MarcaRepository marcaRepository,
                                UsuarioAutenticadoService usuarioAutenticadoService,
                                AuditoriaService auditoriaService) {
        this.marcaRepository = marcaRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    public void execute(Long idMarca) {

        // Validar que la marca existe
        var marca = marcaRepository.findById(idMarca)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        String nombreAnterior = marca.getNombre();

        // Eliminar la marca
        marcaRepository.delete(idMarca);

        Usuario usuario = usuarioAutenticadoService.obtenerUsuario();
        try {
            auditoriaService.registrar(
                    usuario,
                    AccionAuditoria.DELETE,
                    "marca",
                    idMarca,
                    nombreAnterior,
                    null
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


package com.example.bakend_vape.direccion.application.service;

import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.direccion.application.usecase.EliminarDireccionUseCase;
import com.example.bakend_vape.direccion.domain.model.Direccion;
import com.example.bakend_vape.direccion.domain.repository.DireccionRepository;
import com.example.bakend_vape.shared.security.UsuarioAutenticadoService;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class EliminarDireccionService implements EliminarDireccionUseCase {

    private final DireccionRepository direccionRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;

    public EliminarDireccionService(DireccionRepository direccionRepository,
                                    UsuarioAutenticadoService usuarioAutenticadoService,
                                    AuditoriaService auditoriaService) {
        this.direccionRepository = direccionRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    public void execute(Long idDireccion) {

        // 1. Buscar dirección (NECESARIO para auditoría)
        Direccion direccion = direccionRepository.findById(idDireccion)
                .orElseThrow(() -> new RuntimeException("Direccion no encontrada"));

        // 2. Guardar valor anterior
        String direccionAnterior = direccion.getDireccion();

        // 3. Eliminar
        direccionRepository.delete(idDireccion);

        // 4. Usuario autenticado
        Usuario usuario = usuarioAutenticadoService.obtenerUsuario();

        // 5. Auditoría DELETE
        auditoriaService.registrar(
                usuario,
                AccionAuditoria.DELETE,
                "direccion",
                idDireccion,
                direccionAnterior,
                null
        );
    }
}
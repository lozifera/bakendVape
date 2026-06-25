package com.example.bakend_vape.direccion.application.service;

import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.direccion.application.dto.CrearDireccionRequest;
import com.example.bakend_vape.direccion.application.dto.DireccionResponse;
import com.example.bakend_vape.direccion.application.usecase.CrearDireccionUseCase;
import com.example.bakend_vape.direccion.domain.model.Direccion;
import com.example.bakend_vape.direccion.domain.repository.DireccionRepository;
import com.example.bakend_vape.shared.security.UsuarioAutenticadoService;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CrearDireccionService implements CrearDireccionUseCase {

    private final DireccionRepository direccionRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;

    public CrearDireccionService(DireccionRepository direccionRepository, UsuarioRepository usuarioRepository, UsuarioAutenticadoService usuarioAutenticadoService, AuditoriaService auditoriaService) {
        this.direccionRepository = direccionRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    public DireccionResponse execute(CrearDireccionRequest request) {

        Usuario usuarioDestino = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar si el usuario ya tiene direcciones
        List<Direccion> direccionesExistentes = direccionRepository.findByUsuario(request.getIdUsuario());
        boolean esPrimera = direccionesExistentes.isEmpty();

        // Si se intenta crear como principal y no es la primera
        boolean seraPrincipal = esPrimera || (request.getPrincipal() != null && request.getPrincipal());

        if (seraPrincipal && !esPrimera) {
            // Cambiar todas las direcciones existentes a false
            direccionesExistentes.forEach(d -> {
                d.setPrincipal(false);
                direccionRepository.save(d);
            });
        }

        Direccion direccion = new Direccion(
                null,
                request.getDireccion(),
                request.getRefrencia(),
                seraPrincipal,
                usuarioDestino,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Direccion saved = direccionRepository.save(direccion);
        Usuario usuario = usuarioAutenticadoService.obtenerUsuario();

        try {
            auditoriaService.registrar(
                    usuario,
                    AccionAuditoria.CREATE,
                    "direccion",
                    saved.getIdDireccion(),
                    null,
                    saved.getDireccion()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new DireccionResponse(
            saved.getIdDireccion(),
            saved.getDireccion(),
            saved.getReferencia(),
            saved.getPrincipal(),
            saved.getUsuario().getIdUsuario()
        );

    }


}

package com.example.bakend_vape.marca.application.service;

import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.marca.application.dto.ActualizarMarcaRequest;
import com.example.bakend_vape.marca.application.dto.MarcaResponse;
import com.example.bakend_vape.marca.application.usecase.ActualizarMarcaUseCase;
import com.example.bakend_vape.marca.domain.model.Marca;
import com.example.bakend_vape.marca.domain.repository.MarcaRepository;
import com.example.bakend_vape.shared.security.UsuarioAutenticadoService;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ActualizarMarcaService implements ActualizarMarcaUseCase {

    private final MarcaRepository marcaRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;

    public ActualizarMarcaService(MarcaRepository marcaRepository,
                                  UsuarioAutenticadoService usuarioAutenticadoService,
                                  AuditoriaService auditoriaService) {
        this.marcaRepository = marcaRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    public MarcaResponse execute(Long idMarca, ActualizarMarcaRequest request) {

        // Buscar marca existente
        Marca marca = marcaRepository.findById(idMarca)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));

        String valorAnterior = marca.getNombre();

        // Actualizar nombre si se proporciona
        if (request.getNombre() != null && !request.getNombre().trim().isEmpty()) {

            // Validar que el nuevo nombre no esté ya en uso (excepto por la misma marca)
            marcaRepository.findByNombre(request.getNombre())
                    .ifPresent(m -> {
                        if (!m.getId_marca().equals(idMarca)) {
                            throw new RuntimeException("El nombre " + request.getNombre() + " ya está en uso");
                        }
                    });

            marca.setNombre(request.getNombre());
        }

        // Actualizar fecha de modificación
        marca.setUpdated_at(LocalDateTime.now());

        Marca marcaActualizada = marcaRepository.save(marca);

        Usuario usuario = usuarioAutenticadoService.obtenerUsuario();
        try {
            auditoriaService.registrar(
                    usuario,
                    AccionAuditoria.UPDATE,
                    "marca",
                    idMarca,
                    valorAnterior,
                    marcaActualizada.getNombre()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new MarcaResponse(
                marcaActualizada.getId_marca(),
                marcaActualizada.getNombre(),
                marcaActualizada.getCreated_at(),
                marcaActualizada.getUpdated_at()
        );
    }
}


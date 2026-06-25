package com.example.bakend_vape.direccion.application.service;

import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.direccion.application.dto.ActualizarDireccionRequest;
import com.example.bakend_vape.direccion.application.dto.DireccionResponse;
import com.example.bakend_vape.direccion.application.usecase.ActualizarDireccionUseCase;
import com.example.bakend_vape.direccion.domain.model.Direccion;
import com.example.bakend_vape.direccion.domain.repository.DireccionRepository;
import com.example.bakend_vape.shared.security.UsuarioAutenticadoService;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActualizarDireccionService implements ActualizarDireccionUseCase {

    private final DireccionRepository direccionRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;

    public ActualizarDireccionService(DireccionRepository direccionRepository, UsuarioAutenticadoService usuarioAutenticadoService, AuditoriaService auditoriaService) {
        this.direccionRepository = direccionRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    public DireccionResponse execute(Long idDireccion, ActualizarDireccionRequest request) {

        Direccion direccion = direccionRepository.findById(idDireccion)
                .orElseThrow(() -> new RuntimeException("Direccion no encontrada"));

        // 2. Guardar valores anteriores (AUDITORÍA)
        String direccionAnterior = direccion.getDireccion();
        String referenciaAnterior = direccion.getReferencia();
        Boolean principalAnterior = direccion.getPrincipal();

        // Si se marca como principal
        if (request.getPrincipal() != null && request.getPrincipal()) {
            List<Direccion> direccionesUsuario = direccionRepository.findByUsuario(direccion.getUsuario().getIdUsuario());

            direccionesUsuario.forEach(d -> {
                if (!d.getIdDireccion().equals(idDireccion)) {
                    d.setPrincipal(false);
                    direccionRepository.save(d);
                }
            });
        }

        if (request.getDireccion() != null){
            direccion.setDireccion(request.getDireccion());
        }

        if (request.getRefrencia() != null){
            direccion.setReferencia(request.getRefrencia());
        }

        if (request.getPrincipal() != null){
            direccion.setPrincipal(request.getPrincipal());
        }

        direccion.setUpdated_at(LocalDateTime.now());

        Direccion update = direccionRepository.save(direccion);

        // 5. Usuario autenticado
        Usuario usuario = usuarioAutenticadoService.obtenerUsuario();

        // 6. AUDITORÍA UPDATE
        auditoriaService.registrar(
                usuario,
                AccionAuditoria.UPDATE,
                "direccion",
                update.getIdDireccion(),
                "direccion=" + direccionAnterior +
                        ", referencia=" + referenciaAnterior +
                        ", principal=" + principalAnterior,
                "direccion=" + update.getDireccion() +
                        ", referencia=" + update.getReferencia() +
                        ", principal=" + update.getPrincipal()
        );

        return new DireccionResponse(
                update.getIdDireccion(),
                update.getDireccion(),
                update.getReferencia(),
                update.getPrincipal(),
                update.getUsuario().getIdUsuario()
        );
    }
}

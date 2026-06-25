package com.example.bakend_vape.subasta.application.service;

import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import com.example.bakend_vape.shared.domain.valueObject.Money;
import com.example.bakend_vape.shared.security.UsuarioAutenticadoService;
import com.example.bakend_vape.subasta.application.dto.CrearSubastaRequest;
import com.example.bakend_vape.subasta.application.dto.SubastaResponse;
import com.example.bakend_vape.subasta.application.usecase.CrearSubastaUseCase;
import com.example.bakend_vape.subasta.domain.model.EstadoSubasta;
import com.example.bakend_vape.subasta.domain.model.Subasta;
import com.example.bakend_vape.subasta.domain.repository.OfertaSubastaRepository;
import com.example.bakend_vape.subasta.domain.repository.SubastaRepository;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CrearSubastaService implements CrearSubastaUseCase {

    private final SubastaRepository subastaRepository;
    private final OfertaSubastaRepository ofertaSubastaRepository;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;

    public CrearSubastaService(SubastaRepository subastaRepository,
                               OfertaSubastaRepository ofertaSubastaRepository,
                               UsuarioAutenticadoService usuarioAutenticadoService,
                               AuditoriaService auditoriaService) {
        this.subastaRepository = subastaRepository;
        this.ofertaSubastaRepository = ofertaSubastaRepository;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    public SubastaResponse execute(CrearSubastaRequest request) {
        Subasta subasta = new Subasta(
                null,
                request.getTitulo(),
                request.getDescripcion(),
                request.getSoloVip() != null ? request.getSoloVip() : true,
                new Money(request.getPrecioInicial()),
                request.getDuracionMinutos(),
                EstadoSubasta.ACTIVA,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Subasta guardada = subastaRepository.save(subasta);

        Usuario usuario = usuarioAutenticadoService.obtenerUsuario();
        try {
            auditoriaService.registrar(
                    usuario,
                    AccionAuditoria.CREATE,
                    "subasta",
                    guardada.getIdSubasta(),
                    null,
                    guardada.getTitulo()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new SubastaResponse(
                guardada.getIdSubasta(),
                guardada.getTitulo(),
                guardada.getDescripcion(),
                guardada.getSoloVip(),
                guardada.getPrecioInicial().value(),
                guardada.getPrecioInicial().value(), // oferta actual = precio inicial al crear
                guardada.getDuracionMinutos(),
                guardada.getEstado(),
                guardada.getCreatedAt()
        );
    }
}

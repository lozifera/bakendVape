package com.example.bakend_vape.subasta.application.service;

import com.example.bakend_vape.auditoria.application.service.AuditoriaService;
import com.example.bakend_vape.auditoria.domain.model.AccionAuditoria;
import com.example.bakend_vape.shared.domain.exception.BusinessException;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import com.example.bakend_vape.shared.security.UsuarioAutenticadoService;
import com.example.bakend_vape.subasta.application.dto.GanadorSubastaResponse;
import com.example.bakend_vape.subasta.application.usecase.FinalizarSubastaUseCase;
import com.example.bakend_vape.subasta.domain.model.EstadoSubasta;
import com.example.bakend_vape.subasta.domain.model.OfertaSubasta;
import com.example.bakend_vape.subasta.domain.model.Subasta;
import com.example.bakend_vape.subasta.domain.repository.OfertaSubastaRepository;
import com.example.bakend_vape.subasta.domain.repository.SubastaRepository;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class FinalizarSubastaService implements FinalizarSubastaUseCase {

    private final SubastaRepository subastaRepository;
    private final OfertaSubastaRepository ofertaSubastaRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final UsuarioAutenticadoService usuarioAutenticadoService;
    private final AuditoriaService auditoriaService;

    public FinalizarSubastaService(SubastaRepository subastaRepository,
                                   OfertaSubastaRepository ofertaSubastaRepository,
                                   SimpMessagingTemplate messagingTemplate,
                                   UsuarioAutenticadoService usuarioAutenticadoService,
                                   AuditoriaService auditoriaService) {
        this.subastaRepository = subastaRepository;
        this.ofertaSubastaRepository = ofertaSubastaRepository;
        this.messagingTemplate = messagingTemplate;
        this.usuarioAutenticadoService = usuarioAutenticadoService;
        this.auditoriaService = auditoriaService;
    }

    @Override
    public GanadorSubastaResponse execute(Long idSubasta) {
        Subasta subasta = subastaRepository.findById(idSubasta)
                .orElseThrow(() -> new NotFoundException("Subasta no encontrada"));

        if (subasta.getEstado() != EstadoSubasta.ACTIVA) {
            throw new BusinessException("La subasta ya fue finalizada o está en otro estado");
        }

        String valorAnterior = subasta.getEstado().name();

        // Cambiar estado a FINALIZADA
        subasta.setEstado(EstadoSubasta.FINALIZADA);
        subastaRepository.save(subasta);

        Usuario usuario = usuarioAutenticadoService.obtenerUsuario();
        try {
            auditoriaService.registrar(
                    usuario,
                    AccionAuditoria.UPDATE,
                    "subasta",
                    idSubasta,
                    valorAnterior,
                    EstadoSubasta.FINALIZADA.name()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Determinar ganador: mayor oferta
        List<OfertaSubasta> ofertas = ofertaSubastaRepository.findBySubastaId(idSubasta);
        Optional<OfertaSubasta> ofertaGanadora = ofertas.stream()
                .max(Comparator.comparing(o -> o.getMonto().value()));

        GanadorSubastaResponse ganadorResponse;

        if (ofertaGanadora.isPresent()) {
            OfertaSubasta ganadora = ofertaGanadora.get();
            ganadorResponse = new GanadorSubastaResponse(
                    idSubasta,
                    ganadora.getUsuario().getIdUsuario(),
                    ganadora.getUsuario().getNombre() + " " + ganadora.getUsuario().getApellido(),
                    ganadora.getMonto().value(),
                    "Subasta finalizada. ¡Felicitaciones al ganador!"
            );
        } else {
            ganadorResponse = new GanadorSubastaResponse(
                    idSubasta, null, null, null,
                    "Subasta finalizada sin ofertas."
            );
        }

        // Notificar a todos los participantes el resultado final vía WebSocket
        messagingTemplate.convertAndSend("/topic/subastas/" + idSubasta + "/resultado", ganadorResponse);

        return ganadorResponse;
    }
}

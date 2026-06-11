package com.example.bakend_vape.subasta.application.service;

import com.example.bakend_vape.shared.domain.exception.BusinessException;
import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import com.example.bakend_vape.subasta.application.dto.GanadorSubastaResponse;
import com.example.bakend_vape.subasta.application.usecase.FinalizarSubastaUseCase;
import com.example.bakend_vape.subasta.domain.model.EstadoSubasta;
import com.example.bakend_vape.subasta.domain.model.OfertaSubasta;
import com.example.bakend_vape.subasta.domain.model.Subasta;
import com.example.bakend_vape.subasta.domain.repository.OfertaSubastaRepository;
import com.example.bakend_vape.subasta.domain.repository.SubastaRepository;
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

    public FinalizarSubastaService(SubastaRepository subastaRepository,
                                   OfertaSubastaRepository ofertaSubastaRepository,
                                   SimpMessagingTemplate messagingTemplate) {
        this.subastaRepository = subastaRepository;
        this.ofertaSubastaRepository = ofertaSubastaRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public GanadorSubastaResponse execute(Long idSubasta) {
        Subasta subasta = subastaRepository.findById(idSubasta)
                .orElseThrow(() -> new NotFoundException("Subasta no encontrada"));

        if (subasta.getEstado() != EstadoSubasta.ACTIVA) {
            throw new BusinessException("La subasta ya fue finalizada o está en otro estado");
        }

        // Cambiar estado a FINALIZADA
        subasta.setEstado(EstadoSubasta.FINALIZADA);
        subastaRepository.save(subasta);

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

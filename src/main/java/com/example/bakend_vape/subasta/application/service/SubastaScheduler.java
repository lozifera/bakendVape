package com.example.bakend_vape.subasta.application.service;

import com.example.bakend_vape.shared.domain.valueObject.Money;
import com.example.bakend_vape.subasta.application.dto.GanadorSubastaResponse;
import com.example.bakend_vape.subasta.domain.model.EstadoSubasta;
import com.example.bakend_vape.subasta.domain.model.OfertaSubasta;
import com.example.bakend_vape.subasta.domain.model.Subasta;
import com.example.bakend_vape.subasta.domain.repository.OfertaSubastaRepository;
import com.example.bakend_vape.subasta.domain.repository.SubastaRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class SubastaScheduler {

    private final SubastaRepository subastaRepository;
    private final OfertaSubastaRepository ofertaSubastaRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public SubastaScheduler(SubastaRepository subastaRepository,
                            OfertaSubastaRepository ofertaSubastaRepository,
                            SimpMessagingTemplate messagingTemplate) {
        this.subastaRepository = subastaRepository;
        this.ofertaSubastaRepository = ofertaSubastaRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedRate = 10000)
    @Transactional
    public void finalizarSubastasVencidas() {
        List<Subasta> vencidas = subastaRepository.findExpiredActive();

        for (Subasta subasta : vencidas) {
            subasta.setEstado(EstadoSubasta.FINALIZADA);
            subastaRepository.save(subasta);

            List<OfertaSubasta> ofertas = ofertaSubastaRepository.findBySubastaId(subasta.getIdSubasta());
            Optional<OfertaSubasta> ofertaGanadora = ofertas.stream()
                    .max(Comparator.comparing(o -> o.getMonto().value()));

            GanadorSubastaResponse ganadorResponse;

            if (ofertaGanadora.isPresent()) {
                OfertaSubasta ganadora = ofertaGanadora.get();
                ganadorResponse = new GanadorSubastaResponse(
                        subasta.getIdSubasta(),
                        ganadora.getUsuario().getIdUsuario(),
                        ganadora.getUsuario().getNombre() + " " + ganadora.getUsuario().getApellido(),
                        ganadora.getMonto().value(),
                        "Subasta finalizada automaticamente. \u00a1Felicitaciones al ganador!"
                );
            } else {
                ganadorResponse = new GanadorSubastaResponse(
                        subasta.getIdSubasta(), null, null, null,
                        "Subasta finalizada automaticamente sin ofertas."
                );
            }

            messagingTemplate.convertAndSend(
                    "/topic/subastas/" + subasta.getIdSubasta() + "/resultado",
                    ganadorResponse
            );
        }
    }
}

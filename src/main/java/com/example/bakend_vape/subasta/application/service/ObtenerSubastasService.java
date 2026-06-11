package com.example.bakend_vape.subasta.application.service;

import com.example.bakend_vape.subasta.application.dto.SubastaResponse;
import com.example.bakend_vape.subasta.application.usecase.ObtenerSubastasUseCase;
import com.example.bakend_vape.subasta.domain.model.OfertaSubasta;
import com.example.bakend_vape.subasta.domain.repository.OfertaSubastaRepository;
import com.example.bakend_vape.subasta.domain.repository.SubastaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@Service
public class ObtenerSubastasService implements ObtenerSubastasUseCase {

    private final SubastaRepository subastaRepository;
    private final OfertaSubastaRepository ofertaSubastaRepository;

    public ObtenerSubastasService(SubastaRepository subastaRepository,
                                  OfertaSubastaRepository ofertaSubastaRepository) {
        this.subastaRepository = subastaRepository;
        this.ofertaSubastaRepository = ofertaSubastaRepository;
    }

    @Override
    public List<SubastaResponse> execute() {
        return subastaRepository.findAll().stream().map(subasta -> {
            List<OfertaSubasta> ofertas = ofertaSubastaRepository.findBySubastaId(subasta.getIdSubasta());
            BigDecimal ofertaActual = ofertas.stream()
                    .max(Comparator.comparing(o -> o.getMonto().value()))
                    .map(o -> o.getMonto().value())
                    .orElse(subasta.getPrecioInicial().value());

            return new SubastaResponse(
                    subasta.getIdSubasta(),
                    subasta.getTitulo(),
                    subasta.getDescripcion(),
                    subasta.getSoloVip(),
                    subasta.getPrecioInicial().value(),
                    ofertaActual,
                    subasta.getDuracionMinutos(),
                    subasta.getEstado(),
                    subasta.getCreatedAt()
            );
        }).toList();
    }
}

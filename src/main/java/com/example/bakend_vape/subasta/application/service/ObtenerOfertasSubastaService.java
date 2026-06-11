package com.example.bakend_vape.subasta.application.service;

import com.example.bakend_vape.shared.domain.exception.NotFoundException;
import com.example.bakend_vape.subasta.application.dto.OfertaSubastaResponse;
import com.example.bakend_vape.subasta.application.usecase.ObtenerOfertasSubastaUseCase;
import com.example.bakend_vape.subasta.domain.repository.OfertaSubastaRepository;
import com.example.bakend_vape.subasta.domain.repository.SubastaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObtenerOfertasSubastaService implements ObtenerOfertasSubastaUseCase {

    private final OfertaSubastaRepository ofertaSubastaRepository;
    private final SubastaRepository subastaRepository;

    public ObtenerOfertasSubastaService(OfertaSubastaRepository ofertaSubastaRepository,
                                        SubastaRepository subastaRepository) {
        this.ofertaSubastaRepository = ofertaSubastaRepository;
        this.subastaRepository = subastaRepository;
    }

    @Override
    public List<OfertaSubastaResponse> execute(Long idSubasta) {
        subastaRepository.findById(idSubasta)
                .orElseThrow(() -> new NotFoundException("Subasta no encontrada"));

        return ofertaSubastaRepository.findBySubastaId(idSubasta).stream().map(o ->
                new OfertaSubastaResponse(
                        o.getIdOfertaSubasta(),
                        o.getSubasta().getIdSubasta(),
                        o.getUsuario().getIdUsuario(),
                        o.getUsuario().getNombre() + " " + o.getUsuario().getApellido(),
                        o.getMonto().value(),
                        o.getCreatedAt()
                )
        ).toList();
    }
}

package com.example.bakend_vape.subasta.application.usecase;

import com.example.bakend_vape.subasta.application.dto.HacerOfertaRequest;
import com.example.bakend_vape.subasta.application.dto.OfertaSubastaResponse;

public interface HacerOfertaUseCase {
    OfertaSubastaResponse execute(Long idSubasta, HacerOfertaRequest request);
}

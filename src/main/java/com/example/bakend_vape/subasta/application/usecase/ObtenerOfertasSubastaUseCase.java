package com.example.bakend_vape.subasta.application.usecase;

import com.example.bakend_vape.subasta.application.dto.OfertaSubastaResponse;

import java.util.List;

public interface ObtenerOfertasSubastaUseCase {
    List<OfertaSubastaResponse> execute(Long idSubasta);
}

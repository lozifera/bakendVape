package com.example.bakend_vape.subasta.application.usecase;

import com.example.bakend_vape.subasta.application.dto.GanadorSubastaResponse;

public interface FinalizarSubastaUseCase {
    GanadorSubastaResponse execute(Long idSubasta);
}

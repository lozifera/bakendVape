package com.example.bakend_vape.subasta.application.usecase;

import com.example.bakend_vape.subasta.application.dto.CrearSubastaRequest;
import com.example.bakend_vape.subasta.application.dto.SubastaResponse;

public interface CrearSubastaUseCase {
    SubastaResponse execute(CrearSubastaRequest request);
}

package com.example.bakend_vape.subasta.application.usecase;

import com.example.bakend_vape.subasta.application.dto.SubastaResponse;

import java.util.List;

public interface ObtenerSubastasUseCase {
    List<SubastaResponse> execute();
}

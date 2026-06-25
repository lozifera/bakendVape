package com.example.bakend_vape.producto.application.dto;

public record ProductoFilterParams(
        String search,
        Long categoria,
        Long marca,
        Long atributo,
        String valorAtributo
) {}

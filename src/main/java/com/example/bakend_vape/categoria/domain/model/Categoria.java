package com.example.bakend_vape.categoria.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class Categoria {

    private Long id_categoria;
    private String nombre;
    private LocalDate created_at;
    private LocalDate updated_at;

}

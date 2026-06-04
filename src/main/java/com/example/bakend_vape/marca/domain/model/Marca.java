package com.example.bakend_vape.marca.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Setter
public class Marca {

    private Long id_marca;
    private String nombre;
    private LocalDate created_at;
    private LocalDate updated_at;

}

package com.example.bakend_vape.marca.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class Marca {

    private Long id_marca;
    private String nombre;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}

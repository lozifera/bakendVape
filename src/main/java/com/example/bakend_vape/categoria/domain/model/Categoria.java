package com.example.bakend_vape.categoria.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Categoria {

    private Long id_categoria;
    private String nombre;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}

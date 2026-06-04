package com.example.bakend_vape.aviso.domain.model;

import java.time.LocalDateTime;

public class Aviso {

    private Long idAviso;
    private String titulo;
    private String descripcion;
    private Boolean soloVip;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private  LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

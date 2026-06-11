package com.example.bakend_vape.categoria.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.example.bakend_vape.imagen.application.dto.ImagenResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaResponse {

    private Long idCategoria;
    private String nombre;
    private List<ImagenResponse> imagenes;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

}

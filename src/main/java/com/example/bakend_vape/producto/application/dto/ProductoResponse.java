package com.example.bakend_vape.producto.application.dto;

import com.example.bakend_vape.categoria.application.dto.CategoriaResponse;
import com.example.bakend_vape.imagen.application.dto.ImagenResponse;
import com.example.bakend_vape.marca.application.dto.MarcaResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoResponse {

    private Long idProducto;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private Integer stockMinimo;
    private CategoriaResponse categoria;
    private MarcaResponse marca;
    private List<ImagenResponse> imagenes;
    private List<AtributoEnProductoResponse> atributos;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
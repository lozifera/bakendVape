package com.example.bakend_vape.producto.domain.model;

import com.example.bakend_vape.categoria.domain.model.Categoria;
import com.example.bakend_vape.marca.domain.model.Marca;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
public class Producto {

    private Long idProducto;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private Integer stockMinimo;
    private Categoria categoria;
    private Marca marca;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

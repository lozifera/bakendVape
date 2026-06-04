package com.example.bakend_vape.atributo.domain.model;

import com.example.bakend_vape.producto.domain.model.Producto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ProductoAtributo {

    private Long idProductoAtributo;
    private Producto producto;
    private Atributo atributo;
    private String valor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

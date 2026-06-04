package com.example.bakend_vape.carrito.domain.model;

import com.example.bakend_vape.shared.domain.valueObject.Money;
import com.example.bakend_vape.producto.domain.model.Producto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CarritoProducto {

    private Long idCarritoProducto;
    private Producto producto;
    private Carrito carrito;
    private Integer cantidad;
    private Money precioVenta;
    private Money subtotal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

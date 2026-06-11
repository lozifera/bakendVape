package com.example.bakend_vape.carrito.application.dto;

import com.example.bakend_vape.producto.application.dto.ProductoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CarritoProductoResponse {

    private Long idCarritoProducto;
    private ProductoResponse producto;
    private Integer cantidad;
    private BigDecimal precioVenta;
    private BigDecimal subtotal;
}

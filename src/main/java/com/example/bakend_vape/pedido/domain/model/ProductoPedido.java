package com.example.bakend_vape.pedido.domain.model;

import com.example.bakend_vape.producto.domain.model.Producto;
import com.example.bakend_vape.shared.domain.valueObject.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ProductoPedido {

    private Long idProductoPedido;
    private Pedido pedido;
    private Producto producto;
    private Integer cantidad;
    private Money precioUnitario;
    private Money subtotal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

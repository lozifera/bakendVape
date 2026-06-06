package com.example.bakend_vape.carrito.infrastructure.persistence.entity;

import com.example.bakend_vape.producto.infrastructure.persistence.entity.ProductoEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "carrito_producto")
@Getter
@Setter
@NoArgsConstructor
public class CarritoProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito_producto")
    private Long idCarritoProducto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private ProductoEntity producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carrito", nullable = false)
    private CarritoEntity carrito;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_venta", nullable = false,precision = 10, scale = 2)
    private BigDecimal precioVenta;

    @Column(name = "subtotal", nullable = false,precision = 10, scale = 2)
    private BigDecimal subtotal;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}

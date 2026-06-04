package com.example.bakend_vape.atributo.infrastructure.persistence.entity;

import com.example.bakend_vape.producto.infrastructure.persistence.entity.ProductoEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "producto_atributo")
public class ProductoAtributoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto_atributo")
    private Long idProductoAtributo;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private ProductoEntity  producto;

    @ManyToOne
    @JoinColumn(name = "id_atributo", nullable = false)
    private AtributoEntity atributo;

    @Column(nullable = false, length = 255)
    private String valor;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private java.time.LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private java.time.LocalDateTime updatedAt;

}

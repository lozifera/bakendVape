package com.example.bakend_vape.producto.infrastructure.persistence.entity;


import com.example.bakend_vape.categoria.infrastructure.persistence.entity.CategoriaEntity;
import com.example.bakend_vape.marca.infrastructure.persistence.entity.MarcaEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "producto")
@Getter
@Setter
@NoArgsConstructor
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private int stock;

    @Column(name = "stock_minimo")
    private int stockMinimo;

    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private CategoriaEntity categoria;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private MarcaEntity marca;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

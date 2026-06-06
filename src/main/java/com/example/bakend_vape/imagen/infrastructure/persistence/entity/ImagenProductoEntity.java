package com.example.bakend_vape.imagen.infrastructure.persistence.entity;

import com.example.bakend_vape.producto.infrastructure.persistence.entity.ProductoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "imagen_producto")
@Getter
@Setter
@NoArgsConstructor
public class ImagenProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImagenProducto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_imagen", nullable = false)
    private ImagenEntity imagen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private ProductoEntity producto;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false , updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;



}

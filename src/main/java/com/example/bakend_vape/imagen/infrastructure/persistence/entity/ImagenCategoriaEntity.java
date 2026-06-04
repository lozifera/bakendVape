package com.example.bakend_vape.imagen.infrastructure.persistence.entity;

import com.example.bakend_vape.categoria.infrastructure.persistence.entity.CategoriaEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "imagen_categoria")
public class ImagenCategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IdImagenCategoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_imagen", nullable = false)
    private ImagenEntity imagen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categoria", nullable = false)
    private CategoriaEntity categoria;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}

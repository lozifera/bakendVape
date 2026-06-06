package com.example.bakend_vape.direccion.infrastructure.persistence.entity;

import com.example.bakend_vape.usuario.infrastructure.persistence.entity.UsuarioEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "direccion")
@Getter
@Setter
@NoArgsConstructor
public class DireccionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion")
    private Long idDireccion;

    @Column(length = 255)
    private String direccion;

    @Column(length = 255)
    private String referencia;

    @Column(name = "principal", nullable = false)
    private Boolean principal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false , updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
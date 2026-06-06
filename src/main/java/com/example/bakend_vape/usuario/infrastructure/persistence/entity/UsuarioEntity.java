package com.example.bakend_vape.usuario.infrastructure.persistence.entity;

import com.example.bakend_vape.direccion.infrastructure.persistence.entity.DireccionEntity;
import com.example.bakend_vape.rol.infrastructure.persistence.entity.RolEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor

public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(length = 255)
    private String nombre;

    @Column(length = 255)
    private String apellidos;

    @Column(length = 255)
    private String email;

    @Column(length = 255)
    private String password;

    @Column(name = "es_vip")
    private Boolean esVip;

    @Column(name = "puntos_actuales")
    private Integer puntosActuales;

    @OneToMany(
            mappedBy = "usuario",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<DireccionEntity> direcciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol")
    private RolEntity rol;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false , updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
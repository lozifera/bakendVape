package com.example.bakend_vape.carrito.infrastructure.persistence.entity;

import com.example.bakend_vape.usuario.infrastructure.persistence.entity.UsuarioEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "carrito")
@Getter
@Setter
@NoArgsConstructor
public class CarritoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito")
    private Long idCarrito;

    /**
     * Null cuando el carrito pertenece a un visitante anónimo.
     * Se asigna al usuario cuando se autentica y confirma el pedido.
     */
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = true)
    private UsuarioEntity usuario;

    /**
     * Identificador de sesión HTTP para carritos anónimos.
     * Null cuando el carrito ya está asociado a un usuario.
     */
    @Column(name = "session_id", length = 128)
    private String sessionId;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

package com.example.bakend_vape.puntos.infrastructure.persistence.entity;

import com.example.bakend_vape.puntos.domain.model.MotivoMovimento;
import com.example.bakend_vape.usuario.infrastructure.persistence.entity.UsuarioEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimiento_puntos")
@Getter
@Setter
@NoArgsConstructor
public class MovimientoPuntosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento_puntos")
    private Long idMovimientoPuntos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false, foreignKey = @ForeignKey(name = "fk_movimiento_usuario"))
    private UsuarioEntity usuario;

    @Column(nullable = false)
    private Integer puntos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private MotivoMovimento motivo;

    @Column(name = "referencia_id")
    private Long referenciaId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}

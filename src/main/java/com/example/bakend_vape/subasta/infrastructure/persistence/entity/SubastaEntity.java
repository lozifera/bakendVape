package com.example.bakend_vape.subasta.infrastructure.persistence.entity;

import com.example.bakend_vape.subasta.domain.model.EstadoSubasta;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "subastas")
@Getter
@Setter
@NoArgsConstructor
public class SubastaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subasta")
    private Long idSubasta;

    @Column(nullable = false,length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "solo_vip", nullable = false)
    private Boolean soloVip;

    @Column(name = "precio_inicial", nullable = false,precision = 10, scale = 2)
    private BigDecimal precioInicial;

    @Column(name = "duracion_minutos", nullable = false)
    private Integer duracionMinutos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 30)
    private EstadoSubasta estado;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false , updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

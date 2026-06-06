package com.example.bakend_vape.subasta.infrastructure.persistence.entity;


import com.example.bakend_vape.usuario.infrastructure.persistence.entity.UsuarioEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ofertas_subasta")
@Getter
@Setter
@NoArgsConstructor
public class OfertaSubastaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_oferta_subasta")
    private Long idOfertaSubasta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_subasta", nullable = false)
    private SubastaEntity subasta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;

    @Column(nullable = false,precision = 10, scale = 2)
    private BigDecimal monto;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false , updatable = false)
    private LocalDateTime createdAt;


    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}

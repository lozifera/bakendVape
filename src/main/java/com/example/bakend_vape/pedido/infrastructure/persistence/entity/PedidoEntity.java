package com.example.bakend_vape.pedido.infrastructure.persistence.entity;


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
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario",nullable = false)
    private UsuarioEntity usuario;

    @Column(name = "total" ,nullable = false,precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(nullable = false)
    private Boolean estado;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false , updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;





}

package com.example.bakend_vape.atributo.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "atributo")
@Getter
@Setter
@NoArgsConstructor
public class AtributoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_atributo")
    private Long idAtributo;

    @Column(nullable = false,length = 100)
    private String nombre;

    @Column(length = 50)
    private String unidad;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false , updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private java.time.LocalDateTime updatedAt;

}

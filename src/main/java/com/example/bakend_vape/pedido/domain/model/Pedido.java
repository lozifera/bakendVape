package com.example.bakend_vape.pedido.domain.model;

import com.example.bakend_vape.shared.domain.valueObject.Money;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Pedido {

    private Long idPedido;
    private Usuario usuario;
    private Money total;
    private LocalDateTime fecha;
    private EstadoPedido estado;
    private String direccionEnvio;
    private String referenciaEnvio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}

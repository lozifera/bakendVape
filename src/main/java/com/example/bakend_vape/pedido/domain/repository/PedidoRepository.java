package com.example.bakend_vape.pedido.domain.repository;

import com.example.bakend_vape.pedido.domain.model.EstadoPedido;
import com.example.bakend_vape.pedido.domain.model.Pedido;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PedidoRepository {

    Pedido save(Pedido pedido);

    Optional<Pedido> findById(Long id);

    List<Pedido> findByUsuarioId(Long usuarioId);

    List<Pedido> findAll();

    List<Pedido> findByEstado(EstadoPedido estado);

    long countByUsuarioId(Long usuarioId);

    Optional<LocalDateTime> findUltimaFechaByUsuarioId(Long usuarioId);

}

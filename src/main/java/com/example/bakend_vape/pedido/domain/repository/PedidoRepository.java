package com.example.bakend_vape.pedido.domain.repository;

import com.example.bakend_vape.pedido.domain.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidoRepository {

    Pedido save(Pedido pedido);

    Optional<Pedido> findById(Long id);

    List<Pedido> findByUsuarioId(Long usuarioId);

    List<Pedido> findAll();

}

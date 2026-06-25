package com.example.bakend_vape.pedido.infrastructure.persistence.jpa;

import com.example.bakend_vape.pedido.domain.model.EstadoPedido;
import com.example.bakend_vape.pedido.infrastructure.persistence.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.Optional;

public interface JpaPedidoRepository extends JpaRepository<PedidoEntity, Long> {

    List<PedidoEntity> findByUsuarioIdUsuario(Long idUsuario);

    List<PedidoEntity> findByEstado(EstadoPedido estado);

    long countByUsuarioIdUsuario(Long idUsuario);

    Optional<PedidoEntity> findTopByUsuarioIdUsuarioOrderByCreatedAtDesc(Long idUsuario);

}

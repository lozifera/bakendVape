package com.example.bakend_vape.pedido.infrastructure.persistence.jpa;

import com.example.bakend_vape.pedido.infrastructure.persistence.entity.ProductoPedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaProductoPedidoRepository extends JpaRepository<ProductoPedidoEntity, Long> {

    List<ProductoPedidoEntity> findByPedidoIdPedido(Long idPedido);

}

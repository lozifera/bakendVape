package com.example.bakend_vape.pedido.infrastructure.persistence.jpa;

import com.example.bakend_vape.pedido.infrastructure.persistence.entity.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaPedidoRepository extends JpaRepository<PedidoEntity, Long> {

    List<PedidoEntity> findByUsuarioIdUsuario(Long idUsuario);

}

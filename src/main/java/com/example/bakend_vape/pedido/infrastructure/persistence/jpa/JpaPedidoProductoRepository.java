package com.example.bakend_vape.pedido.infrastructure.persistence.jpa;

import com.example.bakend_vape.pedido.infrastructure.persistence.entity.PedidoProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaPedidoProductoRepository extends JpaRepository<PedidoProductoEntity, Long> {

    List<PedidoProductoEntity> findByPedidoIdPedido(Long idPedido);

    List<PedidoProductoEntity> findByProductoIdProducto(Long idProducto);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PedidoProductoEntity p WHERE p.pedido.idPedido = ?1 AND p.producto.idProducto = ?2")
    boolean existsByPedidoAndProducto(Long idPedido, Long idProducto);

}

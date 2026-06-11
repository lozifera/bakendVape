package com.example.bakend_vape.pedido.domain.repository;

import com.example.bakend_vape.pedido.domain.model.PedidoProducto;

import java.util.List;
import java.util.Optional;

public interface PedidoProductoRepository {

    PedidoProducto save(PedidoProducto pedidoProducto);

    Optional<PedidoProducto> findById(Long idPedidoProducto);

    List<PedidoProducto> findAll();

    void deleteById(Long idPedidoProducto);

    List<PedidoProducto> findByPedidoId(Long idPedido);

    List<PedidoProducto> findByProductoId(Long idProducto);

    boolean existsByPedidoIdAndProductoId(Long idPedido, Long idProducto);

    /**
     * Devuelve el ranking de productos más vendidos.
     * Cada Object[] contiene: [idProducto, nombre, precio, totalVendido, ingresosTotales]
     * @param limite número máximo de productos a retornar
     */
    List<Object[]> findRankingProductos(int limite);
}

package com.example.bakend_vape.pedido.infrastructure.persistence.adapter;

import com.example.bakend_vape.pedido.domain.model.PedidoProducto;
import com.example.bakend_vape.pedido.domain.repository.PedidoProductoRepository;
import com.example.bakend_vape.pedido.infrastructure.mapper.PedidoProductoMapper;
import com.example.bakend_vape.pedido.infrastructure.persistence.entity.PedidoProductoEntity;
import com.example.bakend_vape.pedido.infrastructure.persistence.jpa.JpaPedidoProductoRepository;

import java.util.List;
import java.util.Optional;

public class PedidoProductoRepositoryAdapter implements PedidoProductoRepository {

    private final JpaPedidoProductoRepository jpa;
    private final PedidoProductoMapper mapper;

    public PedidoProductoRepositoryAdapter(JpaPedidoProductoRepository jpa) {
        this.jpa = jpa;
        this.mapper = new PedidoProductoMapper();
    }

    @Override
    public PedidoProducto save(PedidoProducto pedidoProducto) {

        PedidoProductoEntity entity = mapper.toEntity(pedidoProducto);
        PedidoProductoEntity saved = jpa.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<PedidoProducto> findById(Long idProductoPedido) {
        return jpa.findById(idProductoPedido).map(mapper :: toDomain);
    }

    @Override
    public List<PedidoProducto> findAll() {
        return jpa.findAll().stream().map(mapper :: toDomain).toList();
    }

    @Override
    public void deleteById(Long idProductoPedido) {

        jpa.deleteById(idProductoPedido);

    }

    @Override
    public List<PedidoProducto> findByPedidoId(Long idPedido) {
        return jpa.findByPedidoIdPedido(idPedido).stream().map(mapper :: toDomain).toList();
    }

    @Override
    public List<PedidoProducto> findByProductoId(Long idProducto) {
        return jpa.findByProductoIdProducto(idProducto).stream().map(mapper :: toDomain).toList();
    }

    @Override
    public boolean existsByPedidoIdAndProductoId(Long idPedido, Long idProducto) {
        return jpa.existsByPedidoAndProducto(idPedido, idProducto);
    }
}

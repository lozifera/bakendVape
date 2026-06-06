package com.example.bakend_vape.pedido.infrastructure.persistence.adapter;

import com.example.bakend_vape.pedido.domain.model.Pedido;
import com.example.bakend_vape.pedido.domain.repository.PedidoRepository;
import com.example.bakend_vape.pedido.infrastructure.mapper.PedidoMapper;
import com.example.bakend_vape.pedido.infrastructure.persistence.entity.PedidoEntity;
import com.example.bakend_vape.pedido.infrastructure.persistence.jpa.JpaPedidoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PedidoRepositoryAdapter implements PedidoRepository {

    private final JpaPedidoRepository jpa;
    private final PedidoMapper mapper;

        public PedidoRepositoryAdapter(JpaPedidoRepository jpa) {
            this.jpa = jpa;
            this.mapper = new PedidoMapper();
        }

    @Override
    public Pedido save(Pedido pedido) {

        PedidoEntity entity = mapper.toEntity(pedido);
        PedidoEntity saved = jpa.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Pedido> findById(Long id) {
        return jpa.findById(id).map(mapper :: toDomain);
    }

    @Override
    public List<Pedido> findByUsuarioId(Long usuarioId) {
        return jpa.findByUsuarioIdUsuario(usuarioId).stream().map(mapper :: toDomain).toList();
    }

    @Override
    public List<Pedido> findAll() {
        return jpa.findAll().stream().map(mapper :: toDomain).toList();
    }
}

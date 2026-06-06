package com.example.bakend_vape.pedido.infrastructure.mapper;

import com.example.bakend_vape.pedido.domain.model.Pedido;
import com.example.bakend_vape.pedido.infrastructure.persistence.entity.PedidoEntity;
import com.example.bakend_vape.shared.domain.valueObject.Money;
import com.example.bakend_vape.usuario.infrastructure.mapper.UsuarioMapper;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    private final UsuarioMapper usuarioMapper = new UsuarioMapper();

    public Pedido toDomain(PedidoEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Pedido(
                entity.getIdPedido(),
                usuarioMapper.toDomain(entity.getUsuario()),
                new Money(entity.getTotal()),
                entity.getFecha(),
                entity.getEstado(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public PedidoEntity toEntity(Pedido pedido) {
        if (pedido == null) {
            return null;
        }
        PedidoEntity entity = new PedidoEntity();
        entity.setIdPedido(pedido.getIdPedido());
        entity.setUsuario(usuarioMapper.toEntity(pedido.getUsuario()));
        entity.setTotal(pedido.getTotal().value());
        entity.setFecha(pedido.getFecha());
        entity.setEstado(pedido.getEstado());
        entity.setCreatedAt(pedido.getCreatedAt());
        entity.setUpdatedAt(pedido.getUpdatedAt());
        return entity;
    }

}

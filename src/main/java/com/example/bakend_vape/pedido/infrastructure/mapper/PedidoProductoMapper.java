package com.example.bakend_vape.pedido.infrastructure.mapper;

import com.example.bakend_vape.pedido.domain.model.PedidoProducto;
import com.example.bakend_vape.pedido.infrastructure.persistence.entity.PedidoProductoEntity;
import com.example.bakend_vape.producto.infrastructure.mapper.ProductoMapper;
import com.example.bakend_vape.shared.domain.valueObject.Money;
import org.springframework.stereotype.Component;

@Component
public class PedidoProductoMapper {

    private final ProductoMapper productoMapper = new ProductoMapper();
    private final PedidoMapper pedidoMapper = new PedidoMapper();


    public PedidoProducto toDomain(PedidoProductoEntity entity) {
        if (entity == null) {
            return null;
        }
        return new PedidoProducto(
                entity.getIdPedidoProducto(),
                pedidoMapper.toDomain(entity.getPedido()),
                productoMapper.toDomain(entity.getProducto()),
                entity.getCantidad(),
                new Money(entity.getPrecioUnitario()),
                new Money(entity.getSubtotal()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public PedidoProductoEntity toEntity(PedidoProducto pedidoProducto) {
        if (pedidoProducto == null) {
            return null;
        }
        PedidoProductoEntity entity = new PedidoProductoEntity();
        entity.setIdPedidoProducto(pedidoProducto.getIdPedidoProducto());
        entity.setPedido(pedidoMapper.toEntity(pedidoProducto.getPedido()));
        entity.setProducto(productoMapper.toEntity(pedidoProducto.getProducto()));
        entity.setCantidad(pedidoProducto.getCantidad());
        entity.setPrecioUnitario(pedidoProducto.getPrecioUnitario().value());
        entity.setSubtotal(pedidoProducto.getSubtotal().value());
        return entity;
    }

}

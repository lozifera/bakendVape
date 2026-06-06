package com.example.bakend_vape.atributo.infrastructure.mapper;

import com.example.bakend_vape.atributo.domain.model.ProductoAtributo;
import com.example.bakend_vape.atributo.infrastructure.persistence.entity.ProductoAtributoEntity;
import com.example.bakend_vape.producto.infrastructure.mapper.ProductoMapper;
import org.springframework.stereotype.Component;

@Component
public class ProductoAtributoMapper {

    private final ProductoMapper productoMapper = new ProductoMapper();

    private final AtributoMapper atributoMapper = new AtributoMapper();


    public ProductoAtributo toDomain(ProductoAtributoEntity entity) {
        if (entity == null) {
            return null;
        }
        return new ProductoAtributo(
                entity.getIdProductoAtributo(),
                productoMapper.toDomain(entity.getProducto()),
                atributoMapper.toDomain(entity.getAtributo()),
                entity.getValor(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public ProductoAtributoEntity toEntity(ProductoAtributo productoAtributo) {
        if (productoAtributo == null) {
            return null;
        }
        ProductoAtributoEntity entity = new ProductoAtributoEntity();
        entity.setIdProductoAtributo(productoAtributo.getIdProductoAtributo());
        entity.setProducto(productoMapper.toEntity(productoAtributo.getProducto()));
        entity.setAtributo(atributoMapper.toEntity(productoAtributo.getAtributo()));
        entity.setValor(productoAtributo.getValor());

        return entity;
    }

}

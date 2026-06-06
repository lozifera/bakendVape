package com.example.bakend_vape.carrito.infrastructure.mapper;

import com.example.bakend_vape.carrito.domain.model.CarritoProducto;
import com.example.bakend_vape.carrito.infrastructure.persistence.entity.CarritoProductoEntity;
import com.example.bakend_vape.producto.infrastructure.mapper.ProductoMapper;
import com.example.bakend_vape.shared.domain.valueObject.Money;
import org.springframework.stereotype.Component;

@Component
public class CarritoProductoMapper {

    private final ProductoMapper productoMapper = new ProductoMapper();
    private final CarritoMapper carritoMapper = new CarritoMapper();


    public CarritoProducto toDomain(CarritoProductoEntity entity){
        if (entity == null) {
            return null;
        }
        return new CarritoProducto(
                entity.getIdCarritoProducto(),
                productoMapper.toDomain(entity.getProducto()),
                carritoMapper.toDomain(entity.getCarrito()),
                entity.getCantidad(),
                new Money(entity.getPrecioVenta()),
                new Money(entity.getSubtotal()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public CarritoProductoEntity toEntity(CarritoProducto carritoProducto){
        if (carritoProducto == null) {
            return null;
        }
        CarritoProductoEntity entity = new CarritoProductoEntity();
        entity.setIdCarritoProducto(carritoProducto.getIdCarritoProducto());
        entity.setProducto(productoMapper.toEntity(carritoProducto.getProducto()));
        entity.setCarrito(carritoMapper.toEntity(carritoProducto.getCarrito()));
        entity.setCantidad(carritoProducto.getCantidad());
        entity.setPrecioVenta(carritoProducto.getPrecioVenta().value());
        entity.setSubtotal(carritoProducto.getSubtotal().value());

        return entity;
    }

}

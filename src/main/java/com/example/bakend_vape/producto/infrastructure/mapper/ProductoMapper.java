package com.example.bakend_vape.producto.infrastructure.mapper;

import com.example.bakend_vape.categoria.infrastructure.mapper.CategoriaMapper;
import com.example.bakend_vape.marca.infrastructure.mapper.MarcaMapper;
import com.example.bakend_vape.producto.domain.model.Producto;
import com.example.bakend_vape.producto.infrastructure.persistence.entity.ProductoEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    private final CategoriaMapper categoriaMapper = new CategoriaMapper();
    private final MarcaMapper marcaMapper = new MarcaMapper();



    public Producto toDomain(ProductoEntity entity){
        if (entity == null) {
            return null;
        }
        return new Producto(
                entity.getIdProducto(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getPrecio(),
                entity.getStock(),
                entity.getStockMinimo(),
                categoriaMapper.toDomain(entity.getCategoria()),
                marcaMapper.toDomain(entity.getMarca()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public ProductoEntity toEntity(Producto producto){
        if (producto == null) {
            return null;
        }
        ProductoEntity entity = new ProductoEntity();
        entity.setIdProducto(producto.getIdProducto());
        entity.setNombre(producto.getNombre());
        entity.setDescripcion(producto.getDescripcion());
        entity.setPrecio(producto.getPrecio());
        entity.setStock(producto.getStock());
        entity.setStockMinimo(producto.getStockMinimo());
        entity.setCategoria(categoriaMapper.toEntity(producto.getCategoria()));
        entity.setMarca(marcaMapper.toEntity(producto.getMarca()));
        return entity;
    }
}

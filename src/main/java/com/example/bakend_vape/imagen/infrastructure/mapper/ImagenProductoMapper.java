package com.example.bakend_vape.imagen.infrastructure.mapper;

import com.example.bakend_vape.imagen.domain.model.ImagenProducto;
import com.example.bakend_vape.imagen.infrastructure.persistence.entity.ImagenProductoEntity;
import com.example.bakend_vape.producto.infrastructure.mapper.ProductoMapper;
import org.springframework.stereotype.Component;

@Component
public class ImagenProductoMapper {

    private final ImagenMapper imagenMapper = new ImagenMapper();
    private final ProductoMapper productoMapper = new ProductoMapper();



    public ImagenProducto toDomain(ImagenProductoEntity entity){
        if (entity == null) {
            return null;
        }
        return new ImagenProducto(
                entity.getIdImagenProducto(),
                imagenMapper.toDomain(entity.getImagen()),
                productoMapper.toDomain(entity.getProducto()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public ImagenProductoEntity toEntity(ImagenProducto imagenProducto){
        if (imagenProducto == null) {
            return null;
        }
        ImagenProductoEntity entity = new ImagenProductoEntity();
        entity.setIdImagenProducto(imagenProducto.getIdImagenProducto());
        entity.setImagen(imagenMapper.toEntity(imagenProducto.getImagen()));
        entity.setProducto(productoMapper.toEntity(imagenProducto.getProducto()));
        return entity;
    }
}

package com.example.bakend_vape.imagen.infrastructure.mapper;

import com.example.bakend_vape.categoria.infrastructure.mapper.CategoriaMapper;
import com.example.bakend_vape.imagen.domain.model.ImagenCategoria;
import com.example.bakend_vape.imagen.infrastructure.persistence.entity.ImagenCategoriaEntity;
import org.springframework.stereotype.Component;

@Component
public class ImagenCategoriaMapper {

    private final ImagenMapper imagenMapper = new ImagenMapper();
    private final CategoriaMapper categoriaMapper = new CategoriaMapper();



    public ImagenCategoria toDomain(ImagenCategoriaEntity entity){
        if (entity == null) {
            return null;
        }
        return new ImagenCategoria(
                entity.getIdImagenCategoria(),
                imagenMapper.toDomain(entity.getImagen()),
                categoriaMapper.toDomain(entity.getCategoria()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public ImagenCategoriaEntity toEntity(ImagenCategoria domain){
        if (domain == null) {
            return null;
        }
        return new ImagenCategoriaEntity(
                domain.getIdImagenCategoria(),
                imagenMapper.toEntity(domain.getImagen()),
                categoriaMapper.toEntity(domain.getCategoria()),
                domain.getCreatedAt(),
                domain.getUpdatedAt()
        );
    }
}

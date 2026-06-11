package com.example.bakend_vape.imagen.infrastructure.mapper;

import com.example.bakend_vape.imagen.domain.model.Imagen;
import com.example.bakend_vape.imagen.infrastructure.persistence.entity.ImagenEntity;
import org.springframework.stereotype.Component;

@Component
public class ImagenMapper {

    public Imagen toDomain(ImagenEntity entity){
        if (entity == null){
            return null;
        }
        return new Imagen(
                entity.getIdImagen(),
                entity.getUrl(),
                entity.getNombre(),
                entity.getEstado(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public ImagenEntity toEntity(Imagen imagen){
        if (imagen == null){
            return null;
        }
        ImagenEntity entity = new ImagenEntity();
        entity.setIdImagen(imagen.getIdImagen());
        entity.setUrl(imagen.getUrl());
        entity.setNombre(imagen.getNombre());
        entity.setEstado(imagen.getEstado());
        return entity;
    }

}

package com.example.bakend_vape.atributo.infrastructure.mapper;

import com.example.bakend_vape.atributo.domain.model.Atributo;
import com.example.bakend_vape.atributo.infrastructure.persistence.entity.AtributoEntity;
import org.springframework.stereotype.Component;

@Component
public class AtributoMapper {

    public Atributo toDomain(AtributoEntity entity){
        if (entity == null) {
            return null;
        }
        return new Atributo(
                entity.getIdAtributo(),
                entity.getNombre(),
                entity.getUnidad(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public AtributoEntity toEntity(Atributo atributo){
        if (atributo == null) {
            return null;
        }
        AtributoEntity entity = new AtributoEntity();
        entity.setIdAtributo(atributo.getIdAtributo());
        entity.setNombre(atributo.getNombre());
        entity.setUnidad(atributo.getUnidad());
        return entity;
    }



}

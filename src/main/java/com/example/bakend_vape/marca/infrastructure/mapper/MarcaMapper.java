package com.example.bakend_vape.marca.infrastructure.mapper;

import com.example.bakend_vape.marca.domain.model.Marca;
import com.example.bakend_vape.marca.infrastructure.persistence.entity.MarcaEntity;
import org.springframework.stereotype.Component;

@Component
public class MarcaMapper {

    public Marca toDomain(MarcaEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Marca(
                entity.getIdMarca(),
                entity.getNombre(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public MarcaEntity toEntity(Marca domain) {
        if (domain == null) {
            return null;
        }
        MarcaEntity entity = new MarcaEntity();
        entity.setIdMarca(domain.getId_marca());
        entity.setNombre(domain.getNombre());

        return entity;
    }

}

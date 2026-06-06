package com.example.bakend_vape.rol.infrastructure.mapper;

import com.example.bakend_vape.rol.domain.model.Rol;
import com.example.bakend_vape.rol.infrastructure.persistence.entity.RolEntity;
import org.springframework.stereotype.Component;

@Component
public class RolMapper {

    public Rol toDomain(RolEntity entity){
        if (entity == null) {
            return null;
        }
        return new Rol(
                entity.getIdRol(),
                entity.getNombre(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public RolEntity toEntity(Rol rol){
        if (rol == null) {
            return null;
        }
        RolEntity entity = new RolEntity();
        // Mantener los getters del modelo de dominio tal como están en tu proyecto
        entity.setIdRol(rol.getId_rol());
        entity.setNombre(rol.getNombre());
        entity.setCreatedAt(rol.getCreated_at());
        entity.setUpdatedAt(rol.getUpdated_at());
        return entity;
    }

}

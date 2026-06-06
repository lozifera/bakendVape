package com.example.bakend_vape.direccion.infrastructure.mapper;

import com.example.bakend_vape.direccion.domain.model.Direccion;
import com.example.bakend_vape.direccion.infrastructure.persistence.entity.DireccionEntity;
import com.example.bakend_vape.usuario.infrastructure.mapper.UsuarioMapper;
import org.springframework.stereotype.Component;

@Component
public class DireccionMapper {

    private UsuarioMapper usuarioMapper = new UsuarioMapper();



    public Direccion toDomain(DireccionEntity entity) {
        if (entity == null) return null;
        return new Direccion(
                entity.getIdDireccion(),
                entity.getDireccion(),
                entity.getReferencia(),
                entity.getPrincipal(),
                usuarioMapper != null ? usuarioMapper.toDomain(entity.getUsuario()) : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public DireccionEntity toEntity(Direccion direccion) {
        if (direccion == null) return null;
        DireccionEntity entity = new DireccionEntity();
        entity.setIdDireccion(direccion.getId_direccion());
        entity.setDireccion(direccion.getDireccion());
        entity.setReferencia(direccion.getReferencia());
        entity.setPrincipal(direccion.getPrincipal());
        if (usuarioMapper != null && direccion.getUsuario() != null) {
            entity.setUsuario(usuarioMapper.toEntity(direccion.getUsuario()));
        }
        return entity;
    }

}

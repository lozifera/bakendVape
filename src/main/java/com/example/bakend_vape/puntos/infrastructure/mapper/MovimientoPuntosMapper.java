package com.example.bakend_vape.puntos.infrastructure.mapper;

import com.example.bakend_vape.puntos.domain.model.MovimientoPuntos;
import com.example.bakend_vape.puntos.infrastructure.persistence.entity.MovimientoPuntosEntity;
import com.example.bakend_vape.shared.domain.valueObject.Puntos;
import com.example.bakend_vape.usuario.infrastructure.mapper.UsuarioMapper;
import org.springframework.stereotype.Component;

@Component
public class MovimientoPuntosMapper {

    private final UsuarioMapper usuarioMapper = new UsuarioMapper();

    public MovimientoPuntos toDomain(MovimientoPuntosEntity entity) {
        if (entity == null) return null;
        return new MovimientoPuntos(
                entity.getIdMovimientoPuntos(),
                usuarioMapper != null ? usuarioMapper.toDomain(entity.getUsuario()) : null,
                new Puntos(entity.getPuntos()),
                entity.getMotivo(),
                entity.getReferenciaId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
    public MovimientoPuntosEntity toEntity(MovimientoPuntos movimientoPuntos) {
        if (movimientoPuntos == null) return null;
        MovimientoPuntosEntity entity = new MovimientoPuntosEntity();
        if (movimientoPuntos.getIdMovimientoPuntos() != null) {
            entity.setIdMovimientoPuntos(movimientoPuntos.getIdMovimientoPuntos());
        }
        entity.setUsuario(usuarioMapper != null ? usuarioMapper.toEntity(movimientoPuntos.getUsuario()) : null);
        entity.setPuntos(movimientoPuntos.getPuntos().value());
        entity.setMotivo(movimientoPuntos.getMotivo());
        entity.setReferenciaId(movimientoPuntos.getReferenciaId());

        return entity;
    }

}

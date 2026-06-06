package com.example.bakend_vape.aviso.infrastructure.mapper;

import com.example.bakend_vape.aviso.domain.model.Aviso;
import com.example.bakend_vape.aviso.infrastructure.persistence.entity.AvisoEntity;
import com.example.bakend_vape.usuario.infrastructure.mapper.UsuarioMapper;
import org.springframework.stereotype.Component;

@Component
public class AvisoMapper {

    private final UsuarioMapper usuarioMapper = new UsuarioMapper();


    public Aviso toDomain(AvisoEntity entity){
    if (entity == null) {
        return null;
    }
        return new Aviso(
                entity.getIdAviso(),
                entity.getTitulo(),
                entity.getDescripcion(),
                entity.getSoloVip(),
                entity.getFechaInicio(),
                entity.getFechaFin(),
                usuarioMapper.toDomain(entity.getUsuario()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public AvisoEntity toEntity(Aviso aviso){
        if (aviso == null) {
            return null;
        }

        AvisoEntity entity = new AvisoEntity();
        entity.setIdAviso(aviso.getIdAviso());
        entity.setTitulo(aviso.getTitulo());
        entity.setDescripcion(aviso.getDescripcion());
        entity.setSoloVip(aviso.getSoloVip());
        entity.setFechaInicio(aviso.getFechaInicio());
        entity.setFechaFin(aviso.getFechaFin());
        entity.setUsuario(usuarioMapper.toEntity(aviso.getUsuario()));

        return entity;
    }

}

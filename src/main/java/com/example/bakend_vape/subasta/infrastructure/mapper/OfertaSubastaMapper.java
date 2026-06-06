package com.example.bakend_vape.subasta.infrastructure.mapper;

import com.example.bakend_vape.shared.domain.valueObject.Money;
import com.example.bakend_vape.subasta.domain.model.OfertaSubasta;
import com.example.bakend_vape.subasta.infrastructure.persistence.entity.OfertaSubastaEntity;
import com.example.bakend_vape.usuario.infrastructure.mapper.UsuarioMapper;
import org.springframework.stereotype.Component;

@Component
public class OfertaSubastaMapper {

    private final UsuarioMapper usuarioMapper = new UsuarioMapper();
    private final SubastaMapper subastaMapper = new SubastaMapper();


    public OfertaSubasta toDomain(OfertaSubastaEntity entity){
        if (entity == null) {
            return null;
        }
        return new OfertaSubasta(
                entity.getIdOfertaSubasta(),
                subastaMapper.toDomain(entity.getSubasta()),
                usuarioMapper.toDomain(entity.getUsuario()),
                new Money(entity.getMonto()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public OfertaSubastaEntity toEntity(OfertaSubasta ofertaSubasta){
        if (ofertaSubasta == null) {
            return null;
        }
        OfertaSubastaEntity entity = new OfertaSubastaEntity();
        entity.setIdOfertaSubasta(ofertaSubasta.getIdOfertaSubasta());
        entity.setSubasta(subastaMapper.toEntity(ofertaSubasta.getSubasta()));
        entity.setUsuario(usuarioMapper.toEntity(ofertaSubasta.getUsuario()));
        entity.setMonto(ofertaSubasta.getMonto().value());

        return entity;
    }
}

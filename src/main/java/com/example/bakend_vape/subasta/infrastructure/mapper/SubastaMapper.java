package com.example.bakend_vape.subasta.infrastructure.mapper;

import com.example.bakend_vape.shared.domain.valueObject.Money;
import com.example.bakend_vape.subasta.domain.model.Subasta;
import com.example.bakend_vape.subasta.infrastructure.persistence.entity.SubastaEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SubastaMapper {

    public Subasta toDomain(SubastaEntity entity){
        if (entity == null) {
            return null;
        }
        return new Subasta(
                entity.getIdSubasta(),
                entity.getTitulo(),
                entity.getDescripcion(),
                entity.getSoloVip(),
                new Money(entity.getPrecioInicial()),
                entity.getDuracionMinutos(),
                entity.getEstado(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );


    }

    public SubastaEntity toEntity(Subasta subasta){
        if (subasta == null) {
            return null;
        }
        SubastaEntity entity = new SubastaEntity();
        entity.setIdSubasta(subasta.getIdSubasta());
        entity.setTitulo(subasta.getTitulo());
        entity.setDescripcion(subasta.getDescripcion());
        entity.setSoloVip(subasta.getSoloVip());

        BigDecimal precio = subasta.getPrecioInicial() != null ? subasta.getPrecioInicial().value() : null;
        entity.setPrecioInicial(precio);

        entity.setDuracionMinutos(subasta.getDuracionMinutos());
        entity.setEstado(subasta.getEstado());

        return entity;
    }

}

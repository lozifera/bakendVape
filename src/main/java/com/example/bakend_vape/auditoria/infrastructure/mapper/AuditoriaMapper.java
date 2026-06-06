package com.example.bakend_vape.auditoria.infrastructure.mapper;

import com.example.bakend_vape.auditoria.domain.model.Auditoria;
import com.example.bakend_vape.auditoria.infrastructure.persistence.entity.AuditoriaEntity;
import org.springframework.stereotype.Component;

@Component
public class AuditoriaMapper {

    public Auditoria toDomain(AuditoriaEntity entity){
        return new Auditoria(
                entity.getIdAuditoria(),
                null, // Usuario no se mapea directamente
                entity.getAccion(),
                entity.getTabla(),
                entity.getRegistroId(),
                entity.getValorAnterior(),
                entity.getValorNuevo(),
                entity.getFecha()
        );
    }

    public AuditoriaEntity toEntity(Auditoria auditoria){
        AuditoriaEntity entity = new AuditoriaEntity();
        entity.setIdAuditoria(auditoria.getIdAuditoria());
        // Usuario no se mapea directamente
        entity.setAccion(auditoria.getAccion());
        entity.setTabla(auditoria.getTabla());
        entity.setRegistroId(auditoria.getRegistroId());
        entity.setValorAnterior(auditoria.getValorAnterior());
        entity.setValorNuevo(auditoria.getValorNuevo());
        entity.setFecha(auditoria.getFecha());
        return entity;
    }
}

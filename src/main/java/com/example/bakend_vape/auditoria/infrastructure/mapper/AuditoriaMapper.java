package com.example.bakend_vape.auditoria.infrastructure.mapper;

import com.example.bakend_vape.auditoria.domain.model.Auditoria;
import com.example.bakend_vape.auditoria.infrastructure.persistence.entity.AuditoriaEntity;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import com.example.bakend_vape.usuario.infrastructure.persistence.entity.UsuarioEntity;
import com.example.bakend_vape.usuario.infrastructure.persistence.jpa.JpaUsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class AuditoriaMapper {

    private final JpaUsuarioRepository usuarioJpaRepository;

    public AuditoriaMapper(JpaUsuarioRepository usuarioJpaRepository) {
        this.usuarioJpaRepository = usuarioJpaRepository;
    }

    public AuditoriaEntity toEntity(Auditoria auditoria, Usuario usuario) {

        AuditoriaEntity entity = new AuditoriaEntity();

        entity.setIdAuditoria(auditoria.getIdAuditoria());

        // 🔥 ESTO ES LO QUE TE FALTABA
        UsuarioEntity usuarioEntity =
                usuarioJpaRepository.findById(usuario.getIdUsuario())
                        .orElseThrow();

        entity.setUsuario(usuarioEntity);

        entity.setAccion(auditoria.getAccion());
        entity.setTabla(auditoria.getTabla());
        entity.setRegistroId(auditoria.getRegistroId());
        entity.setValorAnterior(auditoria.getValorAnterior());
        entity.setValorNuevo(auditoria.getValorNuevo());
        entity.setFecha(auditoria.getFecha());

        return entity;
    }

    public Auditoria toDomain(AuditoriaEntity entity) {
        return new Auditoria(
                entity.getIdAuditoria(),
                null,
                entity.getAccion(),
                entity.getTabla(),
                entity.getRegistroId(),
                entity.getValorAnterior(),
                entity.getValorNuevo(),
                entity.getFecha()
        );
    }
}
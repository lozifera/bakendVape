package com.example.bakend_vape.usuario.infrastructure.mapper;

import com.example.bakend_vape.rol.infrastructure.mapper.RolMapper;
import com.example.bakend_vape.usuario.domain.model.Usuario;
import com.example.bakend_vape.usuario.domain.valueObject.Email;
import com.example.bakend_vape.usuario.domain.valueObject.Password;
import com.example.bakend_vape.usuario.infrastructure.persistence.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    private final RolMapper rolMapper = new RolMapper();

    public Usuario toDomain(UsuarioEntity entity){
        if (entity == null) return null;
        return new Usuario(
                entity.getIdUsuario(),
                entity.getNombre(),
                entity.getApellidos(),
                entity.getEmail() != null ? new Email(entity.getEmail()) : null,
                entity.getPassword() != null ? new Password(entity.getPassword()) : null,
                entity.getEsVip(),
                entity.getPuntosActuales(),
                rolMapper.toDomain(entity.getRol()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public UsuarioEntity toEntity(Usuario usuario){
        if (usuario == null) return null;
        UsuarioEntity entity = new UsuarioEntity();
        if (usuario.getId_usuario() != null) {
            entity.setIdUsuario(usuario.getId_usuario());
        }
        entity.setNombre(usuario.getNombre());
        entity.setApellidos(usuario.getApellido());
        entity.setEmail(usuario.getEmail() != null ? usuario.getEmail().getValue() : null);
        entity.setPassword(usuario.getPassword() != null ? usuario.getPassword().getValue() : null);
        entity.setEsVip(usuario.getEs_vip());
        entity.setPuntosActuales(usuario.getPuntos_actuales());
        entity.setRol(usuario.getRol() != null ? rolMapper.toEntity(usuario.getRol()) : null);

        return entity;
    }

}

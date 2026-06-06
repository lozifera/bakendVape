package com.example.bakend_vape.valoracion.infrastructure.mapper;

import com.example.bakend_vape.producto.infrastructure.mapper.ProductoMapper;
import com.example.bakend_vape.usuario.infrastructure.mapper.UsuarioMapper;
import com.example.bakend_vape.valoracion.domain.model.Valoracion;
import com.example.bakend_vape.valoracion.infrastructure.persistence.entity.ValoracionEntity;
import org.springframework.stereotype.Component;

@Component
public class ValoracionMapper {

    private final UsuarioMapper usuarioMapper = new UsuarioMapper();
    private final ProductoMapper productoMapper = new ProductoMapper();


    public Valoracion toDomain(ValoracionEntity entity){
        if (entity == null) {
            return null;
        }
        return new Valoracion(
                entity.getIdValoracion(),
                usuarioMapper.toDomain(entity.getUsuario()),
                productoMapper.toDomain(entity.getProducto()),
                entity.getPuntuacion(),
                entity.getComentario(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public ValoracionEntity toEntity(Valoracion valoracion){
        if (valoracion == null) {
            return null;
        }
        ValoracionEntity entity = new ValoracionEntity();
        entity.setIdValoracion(valoracion.getIdValoracion());
        entity.setUsuario(usuarioMapper.toEntity(valoracion.getUsuario()));
        entity.setProducto(productoMapper.toEntity(valoracion.getProducto()));
        entity.setPuntuacion(valoracion.getPuntuacion());
        entity.setComentario(valoracion.getComentario());
        entity.setCreatedAt(valoracion.getCreatedAt());
        entity.setUpdatedAt(valoracion.getUpdatedAt());
        return entity;
    }
}

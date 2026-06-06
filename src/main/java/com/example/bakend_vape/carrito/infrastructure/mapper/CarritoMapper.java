package com.example.bakend_vape.carrito.infrastructure.mapper;

import com.example.bakend_vape.carrito.domain.model.Carrito;
import com.example.bakend_vape.carrito.infrastructure.persistence.entity.CarritoEntity;
import com.example.bakend_vape.usuario.infrastructure.mapper.UsuarioMapper;
import org.springframework.stereotype.Component;

@Component
public class CarritoMapper {

    private final UsuarioMapper usuarioMapper = new UsuarioMapper();



    public Carrito toDomain(CarritoEntity entity){
        if (entity == null) {
            return null;
        }
        return new Carrito(

                entity.getIdCarrito(),
                usuarioMapper.toDomain(entity.getUsuario()),
                entity.getActivo(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()


        );

    }

    public CarritoEntity toEntity(Carrito carrito){
        if (carrito == null) {
            return null;
        }
        CarritoEntity entity = new CarritoEntity();
        entity.setIdCarrito(carrito.getIdCarrito());
        entity.setUsuario(usuarioMapper.toEntity(carrito.getUsuario()));
        entity.setActivo(carrito.getActivo());

        return entity;
    }

}

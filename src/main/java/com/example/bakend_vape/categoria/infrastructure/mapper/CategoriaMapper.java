package com.example.bakend_vape.categoria.infrastructure.mapper;

import com.example.bakend_vape.categoria.domain.model.Categoria;
import com.example.bakend_vape.categoria.infrastructure.persistence.entity.CategoriaEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public Categoria toDomain(CategoriaEntity entity){
        if (entity == null){
            return null;
        }
        return new Categoria(
                entity.getIdCategoria(),
                entity.getNombre(),
                entity.getCreatedAt().toLocalDate(),
                entity.getUpdatedAt() != null ? entity.getUpdatedAt().toLocalDate() : null
        );
    }

        public CategoriaEntity toEntity(Categoria categoria){
            if (categoria == null){
                return null;
            }
            CategoriaEntity entity = new CategoriaEntity();
            entity.setIdCategoria(categoria.getId_categoria());
            entity.setNombre(categoria.getNombre());

            return entity;
        }

}

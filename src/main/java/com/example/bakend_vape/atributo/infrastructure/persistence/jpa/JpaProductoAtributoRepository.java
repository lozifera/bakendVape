package com.example.bakend_vape.atributo.infrastructure.persistence.jpa;

import com.example.bakend_vape.atributo.infrastructure.persistence.entity.ProductoAtributoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaProductoAtributoRepository extends JpaRepository<ProductoAtributoEntity, Long> {

    List<ProductoAtributoEntity> findByProductoIdProducto(Long idProducto);

    List<ProductoAtributoEntity> findByAtributoIdAtributoAndValor(Long idAtributo, String valor);

}

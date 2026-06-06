package com.example.bakend_vape.atributo.infrastructure.persistence.adapter;

import com.example.bakend_vape.atributo.domain.model.ProductoAtributo;
import com.example.bakend_vape.atributo.domain.repository.ProductoAtributoRepository;
import com.example.bakend_vape.atributo.infrastructure.mapper.ProductoAtributoMapper;
import com.example.bakend_vape.atributo.infrastructure.persistence.entity.ProductoAtributoEntity;
import com.example.bakend_vape.atributo.infrastructure.persistence.jpa.JpaProductoAtributoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ProductoAtributoAdapter implements ProductoAtributoRepository {

    private final JpaProductoAtributoRepository jpa;
    private final ProductoAtributoMapper mapper;

    public ProductoAtributoAdapter(JpaProductoAtributoRepository jpa, ProductoAtributoMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public ProductoAtributo save(ProductoAtributo productoAtributo) {

        ProductoAtributoEntity entity = mapper.toEntity(productoAtributo);
        ProductoAtributoEntity saved = jpa.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ProductoAtributo> findById(Long id) {
        return jpa.findById(id).map(mapper::toDomain);
    }
}

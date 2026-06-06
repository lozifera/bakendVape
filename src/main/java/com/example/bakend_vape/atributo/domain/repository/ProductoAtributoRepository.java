package com.example.bakend_vape.atributo.domain.repository;

import com.example.bakend_vape.atributo.domain.model.ProductoAtributo;

import java.util.Optional;

public interface ProductoAtributoRepository {


    ProductoAtributo save(ProductoAtributo productoAtributo);

    Optional<ProductoAtributo> findById(Long id);

}

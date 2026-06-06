package com.example.bakend_vape.producto.infrastructure.persistence.adapter;

import com.example.bakend_vape.producto.domain.model.Producto;
import com.example.bakend_vape.producto.domain.repository.ProductoRepository;
import com.example.bakend_vape.producto.infrastructure.mapper.ProductoMapper;
import com.example.bakend_vape.producto.infrastructure.persistence.entity.ProductoEntity;
import com.example.bakend_vape.producto.infrastructure.persistence.jpa.JpaProductoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepositoryAdapter implements ProductoRepository {

    private final JpaProductoRepository jpa;
    private final ProductoMapper mapper;

    public ProductoRepositoryAdapter(JpaProductoRepository jpa) {
        this.jpa = jpa;
        this.mapper = new ProductoMapper();
    }

    @Override
    public Producto save(Producto producto) {

        ProductoEntity entity = mapper.toEntity(producto);
        ProductoEntity saved = jpa.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Producto> findById(Long id) {
        return jpa.findById(id).map(mapper :: toDomain);
    }

    @Override
    public List<Producto> findAll() {
        return jpa.findAll().stream().map(mapper :: toDomain).toList();
    }

    @Override
    public List<Producto> findByCategoriaId(Long categoriaId) {
        return jpa.findByCategoriaIdCategoria(categoriaId).stream().map(mapper :: toDomain).toList();
    }

    @Override
    public Optional<Producto> findByNombre(String nombre) {
        return jpa.findByNombre(nombre).map(mapper :: toDomain);
    }

    @Override
    public List<Producto> findByMarcaId(Long marcaId) {
        return jpa.findByMarcaIdMarca(marcaId).stream().map(mapper :: toDomain).toList();
    }

    @Override
    public Boolean existsByNombre(String nombre) {
        return jpa.existsByNombre(nombre);
    }

    @Override
    public void delete(Long id) {
        jpa.deleteById(id);
    }
}

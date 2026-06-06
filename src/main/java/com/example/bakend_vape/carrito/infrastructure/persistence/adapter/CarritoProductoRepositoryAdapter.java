package com.example.bakend_vape.carrito.infrastructure.persistence.adapter;

import com.example.bakend_vape.carrito.domain.model.CarritoProducto;
import com.example.bakend_vape.carrito.domain.repository.CarritoProductoRepository;
import com.example.bakend_vape.carrito.infrastructure.mapper.CarritoProductoMapper;
import com.example.bakend_vape.carrito.infrastructure.persistence.entity.CarritoProductoEntity;
import com.example.bakend_vape.carrito.infrastructure.persistence.jpa.JpaCarritoProductoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CarritoProductoRepositoryAdapter implements CarritoProductoRepository {

    private final JpaCarritoProductoRepository jpa;
    private final CarritoProductoMapper mapper;

    public CarritoProductoRepositoryAdapter(JpaCarritoProductoRepository jpa) {
        this.jpa = jpa;
        this.mapper = new CarritoProductoMapper();
    }

    @Override
    public CarritoProducto save(CarritoProducto carritoProducto) {

        CarritoProductoEntity entity = mapper.toEntity(carritoProducto);
        CarritoProductoEntity saved = jpa.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<CarritoProducto> findById(Long idCarritoProducto) {
        return jpa.findById(idCarritoProducto).map(mapper::toDomain);
    }

    @Override
    public List<CarritoProducto> findAll() {
        return jpa.findAll().stream().map(mapper :: toDomain).toList();
    }

    @Override
    public List<CarritoProducto> findByCarritoId(Long idCarrito) {
        return jpa.findByCarritoIdCarrito(idCarrito).stream().map(mapper :: toDomain).toList();
    }

    @Override
    public List<CarritoProducto> findByProductoId(Long idProducto) {
        return jpa.findByProductoIdProducto(idProducto).stream().map(mapper :: toDomain).toList();
    }

    @Override
    public void deleteById(Long idCarritoProducto) {

        jpa.deleteById(idCarritoProducto);

    }

    @Override
    public void deleteByCarritoId(Long idCarrito) {

        jpa.deleteAllByCarritoIdCarrito(idCarrito);
    }

    @Override
    public boolean existsByCarritoIdAndProductoId(Long idCarrito, Long idProducto) {
        return jpa.existsByCarritoIdCarritoAndProductoIdProducto(idCarrito, idProducto);
    }
}

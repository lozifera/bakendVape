package com.example.bakend_vape.carrito.infrastructure.persistence.adapter;

import com.example.bakend_vape.carrito.domain.model.Carrito;
import com.example.bakend_vape.carrito.domain.repository.CarritoRepository;
import com.example.bakend_vape.carrito.infrastructure.mapper.CarritoMapper;
import com.example.bakend_vape.carrito.infrastructure.persistence.entity.CarritoEntity;
import com.example.bakend_vape.carrito.infrastructure.persistence.jpa.JpaCarritoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CarritoRepositoryAdapter implements CarritoRepository {

    private final JpaCarritoRepository jpa;
    private final CarritoMapper mapper;

    public CarritoRepositoryAdapter(JpaCarritoRepository jpa) {
        this.jpa = jpa;
        this.mapper = new CarritoMapper();
    }

    @Override
    public Carrito save(Carrito carrito) {
        CarritoEntity entity = mapper.toEntity(carrito);
        CarritoEntity saved = jpa.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Carrito> findById(Long id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Carrito> findByUsuarioId(Long usuarioId) {
        return jpa.findByUsuarioIdUsuario(usuarioId).map(mapper::toDomain);
    }

    @Override
    public Optional<Carrito> findBySessionId(String sessionId) {
        return jpa.findBySessionId(sessionId).map(mapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }
}

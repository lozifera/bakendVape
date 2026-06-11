package com.example.bakend_vape.direccion.infrastructure.persistence.adapter;

import com.example.bakend_vape.direccion.domain.model.Direccion;
import com.example.bakend_vape.direccion.domain.repository.DireccionRepository;
import com.example.bakend_vape.direccion.infrastructure.mapper.DireccionMapper;
import com.example.bakend_vape.direccion.infrastructure.persistence.entity.DireccionEntity;
import com.example.bakend_vape.direccion.infrastructure.persistence.jpa.JpaDireccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DireccionRepositoryAdapter implements DireccionRepository {

    private final JpaDireccionRepository jpa;
    private final DireccionMapper mapper;



    @Override
    public Direccion save(Direccion direccion) {

        DireccionEntity entity = mapper.toEntity(direccion);
        DireccionEntity saved = jpa.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Direccion> findById(Long id) {
        return jpa.findById(id).map(mapper :: toDomain);
    }

    @Override
    public List<Direccion> findByUsuario(Long idUsuario) {
        return jpa.findByUsuarioIdUsuario(idUsuario).stream().map(mapper :: toDomain).toList();
    }

    @Override
    public void delete(Long id) {

        jpa.deleteById(id);

    }

    @Override
    public Long countByUsuario(Long idUsuario) {
        return jpa.countByUsuarioIdUsuario(idUsuario);
    }
}

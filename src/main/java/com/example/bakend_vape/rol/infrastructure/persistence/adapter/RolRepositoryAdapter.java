package com.example.bakend_vape.rol.infrastructure.persistence.adapter;

import com.example.bakend_vape.rol.domain.model.Rol;
import com.example.bakend_vape.rol.domain.repository.RolRepository;
import com.example.bakend_vape.rol.infrastructure.mapper.RolMapper;
import com.example.bakend_vape.rol.infrastructure.persistence.entity.RolEntity;
import com.example.bakend_vape.rol.infrastructure.persistence.jpa.JpaRolRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RolRepositoryAdapter implements RolRepository {

    private final JpaRolRepository jpa;
    private final RolMapper mapper;

    public RolRepositoryAdapter(JpaRolRepository jpa, RolMapper mapper) {
        this.jpa = jpa;
        this.mapper = mapper;
    }

    @Override
    public Rol save(Rol rol) {

        RolEntity entity = mapper.toEntity(rol);
        RolEntity saved = jpa.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Rol> findById(Long id) {
        return jpa.findById(id).map(mapper :: toDomain);
    }

    @Override
    public Optional<Rol> findByNombre(String nombre) {
        return jpa.findByNombre(nombre).map(mapper :: toDomain);
    }

    @Override
    public List<Rol> findAll() {
        return jpa.findAll().stream().map(mapper :: toDomain).toList();
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return jpa.existsByNombre(nombre);
    }
}

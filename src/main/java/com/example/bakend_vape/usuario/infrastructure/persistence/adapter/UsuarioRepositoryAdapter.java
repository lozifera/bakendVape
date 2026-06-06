package com.example.bakend_vape.usuario.infrastructure.persistence.adapter;

import com.example.bakend_vape.usuario.domain.model.Usuario;
import com.example.bakend_vape.usuario.domain.repository.UsuarioRepository;
import com.example.bakend_vape.usuario.infrastructure.mapper.UsuarioMapper;
import com.example.bakend_vape.usuario.infrastructure.persistence.entity.UsuarioEntity;
import com.example.bakend_vape.usuario.infrastructure.persistence.jpa.JpaUsuarioRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepositoryAdapter implements UsuarioRepository {

    private final JpaUsuarioRepository jpa;
    private  final UsuarioMapper mapper;

    public UsuarioRepositoryAdapter(JpaUsuarioRepository jpa) {
        this.jpa = jpa;
        this.mapper = new UsuarioMapper();
    }

    @Override
    public Usuario save(Usuario usuario) {

        UsuarioEntity entity = mapper.toEntity(usuario);
        UsuarioEntity saved = jpa.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return jpa.findById(id).map(mapper :: toDomain);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return jpa.findByEmail(email).map(mapper :: toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpa.existsByEmail(email);
    }

    @Override
    public List<Usuario> findAll() {
        return jpa.findAll().stream().map(mapper :: toDomain).toList();
    }

    @Override
    public List<Usuario> findByEsVip(Boolean esVip) {
        return jpa.findByEsVip(esVip).stream().map(mapper :: toDomain).toList();
    }

    @Override
    public Long countByRolIdRol(Long rolId) {
        return jpa.countByRolIdRol(rolId);
    }

    @Override
    public void delete(Long id) {

        jpa.deleteById(id);
    }
}

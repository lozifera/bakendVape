package com.example.bakend_vape.imagen.infrastructure.persistence.adapter;

import com.example.bakend_vape.imagen.domain.model.Imagen;
import com.example.bakend_vape.imagen.domain.repository.ImagenRepository;
import com.example.bakend_vape.imagen.infrastructure.mapper.ImagenMapper;
import com.example.bakend_vape.imagen.infrastructure.persistence.entity.ImagenEntity;
import com.example.bakend_vape.imagen.infrastructure.persistence.jpa.JpaImagenRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ImagenRepositoryAdapter implements ImagenRepository {

    private final JpaImagenRepository jpa;
    private final ImagenMapper mapper;

    public ImagenRepositoryAdapter (JpaImagenRepository jpa) {
        this.jpa = jpa;
        this.mapper = new ImagenMapper();
    }

    @Override
    public Imagen save(Imagen imagen) {

        ImagenEntity entity = mapper.toEntity(imagen);
        ImagenEntity saved = jpa.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Imagen> findById(Long id) {
        return jpa.findById(id).map(mapper :: toDomain);
    }

    @Override
    public Optional<Imagen> findByUrl(String url) {
        return jpa.findByUrl(url).map(mapper :: toDomain);
    }

    @Override
    public List<Imagen> findAll() {
        return jpa.findAll().stream().map(mapper :: toDomain).toList();
    }

    @Override
    public void delete(Long id) {

        jpa.deleteById(id);

    }
}

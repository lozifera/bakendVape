package com.example.bakend_vape.imagen.infrastructure.persistence.adapter;

import com.example.bakend_vape.imagen.domain.model.ImagenCategoria;
import com.example.bakend_vape.imagen.domain.repository.ImagenCategoriaRepository;
import com.example.bakend_vape.imagen.infrastructure.mapper.ImagenCategoriaMapper;
import com.example.bakend_vape.imagen.infrastructure.persistence.entity.ImagenCategoriaEntity;
import com.example.bakend_vape.imagen.infrastructure.persistence.jpa.JpaImagenCategoriaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ImagenCategoriaRepositoryAdapter implements ImagenCategoriaRepository {

    private final JpaImagenCategoriaRepository jpa;
    private final ImagenCategoriaMapper mapper;

    public ImagenCategoriaRepositoryAdapter(JpaImagenCategoriaRepository jpa) {
        this.jpa = jpa;
        this.mapper = new ImagenCategoriaMapper();
    }

    @Override
    public ImagenCategoria save(ImagenCategoria imagenCategoria) {

        ImagenCategoriaEntity entity = mapper.toEntity(imagenCategoria);
        ImagenCategoriaEntity saved = jpa.save(entity);

        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ImagenCategoria> findById(Long idImagenCategoria) {
        return jpa.findById(idImagenCategoria).map(mapper :: toDomain);
    }

    @Override
    public List<ImagenCategoria> findAll() {
        return jpa.findAll().stream().map(mapper :: toDomain).toList();
    }

    @Override
    public void delete(Long idImagenCategoria) {

        jpa.deleteById(idImagenCategoria);
    }

    @Override
    public List<ImagenCategoria> findByCategoriaId(Long idCategoria) {
        return jpa.findByCategoriaIdCategoria(idCategoria).stream().map(mapper :: toDomain).toList();
    }

    @Override
    public List<ImagenCategoria> findByImagenId(Long idImagen) {
        return jpa.findByImagenIdImagen(idImagen).stream().map(mapper :: toDomain).toList();
    }
}
